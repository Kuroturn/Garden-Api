package com.garden.api.example.item;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import com.garden.api.example.client.renderer.armor.WolfArmorRenderer;
import com.garden.api.example.registry.ItemRegistry;
import com.garden.api.animatable.GeoItem;
import com.garden.api.animatable.client.RenderProvider;
import com.garden.api.constant.DataTickets;
import com.garden.api.constant.DefaultAnimations;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animation.AnimationController;
import com.garden.api.core.object.PlayState;
import com.garden.api.renderer.GeoArmorRenderer;
import com.garden.api.util.GardenApiUtil;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Example {@link com.garden.api.core.animatable.GeoAnimatable GeoAnimatable} {@link ArmorItem} implementation
 * @see GeoItem
 * @see WolfArmorRenderer
 */
public final class WolfArmorItem extends ArmorItem implements GeoItem {
	private final AnimatableInstanceCache cache = GardenApiUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public WolfArmorItem(ArmorMaterial armorMaterial, ArmorItem.Type type, Properties properties) {
		super(armorMaterial, type, properties);
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private GeoArmorRenderer<?> renderer;

			@Override
			public @NotNull HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
				if (this.renderer == null)
					this.renderer = new WolfArmorRenderer();

				// This prepares our GeoArmorRenderer for the current render frame.
				// These parameters may be null however, so we don't do anything further with them
				this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

	// Let's add our animation controller
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, 20, state -> {
			// Apply our generic idle animation.
			// Whether it plays or not is decided down below.
			state.getController().setAnimation(DefaultAnimations.IDLE);

			// Let's gather some data from the state to use below
			// This is the entity that is currently wearing/holding the item
			Entity entity = state.getData(DataTickets.ENTITY);

			// We'll just have ArmorStands always animate, so we can return here
			if (entity instanceof ArmorStand)
				return PlayState.CONTINUE;

			// For this example, we only want the animation to play if the entity is wearing all pieces of the armor
			// Let's collect the armor pieces the entity is currently wearing
			Set<Item> wornArmor = new ObjectOpenHashSet<>();

			for (ItemStack stack : entity.getArmorSlots()) {
				// We can stop immediately if any of the slots are empty
				if (stack.isEmpty())
					return PlayState.STOP;

				wornArmor.add(stack.getItem());
			}

			// Check each of the pieces match our set
			boolean isFullSet = wornArmor.containsAll(ObjectArrayList.of(
					ItemRegistry.WOLF_ARMOR_BOOTS,
					ItemRegistry.WOLF_ARMOR_LEGGINGS,
					ItemRegistry.WOLF_ARMOR_CHESTPLATE,
					ItemRegistry.WOLF_ARMOR_HELMET));

			// Play the animation if the full set is being worn, otherwise stop
			return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}