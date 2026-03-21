package com.garden.api.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import com.garden.api.animatable.stateless.StatelessGeoSingletonAnimatable;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animation.RawAnimation;
import com.garden.api.network.AbstractPacket;
import com.garden.api.network.GardenApiNetwork;
import com.garden.api.util.NetworkUtil;

/**
 * Packet for instructing clients to set a play state for an animation for a {@link StatelessGeoSingletonAnimatable}
 */
public class StatelessSingletonPlayAnimPacket extends AbstractPacket {
	private final String syncableId;
	private final long instanceId;
	private final RawAnimation animation;

	public StatelessSingletonPlayAnimPacket(String syncableId, long instanceId, RawAnimation animation) {
		this.syncableId = syncableId;
		this.instanceId = instanceId;
		this.animation = animation;
	}

	@Override
    public FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();

        buf.writeUtf(this.syncableId);
        buf.writeVarLong(this.instanceId);
        NetworkUtil.writeRawAnimationToBuffer(animation, buf);

        return buf;
    }

	@Override
	public ResourceLocation getPacketID() {
		return GardenApiNetwork.STATELESS_PLAY_ANIM_PACKET_ID;
	}

	public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
		final String syncableId = buf.readUtf();
		final long instanceID = buf.readVarLong();
		final RawAnimation animation = NetworkUtil.readRawAnimationFromBuffer(buf);

        client.execute(() -> runOnThread(syncableId, instanceID, animation));
    }

	private static <D> void runOnThread(String syncableId, long instanceId, RawAnimation animation) {
		GeoAnimatable animatable = GardenApiNetwork.getSyncedAnimatable(syncableId);

        if (animatable instanceof StatelessGeoSingletonAnimatable statelessAnimatable)
            statelessAnimatable.handleClientAnimationPlay(animatable, instanceId, animation);
	}
}
