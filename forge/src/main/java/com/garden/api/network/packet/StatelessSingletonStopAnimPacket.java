package com.garden.api.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import com.garden.api.animatable.stateless.StatelessGeoSingletonAnimatable;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.network.GardenApiNetwork;

import java.util.function.Supplier;

/**
 * Packet for instructing clients to set a stop state for an animation for a {@link StatelessGeoSingletonAnimatable}
 */
public class StatelessSingletonStopAnimPacket {
	private final String syncableId;
	private final long instanceId;
	private final String animation;

	public StatelessSingletonStopAnimPacket(String syncableId, long instanceId, String animation) {
		this.syncableId = syncableId;
		this.instanceId = instanceId;
		this.animation = animation;
	}

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.syncableId);
        buffer.writeVarLong(this.instanceId);
        buffer.writeUtf(this.animation);
    }

    public static StatelessSingletonStopAnimPacket decode(FriendlyByteBuf buffer) {
        final String syncableId = buffer.readUtf();
        final long instanceID = buffer.readVarLong();
        final String animation = buffer.readUtf();

        return new StatelessSingletonStopAnimPacket(syncableId, instanceID, animation);
    }

    public void receivePacket(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context handler = context.get();

        handler.enqueueWork(() -> {
            GeoAnimatable animatable = GardenApiNetwork.getSyncedAnimatable(syncableId);

            if (animatable instanceof StatelessGeoSingletonAnimatable statelessAnimatable)
                statelessAnimatable.handleClientAnimationStop(animatable, instanceId, animation);
        });
    }
}
