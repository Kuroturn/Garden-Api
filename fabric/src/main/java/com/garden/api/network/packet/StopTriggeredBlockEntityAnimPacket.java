package com.garden.api.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import com.garden.api.animatable.GeoBlockEntity;
import com.garden.api.network.AbstractPacket;
import com.garden.api.network.GardenApiNetwork;
import com.garden.api.util.ClientUtils;

import javax.annotation.Nullable;

public class StopTriggeredBlockEntityAnimPacket extends AbstractPacket {
    private final BlockPos pos;
    private final String controllerName;
    private final String animName;

    public StopTriggeredBlockEntityAnimPacket(BlockPos blockPos, @Nullable String controllerName, @Nullable String animName) {
        this.pos = blockPos;
        this.controllerName = controllerName == null ? "" : controllerName;
        this.animName = animName == null ? "" : animName;
    }

    public FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();

        buf.writeBlockPos(this.pos);
        buf.writeUtf(this.controllerName);
        buf.writeUtf(this.animName);

        return buf;
    }

    @Override
    public ResourceLocation getPacketID() {
        return GardenApiNetwork.STOP_TRIGGERED_BLOCK_ENTITY_ANIM_PACKET_ID;
    }

    public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        final BlockPos blockPos = buf.readBlockPos();
        final String controllerName = buf.readUtf();
        final String animName = buf.readUtf();

        client.execute(() -> runOnThread(blockPos, controllerName, animName));
    }

    private static void runOnThread(BlockPos blockPos, String controllerName, String animName) {
        BlockEntity blockEntity = ClientUtils.getLevel().getBlockEntity(blockPos);

        if (blockEntity instanceof GeoBlockEntity getBlockEntity)
            getBlockEntity.stopTriggeredAnim(controllerName.isEmpty() ? null : controllerName, animName.isEmpty() ? null : animName);
    }
}
