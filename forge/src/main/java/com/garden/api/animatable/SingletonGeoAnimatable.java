package com.garden.api.animatable;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.core.animatable.instance.SingletonAnimatableInstanceCache;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animation.AnimationController;
import com.garden.api.network.GardenApiNetwork;
import com.garden.api.network.SerializableDataTicket;
import com.garden.api.network.packet.AnimDataSyncPacket;
import com.garden.api.network.packet.AnimTriggerPacket;
import com.garden.api.network.packet.StopTriggeredSingletonAnimPacket;

/**
 * The {@link GeoAnimatable} interface specific to singleton objects.
 * This primarily applies to armor and items
 * @see <a href="https://github.com/bernie-g/garden_api/wiki/Item-Animations">GardenApi Wiki - Item Animations</a>
 */
public interface SingletonGeoAnimatable extends GeoAnimatable {
	/**
	 * Register this as a synched {@code GeoAnimatable} instance with GardenApi's networking functions.<br>
	 * This should be called inside the constructor of your object.
	 */
	static void registerSyncedAnimatable(GeoAnimatable animatable) {
		GardenApiNetwork.registerSyncedAnimatable(animatable);
	}

	/**
	 * Get server-synced animation data via its relevant {@link SerializableDataTicket}.<br>
	 * Should only be used on the <u>client-side</u>.<br>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 * @param instanceId The animatable's instance id
	 * @param dataTicket The data ticket for the data to retrieve
	 * @return The synced data, or null if no data of that type has been synced
	 */
	@Nullable
	default <D> D getAnimData(long instanceId, SerializableDataTicket<D> dataTicket) {
		return getAnimatableInstanceCache().getManagerForId(instanceId).getData(dataTicket);
	}

	/**
	 * Saves an arbitrary piece of syncable data to this animatable's {@link AnimatableManager}.<br>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 * @param relatedEntity An entity related to the state of the data for syncing (E.G. The player holding the item)
	 * @param instanceId The unique id that identifies the specific animatable instance
	 * @param dataTicket The DataTicket to sync the data for
	 * @param data The data to sync
	 */
	default <D> void setAnimData(Entity relatedEntity, long instanceId, SerializableDataTicket<D> dataTicket, D data) {
		if (relatedEntity.level().isClientSide()) {
			getAnimatableInstanceCache().getManagerForId(instanceId).setData(dataTicket, data);
		}
		else {
			syncAnimData(instanceId, dataTicket, data, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> relatedEntity));
		}
	}

	/**
	 * Syncs an arbitrary piece of data to all players targeted by the packetTarget.<br>
	 * This method should only be called on the <u>server side</u>.<br>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param instanceId   The unique id that identifies the specific animatable instance
	 * @param dataTicket   The DataTicket to sync the data for
	 * @param data         The data to sync
	 * @param packetTarget The distribution method determining which players to sync the data to
	 */
	default <D> void syncAnimData(long instanceId, SerializableDataTicket<D> dataTicket, D data, PacketDistributor.PacketTarget packetTarget) {
		GardenApiNetwork.send(new AnimDataSyncPacket<>(GardenApiNetwork.getSyncedSingletonAnimatableId(this), instanceId, dataTicket, data), packetTarget);
	}

	/**
	 * Trigger a client-side animation for this GeoAnimatable for the given controller name and animation name.<br>
	 * This can be fired from either the client or the server, but optimally you would call it from the server.<br>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 * @param relatedEntity An entity related to the animatable to trigger the animation for (E.G. The player holding the item)
	 * @param instanceId The unique id that identifies the specific animatable instance
	 * @param controllerName The name of the controller name the animation belongs to, or null to do an inefficient lazy search
	 * @param animName The name of animation to trigger. This needs to have been registered with the controller via {@link com.garden.api.core.animation.AnimationController#triggerableAnim AnimationController.triggerableAnim}
	 */
	default <D> void triggerAnim(Entity relatedEntity, long instanceId, @Nullable String controllerName, String animName) {
		if (relatedEntity.level().isClientSide()) {
			getAnimatableInstanceCache().getManagerForId(instanceId).tryTriggerAnimation(controllerName, animName);
		}
		else {
			triggerAnim(instanceId, controllerName, animName, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> relatedEntity));
		}
	}

	/**
	 * Remotely triggers a client-side animation for this GeoAnimatable for all players targeted by the packetTarget.<br>
	 * This method should only be called on the <u>server side</u>.<br>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param instanceId     The unique id that identifies the specific animatable instance
	 * @param controllerName The name of the controller name the animation belongs to, or null to do an inefficient lazy search
	 * @param animName       The name of animation to trigger. This needs to have been registered with the controller via {@link com.garden.api.core.animation.AnimationController#triggerableAnim AnimationController.triggerableAnim}
	 * @param packetTarget   The distribution method determining which players to sync the data to
	 */
	default <D> void triggerAnim(long instanceId, @Nullable String controllerName, String animName, PacketDistributor.PacketTarget packetTarget) {
		GardenApiNetwork.send(new AnimTriggerPacket<>(GardenApiNetwork.getSyncedSingletonAnimatableId(this), instanceId, controllerName, animName), packetTarget);
	}

	/**
	 * Stop a previously triggered animation for this GeoAnimatable for the given controller name and animation name
	 * <p>
	 * This can be fired from either the client or the server, but optimally you would call it from the server
	 * <p>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param relatedEntity An entity related to the animatable to trigger the animation for (E.G. The player holding the item)
	 * @param instanceId The unique id that identifies the specific animatable instance
	 * @param controllerName The name of the controller the animation belongs to, or null to do an inefficient lazy search
	 * @param animName The name of the triggered animation to stop, or null to stop any currently playing triggered animation
	 */
	@ApiStatus.NonExtendable
	default void stopTriggeredAnim(Entity relatedEntity, long instanceId, @Nullable String controllerName, @Nullable String animName) {
		if (relatedEntity.level().isClientSide()) {
			AnimatableManager<GeoAnimatable> animatableManager = getAnimatableInstanceCache().getManagerForId(instanceId);

			if (animatableManager == null)
				return;

			if (controllerName != null) {
				animatableManager.stopTriggeredAnimation(controllerName, animName);
			}
			else {
				animatableManager.stopTriggeredAnimation(animName);
			}
		}
		else {
			stopTriggeredAnim(relatedEntity, instanceId, controllerName, animName, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> relatedEntity));
		}
	}

	/**
	 * Stop a previously triggered animation for this GeoAnimatable for the given controller name and animation name
	 * <p>
	 * This can be fired from either the client or the server, but optimally you would call it from the server
	 * <p>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param relatedEntity An entity related to the animatable to trigger the animation for (E.G. The player holding the item)
	 * @param instanceId The unique id that identifies the specific animatable instance
	 * @param controllerName The name of the controller the animation belongs to, or null to do an inefficient lazy search
	 * @param animName The name of the triggered animation to stop, or null to stop any currently playing triggered animation
	 */
	@ApiStatus.NonExtendable
	default void stopTriggeredAnim(Entity relatedEntity, long instanceId, @Nullable String controllerName, @Nullable String animName, PacketDistributor.PacketTarget packetTarget) {
		GardenApiNetwork.send(new StopTriggeredSingletonAnimPacket(GardenApiNetwork.getSyncedSingletonAnimatableId(this), instanceId, controllerName, animName), packetTarget);
	}

	/**
	 * Trigger a client-side animation for this GeoAnimatable's armor rendering for the given controller name and animation name
	 * <p>
	 * This can be fired from either the client or the server, but optimally you would call it from the server
	 * <p>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param relatedEntity  An entity related to the animatable to trigger the animation for (E.G. The player holding the item)
	 * @param instanceId     The unique id that identifies the specific animatable instance
	 * @param controllerName The name of the controller the animation belongs to, or null to do an inefficient lazy search
	 * @param animName       The name of animation to trigger. This needs to have been registered with the controller via {@link AnimationController#triggerableAnim AnimationController.triggerableAnim}
	 */
	@ApiStatus.NonExtendable
	default void triggerArmorAnim(Entity relatedEntity, long instanceId, @Nullable String controllerName, String animName) {
		triggerAnim(relatedEntity, -instanceId, controllerName, animName);
	}

	/**
	 * Stop a previously triggered animation for this GeoAnimatable's armor rendering for the given controller name and animation name
	 * <p>
	 * This can be fired from either the client or the server, but optimally you would call it from the server
	 * <p>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param relatedEntity An entity related to the animatable to trigger the animation for (E.G. The player holding the item)
	 * @param instanceId The unique id that identifies the specific animatable instance
	 * @param controllerName The name of the controller the animation belongs to, or null to do an inefficient lazy search
	 * @param animName The name of the triggered animation to stop, or null to stop any currently playing triggered animation
	 */
	@ApiStatus.NonExtendable
	default void stopTriggeredArmorAnim(Entity relatedEntity, long instanceId, @Nullable String controllerName, @Nullable String animName) {
		stopTriggeredAnim(relatedEntity, -instanceId, controllerName, animName);
	}

	/**
	 * Override the default handling for instantiating an AnimatableInstanceCache for this animatable.<br>
	 * Don't override this unless you know what you're doing.
	 */
	@Override
	default @Nullable AnimatableInstanceCache animatableCacheOverride() {
		return new SingletonAnimatableInstanceCache(this);
	}
}
