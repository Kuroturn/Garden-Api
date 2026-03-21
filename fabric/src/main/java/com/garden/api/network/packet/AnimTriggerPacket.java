package com.garden.api.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.network.AbstractPacket;
import com.garden.api.network.GardenApiNetwork;

import javax.annotation.Nullable;

/**
 * Packet for syncing user-definable animations that can be triggered from the
 * server
 */
public class AnimTriggerPacket extends AbstractPacket {
    private final String syncableId;
    private final long instanceId;
    private final String controllerName;
    private final String animName;

    public AnimTriggerPacket(String syncableId, long instanceId, @Nullable String controllerName, String animName) {
        this.syncableId = syncableId;
        this.instanceId = instanceId;
        this.controllerName = controllerName == null ? "" : controllerName;
        this.animName = animName;
    }

    public FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();

        buf.writeUtf(this.syncableId);
        buf.writeVarLong(this.instanceId);
        buf.writeUtf(this.controllerName);
        buf.writeUtf(this.animName);

        return buf;
    }

    @Override
    public ResourceLocation getPacketID() {
        return GardenApiNetwork.ANIM_TRIGGER_SYNC_PACKET_ID;
    }

    public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        String syncableId = buf.readUtf();
        long instanceId = buf.readVarLong();
        String controllerName = buf.readUtf();
        String animName = buf.readUtf();

        client.execute(() -> runOnThread(syncableId, instanceId, controllerName, animName));
    }

    private static <D> void runOnThread(String syncableId, long instanceId, String controllerName, String animName) {
        GeoAnimatable animatable = GardenApiNetwork.getSyncedAnimatable(syncableId);

        if (animatable != null) {
            AnimatableManager<?> manager = animatable.getAnimatableInstanceCache().getManagerForId(instanceId);

            manager.tryTriggerAnimation(controllerName, animName);
        }
    }
}
