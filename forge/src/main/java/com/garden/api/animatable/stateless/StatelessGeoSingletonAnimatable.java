package com.garden.api.animatable.stateless;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;
import com.garden.api.animatable.SingletonGeoAnimatable;
import com.garden.api.core.animation.RawAnimation;
import com.garden.api.network.GardenApiNetwork;
import com.garden.api.network.packet.StatelessSingletonPlayAnimPacket;
import com.garden.api.network.packet.StatelessSingletonStopAnimPacket;

/**
 * Extension of {@link StatelessAnimatable} for {@link SingletonGeoAnimatable} animatables.
 * <p>
 * Animatables <b><u>MUST</u></b> be registered with {@link SingletonGeoAnimatable#registerSyncedAnimatable} to use this interface
 */
public non-sealed interface StatelessGeoSingletonAnimatable extends StatelessAnimatable, SingletonGeoAnimatable {
    /**
     * Start or continue an animation, letting its pre-defined loop type determine whether it should loop or not
     */
    default void playAnimation(String animation, Entity relatedEntity, long instanceId) {
        playAnimation(RawAnimation.begin().thenPlay(animation), relatedEntity, instanceId);
    }

    /**
     * Start or continue an animation, forcing it to loop continuously until stopped
     */
    default void playLoopingAnimation(String animation, Entity relatedEntity, long instanceId) {
        playAnimation(RawAnimation.begin().thenLoop(animation), relatedEntity, instanceId);
    }

    /**
     * Start or continue an animation, then hold the pose at the end of the animation until otherwise stopped
     */
    default void playAndHoldAnimation(String animation, Entity relatedEntity, long instanceId) {
        playAnimation(RawAnimation.begin().thenPlayAndHold(animation), relatedEntity, instanceId);
    }

    /**
     * Stop an already-playing animation
     */
    default void stopAnimation(RawAnimation animation, Entity relatedEntity, long instanceId) {
        stopAnimation(animation.getStageCount() == 1 ? animation.getAnimationStages().get(0).animationName() : animation.toString(), relatedEntity, instanceId);
    }

    /**
     * Start or continue a pre-defined animation
     */
    default void playAnimation(RawAnimation animation, Entity relatedEntity, long instanceId) {
        if (relatedEntity.level().isClientSide) {
            handleClientAnimationPlay(this, instanceId, animation);
        }
        else {
            GardenApiNetwork.send(new StatelessSingletonPlayAnimPacket(GardenApiNetwork.getSyncedSingletonAnimatableId(this), instanceId, animation), PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> relatedEntity));
        }
    }

    /**
     * Stop an already-playing animation
     */
    default void stopAnimation(String animation, Entity relatedEntity, long instanceId) {
        if (relatedEntity.level().isClientSide) {
            handleClientAnimationStop(this, instanceId, animation);
        }
        else {
            GardenApiNetwork.send(new StatelessSingletonStopAnimPacket(GardenApiNetwork.getSyncedSingletonAnimatableId(this), instanceId, animation), PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> relatedEntity));
        }
    }

    // Unsupported method handlers below; do not use

    /**
     * @deprecated Use {@link #playAnimation(String, Entity, long)} instead.
     */
    @Deprecated
    default void playAnimation(String animation) {
        throw new IllegalStateException("Cannot use non-level method handlers on StatelessSingletonGeoAnimatable");
    }

    /**
     * @deprecated Use {@link #playLoopingAnimation(String, Entity, long)} instead.
     */
    @Deprecated
    default void playLoopingAnimation(String animation) {
        throw new IllegalStateException("Cannot use non-level method handlers on StatelessSingletonGeoAnimatable");
    }

    /**
     * @deprecated Use {@link #playAndHoldAnimation(String, Entity, long)} instead.
     */
    @Deprecated
    default void playAndHoldAnimation(String animation) {
        throw new IllegalStateException("Cannot use non-level method handlers on StatelessSingletonGeoAnimatable");
    }

    /**
     * @deprecated Use {@link #stopAnimation(RawAnimation, Entity, long)} instead.
     */
    @Deprecated
    default void stopAnimation(RawAnimation animation) {
        throw new IllegalStateException("Cannot use non-level method handlers on StatelessSingletonGeoAnimatable");
    }

    /**
     * @deprecated Use {@link #playAnimation(RawAnimation, Entity, long)} instead.
     */
    @Deprecated
    default void playAnimation(RawAnimation animation) {
        throw new IllegalStateException("Cannot use non-level method handlers on StatelessSingletonGeoAnimatable");
    }

    /**
     * @deprecated Use {@link #stopAnimation(String, Entity, long)} instead.
     */
    @Deprecated
    default void stopAnimation(String animation) {
        throw new IllegalStateException("Cannot use non-level method handlers on StatelessSingletonGeoAnimatable");
    }
}
