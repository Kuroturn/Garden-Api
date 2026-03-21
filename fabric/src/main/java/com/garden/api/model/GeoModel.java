package com.garden.api.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import com.garden.api.GardenApiException;
import com.garden.api.animatable.GeoReplacedEntity;
import com.garden.api.cache.GardenApiCache;
import com.garden.api.cache.object.BakedGeoModel;
import com.garden.api.cache.object.GeoBone;
import com.garden.api.constant.DataTickets;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animatable.model.CoreGeoModel;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animation.Animation;
import com.garden.api.core.animation.AnimationProcessor;
import com.garden.api.core.animation.AnimationState;
import com.garden.api.core.molang.MolangParser;
import com.garden.api.core.molang.MolangQueries;
import com.garden.api.core.object.DataTicket;
import com.garden.api.loading.object.BakedAnimations;
import com.garden.api.renderer.GeoRenderer;
import com.garden.api.util.RenderUtils;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Base class for all code-based model objects.<br>
 * All models to registered to a {@link GeoRenderer} should be an instance of this or one of its subclasses.
 * @see <a href="https://github.com/bernie-g/garden_api/wiki/Models">GardenApi Wiki - Models</a>
 */
public abstract class GeoModel<T extends GeoAnimatable> implements CoreGeoModel<T> {
	private final AnimationProcessor<T> processor = new AnimationProcessor<>(this);

	private BakedGeoModel currentModel = null;
	private double animTime;
	private double lastGameTickTime;
	private long lastRenderedInstance = -1;

	/**
	 * Returns the resource path for the {@link BakedGeoModel} (model json file) to render based on the provided animatable
	 */
	public ResourceLocation getModelResource(T animatable, @Nullable GeoRenderer<T> renderer) {
		return getModelResource(animatable);
	}

	/**
	 * Returns the resource path for the {@link BakedGeoModel} (model json file) to render based on the provided animatable
	 */
	public abstract ResourceLocation getModelResource(T animatable);

	/**
	 * Returns the resource path for the texture file to render based on the provided animatable
	 */
	public ResourceLocation getTextureResource(T animatable, @Nullable GeoRenderer<T> renderer) {
		return getTextureResource(animatable);
	}

	/**
	 * Returns the resource path for the texture file to render based on the provided animatable
	 */
	public abstract ResourceLocation getTextureResource(T animatable);

	/**
	 * Returns the resourcepath for the {@link BakedAnimations} (animation json file) to use for animations based on the provided animatable
	 */
	public abstract ResourceLocation getAnimationResource(T animatable);

	/**
	 * Returns the resource path for the {@link BakedAnimations} (animation json file) fallback locations in the event
	 * your animation isn't present in the {@link #getAnimationResource(GeoAnimatable) primary resource}.
	 * <p>
	 * Should <b><u>NOT</u></b> be used as the primary animation resource path, and in general shouldn't be used
	 * at all unless you know what you are doing
	 */
	public ResourceLocation[] getAnimationResourceFallbacks(T animatable) {
		return new ResourceLocation[0];
	}

	/**
	 * Override this and return true if Geckolib should crash when attempting to animate the model, but fails to find a bone.<br>
	 * By default, GardenApi will just gracefully ignore a missing bone, which might cause oddities with incorrect models or mismatching variables.<br>
	 */
	public boolean crashIfBoneMissing() {
		return false;
	}

	/**
	 * Gets the default render type for this animatable, to be selected by default by the renderer using it
	 */
	public RenderType getRenderType(T animatable, ResourceLocation texture) {
		return RenderType.entityCutoutNoCull(texture);
	}

	@Override
	public final BakedGeoModel getBakedGeoModel(String location) {
		return getBakedModel(new ResourceLocation(location));
	}

	/**
	 * Get the baked geo model object used for rendering from the given resource path
	 */
	public BakedGeoModel getBakedModel(ResourceLocation location) {
		BakedGeoModel model = GardenApiCache.getBakedModels().get(location);

		if (model == null)
			throw new GardenApiException(location, "Unable to find model");

		if (model != this.currentModel) {
			this.processor.setActiveModel(model);
			this.currentModel = model;
		}

		return this.currentModel;
	}

	/**
	 * Gets a bone from this model by name
	 * @param name The name of the bone
	 * @return An {@link Optional} containing the {@link com.garden.api.cache.object.GeoBone} if one matches, otherwise an empty Optional
	 */
	public Optional<com.garden.api.cache.object.GeoBone> getBone(String name) {
		return Optional.ofNullable((GeoBone)getAnimationProcessor().getBone(name));
	}

	/**
	 * Gets the loaded {@link Animation} for the given animation {@code name}, if it exists
	 *
	 * @param animatable The {@code GeoAnimatable} instance being referred to
	 * @param name The name of the animation to retrieve
	 * @return The {@code Animation} instance for the provided {@code name}, or null if none match
	 */
	@Nullable
	public Animation getAnimation(T animatable, String name) {
		ResourceLocation location = getAnimationResource(animatable);
		BakedAnimations bakedAnimations = GardenApiCache.getBakedAnimations().get(location);
		Animation animation = bakedAnimations != null ? bakedAnimations.getAnimation(name) : null;

		if (animation != null)
			return animation;

		for (ResourceLocation fallbackLocation : getAnimationResourceFallbacks(animatable)) {
			bakedAnimations = GardenApiCache.getBakedAnimations().get(location = fallbackLocation);
			animation = bakedAnimations != null ? bakedAnimations.getAnimation(name) : null;

			if (animation != null)
				return animation;
		}

		if (bakedAnimations == null) {
			if (!location.getPath().contains("animations/"))
				throw new GardenApiException(location, "Invalid animation resource path provided - GardenApi animations must be placed in assets/<modid>/animations/");

			throw new GardenApiException(location, "Unable to find animation file.");
		}

		return null;
	}

	@Override
	public AnimationProcessor<T> getAnimationProcessor() {
		return this.processor;
	}

	/**
	 * Add additional {@link DataTicket DataTickets} to the {@link AnimationState} to be handled by your animation handler at render time
	 * @param animatable The animatable instance currently being animated
	 * @param instanceId The unique instance id of the animatable being animated
	 * @param dataConsumer The DataTicket + data consumer to be added to the AnimationEvent
	 */
	public void addAdditionalStateData(T animatable, long instanceId, BiConsumer<DataTicket<T>, T> dataConsumer) {}

	/**
	 * This method is called once per render frame for each {@link GeoAnimatable} being rendered.<br>
	 * It is an internal method for automated animation parsing. Use {@link CoreGeoModel#setCustomAnimations(GeoAnimatable, long, AnimationState)} for custom animation work
	 */
	@ApiStatus.Internal
	@Override
	public void handleAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
		Minecraft mc = Minecraft.getInstance();
		AnimatableManager<T> animatableManager = animatable.getAnimatableInstanceCache().getManagerForId(instanceId);
		Double currentTick = animationState.getData(DataTickets.TICK);

		if (currentTick == null)
			currentTick = animatable instanceof Entity entity ? (double)entity.tickCount : RenderUtils.getCurrentTick();

		if (animatableManager.getFirstTickTime() == -1)
			animatableManager.startedAt(currentTick + mc.getFrameTime());

		double currentFrameTime = animatable instanceof Entity || animatable instanceof GeoReplacedEntity ? currentTick + mc.getFrameTime() : currentTick - animatableManager.getFirstTickTime();
		boolean isReRender = !animatableManager.isFirstTick() && currentFrameTime == animatableManager.getLastUpdateTime();

		if (isReRender && instanceId == this.lastRenderedInstance)
			return;

		if (!mc.isPaused() || animatable.shouldPlayAnimsWhileGamePaused()) {
			animatableManager.updatedAt(currentFrameTime);

			double lastUpdateTime = animatableManager.getLastUpdateTime();
			this.animTime += lastUpdateTime - this.lastGameTickTime;
			this.lastGameTickTime = lastUpdateTime;
		}

		animationState.animationTick = this.animTime;
		this.lastRenderedInstance = instanceId;
		AnimationProcessor<T> processor = getAnimationProcessor();

		MolangParser.INSTANCE.setValue(MolangQueries.CONTROLLER_SPEED, () -> animationState.getController().getAnimationSpeed());
		processor.preAnimationSetup(animationState.getAnimatable(), this.animTime);

		if (!processor.getRegisteredBones().isEmpty())
			processor.tickAnimation(animatable, this, animatableManager, this.animTime, animationState, crashIfBoneMissing());

		setCustomAnimations(animatable, instanceId, animationState);
	}

	@Override
	public void applyMolangQueries(T animatable, double animTime) {
		MolangParser parser = MolangParser.INSTANCE;
		Minecraft mc = Minecraft.getInstance();

		parser.setMemoizedValue(MolangQueries.LIFE_TIME, () -> animTime / 20d);
		parser.setMemoizedValue(MolangQueries.ACTOR_COUNT, mc.level::getEntityCount);
		parser.setMemoizedValue(MolangQueries.TIME_OF_DAY, () -> mc.level.getDayTime() / 24000f);
		parser.setMemoizedValue(MolangQueries.MOON_PHASE, mc.level::getMoonPhase);

		if (animatable instanceof Entity entity) {
			parser.setMemoizedValue(MolangQueries.DISTANCE_FROM_CAMERA, () -> mc.gameRenderer.getMainCamera().getPosition().distanceTo(entity.position()));
			parser.setMemoizedValue(MolangQueries.IS_ON_GROUND, () -> RenderUtils.booleanToFloat(entity.onGround()));
			parser.setMemoizedValue(MolangQueries.IS_IN_WATER, () -> RenderUtils.booleanToFloat(entity.isInWater()));
			parser.setMemoizedValue(MolangQueries.IS_IN_WATER_OR_RAIN, () -> RenderUtils.booleanToFloat(entity.isInWaterOrRain()));
			parser.setMemoizedValue(MolangQueries.IS_ON_FIRE, () -> RenderUtils.booleanToFloat(entity.isOnFire()));

			if (entity instanceof LivingEntity livingEntity) {
				parser.setMemoizedValue(MolangQueries.HEALTH, livingEntity::getHealth);
				parser.setMemoizedValue(MolangQueries.MAX_HEALTH, livingEntity::getMaxHealth);
				parser.setMemoizedValue(MolangQueries.GROUND_SPEED, () -> {
					Vec3 velocity = livingEntity.getDeltaMovement();

					return Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
				});
				parser.setMemoizedValue(MolangQueries.YAW_SPEED, () -> livingEntity.getYRot() - livingEntity.yRotO);
			}
		}
	}
}
