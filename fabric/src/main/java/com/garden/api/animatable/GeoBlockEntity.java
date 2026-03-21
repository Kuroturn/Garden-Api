package com.garden.api.animatable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.ApiStatus;
import com.garden.api.GardenApi;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.network.GardenApiNetwork;
import com.garden.api.network.SerializableDataTicket;
import com.garden.api.network.packet.BlockEntityAnimDataSyncPacket;
import com.garden.api.network.packet.BlockEntityAnimTriggerPacket;
import com.garden.api.network.packet.StopTriggeredBlockEntityAnimPacket;
import com.garden.api.util.RenderUtils;

import javax.annotation.Nullable;

/**
 * The {@link GeoAnimatable} interface specific to {@link BlockEntity BlockEntities}
 * @see <a href="https://github.com/bernie-g/garden_api/wiki/Block-Animations">GardenApi Wiki - Block Animations</a>
 */
public interface GeoBlockEntity extends GeoAnimatable {
	/**
	 * Get server-synced animation data via its relevant {@link SerializableDataTicket}.<br>
	 * Should only be used on the <u>client-side</u>.<br>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 * @param dataTicket The data ticket for the data to retrieve
	 * @return The synced data, or null if no data of that type has been synced
	 */
	@Nullable
	default <D> D getAnimData(SerializableDataTicket<D> dataTicket) {
		return getAnimatableInstanceCache().getManagerForId(0).getData(dataTicket);
	}

	/**
	 * Saves an arbitrary piece of data to this animatable's {@link AnimatableManager}.<br>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 * @param dataTicket The DataTicket to sync the data for
	 * @param data The data to sync
	 */
	default <D> void setAnimData(SerializableDataTicket<D> dataTicket, D data) {
		BlockEntity blockEntity = (BlockEntity)this;
		Level level = blockEntity.getLevel();

		if (level == null) {
			GardenApi.LOGGER.error("Attempting to set animation data for BlockEntity too early! Must wait until after the BlockEntity has been set in the world. (" + blockEntity.getClass().toString() + ")");

			return;
		}

		if (level.isClientSide()) {
			getAnimatableInstanceCache().getManagerForId(0).setData(dataTicket, data);
		}
		else {
			BlockPos pos = blockEntity.getBlockPos();

			GardenApiNetwork.sendToEntitiesTrackingChunk(new BlockEntityAnimDataSyncPacket<>(pos, dataTicket, data), (ServerLevel) level, pos);
		}
	}

	/**
	 * Trigger an animation for this BlockEntity, based on the controller name and animation name.<br>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 * @param controllerName The name of the controller name the animation belongs to, or null to do an inefficient lazy search
	 * @param animName The name of animation to trigger. This needs to have been registered with the controller via {@link com.garden.api.core.animation.AnimationController#triggerableAnim AnimationController.triggerableAnim}
	 */
	default void triggerAnim(@Nullable String controllerName, String animName) {
		BlockEntity blockEntity = (BlockEntity)this;
		Level level = blockEntity.getLevel();

		if (level == null) {
			GardenApi.LOGGER.error("Attempting to trigger an animation for a BlockEntity too early! Must wait until after the BlockEntity has been set in the world. (" + blockEntity.getClass().toString() + ")");

			return;
		}

		if (level.isClientSide()) {
			getAnimatableInstanceCache().getManagerForId(0).tryTriggerAnimation(controllerName, animName);
		}
		else {
			BlockPos pos = blockEntity.getBlockPos();

			GardenApiNetwork.sendToEntitiesTrackingChunk(new BlockEntityAnimTriggerPacket(pos, controllerName, animName), (ServerLevel)level, pos);
		}
	}

	/**
	 * Stop a previously triggered animation for this BlockEntity for the given controller name and animation name
	 * <p>
	 * This can be fired from either the client or the server, but optimally you would call it from the server
	 * <p>
	 * <b><u>DO NOT OVERRIDE</u></b>
	 *
	 * @param controllerName The name of the controller name the animation belongs to, or null to do an inefficient lazy search
	 * @param animName The name of the triggered animation to stop, or null to stop any currently playing triggered animation
	 */
	@ApiStatus.NonExtendable
	default void stopTriggeredAnim(@Nullable String controllerName, @Nullable String animName) {
		BlockEntity blockEntity = (BlockEntity)this;
		Level level = blockEntity.getLevel();

		if (level == null) {
			GardenApi.LOGGER.error("Attempting to stop a triggered animation for a BlockEntity too early! Must wait until after the BlockEntity has been set in the world. (" + blockEntity.getClass().toString() + ")");

			return;
		}

		if (level.isClientSide()) {
			AnimatableManager<GeoAnimatable> animatableManager = getAnimatableInstanceCache().getManagerForId(0);

			if (controllerName != null) {
				animatableManager.stopTriggeredAnimation(controllerName, animName);
			}
			else {
				animatableManager.stopTriggeredAnimation(animName);
			}
		}
		else {
			BlockPos pos = blockEntity.getBlockPos();

			GardenApiNetwork.sendToEntitiesTrackingChunk(new StopTriggeredBlockEntityAnimPacket(pos, controllerName, animName), (ServerLevel)level, pos);
		}
	}

	/**
	 * Returns the current age/tick of the animatable instance.<br>
	 * By default this is just the animatable's age in ticks, but this method allows for non-ticking custom animatables to provide their own values
	 * @param blockEntity The BlockEntity representing this animatable
	 * @return The current tick/age of the animatable, for animation purposes
	 */
	@Override
	default double getTick(Object blockEntity) {
		return RenderUtils.getCurrentTick();
	}
}
