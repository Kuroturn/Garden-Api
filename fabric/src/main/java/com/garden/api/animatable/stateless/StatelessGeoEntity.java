package com.garden.api.animatable.stateless;

import net.minecraft.world.entity.Entity;
import com.garden.api.animatable.GeoEntity;
import com.garden.api.core.animation.RawAnimation;
import com.garden.api.network.GardenApiNetwork;
import com.garden.api.network.packet.StatelessEntityPlayAnimPacket;
import com.garden.api.network.packet.StatelessEntityStopAnimPacket;

/**
 * Extension of {@link StatelessAnimatable} for {@link GeoEntity} animatables
 */
public non-sealed interface StatelessGeoEntity extends StatelessAnimatable, GeoEntity {
    /**
     * Start or continue a pre-defined animation
     */
    @Override
    default void playAnimation(RawAnimation animation) {
        if (!(this instanceof Entity self))
            throw new ClassCastException("Cannot use StatelessGeoEntity on a non-entity animatable!");

        if (self.level().isClientSide) {
            handleClientAnimationPlay(this, self.getId(), animation);
        }
        else {
            GardenApiNetwork.sendToTrackingEntityAndSelf(new StatelessEntityPlayAnimPacket(self.getId(), false, animation), self);
        }
    }

    /**
     * Stop an already-playing animation
     */
    @Override
    default void stopAnimation(String animation) {
        if (!(this instanceof Entity self))
            throw new ClassCastException("Cannot use StatelessGeoEntity on a non-entity animatable!");

        if (self.level().isClientSide) {
            handleClientAnimationStop(this, self.getId(), animation);
        }
        else {
            GardenApiNetwork.sendToTrackingEntityAndSelf(new StatelessEntityStopAnimPacket(self.getId(), false, animation), self);
        }
    }
}
