package com.garden.api.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import com.garden.api.animatable.stateless.StatelessGeoSingletonAnimatable;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.network.AbstractPacket;
import com.garden.api.network.GardenApiNetwork;

/**
 * Packet for instructing clients to set a stop state for an animation for a {@link StatelessGeoSingletonAnimatable}
 */
public class StatelessSingletonStopAnimPacket extends AbstractPacket {
	private final String syncableId;
	private final long instanceId;
	private final String animation;

	public StatelessSingletonStopAnimPacket(String syncableId, long instanceId, String animation) {
		this.syncableId = syncableId;
		this.instanceId = instanceId;
		this.animation = animation;
	}

	@Override
    public FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();

        buf.writeUtf(this.syncableId);
        buf.writeVarLong(this.instanceId);
        buf.writeUtf(this.animation);

        return buf;
    }

	@Override
	public ResourceLocation getPacketID() {
		return GardenApiNetwork.STATELESS_STOP_ANIM_PACKET_ID;
	}

	public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
		final String syncableId = buf.readUtf();
		final long instanceID = buf.readVarLong();
		final String animation = buf.readUtf();

        client.execute(() -> runOnThread(syncableId, instanceID, animation));
    }

	private static <D> void runOnThread(String syncableId, long instanceId, String animation) {
		GeoAnimatable animatable = GardenApiNetwork.getSyncedAnimatable(syncableId);

        if (animatable instanceof StatelessGeoSingletonAnimatable statelessAnimatable)
            statelessAnimatable.handleClientAnimationStop(animatable, instanceId, animation);
	}
}
