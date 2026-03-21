package com.garden.api.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import com.garden.api.animatable.GeoBlockEntity;
import com.garden.api.animatable.stateless.StatelessGeoBlockEntity;
import com.garden.api.core.animation.RawAnimation;
import com.garden.api.network.AbstractPacket;
import com.garden.api.network.GardenApiNetwork;
import com.garden.api.util.ClientUtils;
import com.garden.api.util.NetworkUtil;

/**
 * Packet for instructing clients to set a play state for an animation for a {@link StatelessGeoBlockEntity}
 */
public class StatelessBlockEntityPlayAnimPacket extends AbstractPacket {
	private final BlockPos blockPos;
	private final RawAnimation animation;

	public StatelessBlockEntityPlayAnimPacket(BlockPos blockPos, RawAnimation animation) {
		this.blockPos = blockPos;
		this.animation = animation;
	}

	@Override
    public FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();

        buf.writeBlockPos(this.blockPos);
        NetworkUtil.writeRawAnimationToBuffer(animation, buf);

        return buf;
    }

	@Override
	public ResourceLocation getPacketID() {
		return GardenApiNetwork.STATELESS_BLOCK_ENTITY_PLAY_ANIM_PACKET_ID;
	}

	public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
		BlockPos blockPos = buf.readBlockPos();
		RawAnimation animation = NetworkUtil.readRawAnimationFromBuffer(buf);

        client.execute(() -> runOnThread(blockPos, animation));
    }

	private static <D> void runOnThread(BlockPos blockPos, RawAnimation animation) {
        if (ClientUtils.getLevel().getBlockEntity(blockPos) instanceof GeoBlockEntity blockEntity && blockEntity instanceof StatelessGeoBlockEntity statelessAnimatable)
            statelessAnimatable.handleClientAnimationPlay(blockEntity, 0, animation);
	}
}
