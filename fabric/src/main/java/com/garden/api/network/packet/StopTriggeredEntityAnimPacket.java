package com.garden.api.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import com.garden.api.animatable.GeoEntity;
import com.garden.api.animatable.GeoReplacedEntity;
import com.garden.api.network.AbstractPacket;
import com.garden.api.network.GardenApiNetwork;
import com.garden.api.util.ClientUtils;
import com.garden.api.util.RenderUtils;

import javax.annotation.Nullable;

public class StopTriggeredEntityAnimPacket extends AbstractPacket {
    private final int entityId;
    private final boolean isReplacedEntity;

    private final String controllerName;
    private final String animName;

    public StopTriggeredEntityAnimPacket(int entityId, @Nullable String controllerName, @Nullable String animName) {
        this(entityId, false, controllerName, animName);
    }

    public StopTriggeredEntityAnimPacket(int entityId, boolean isReplacedEntity, @Nullable String controllerName,
                                         @Nullable String animName) {
        this.entityId = entityId;
        this.isReplacedEntity = isReplacedEntity;
        this.controllerName = controllerName == null ? "" : controllerName;
        this.animName = animName == null ? "" : animName;
    }

    @Override
    public FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();

        buf.writeVarInt(this.entityId);
        buf.writeBoolean(this.isReplacedEntity);

        buf.writeUtf(this.controllerName);
        buf.writeUtf(this.animName);

        return buf;
    }

    @Override
    public ResourceLocation getPacketID() {
        return GardenApiNetwork.STOP_TRIGGERED_ENTITY_ANIM_PACKET_ID;
    }

    public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        final int entityId = buf.readVarInt();
        final boolean isReplacedEntity = buf.readBoolean();

        final String controllerName = buf.readUtf();
        final String animName = buf.readUtf();

        client.execute(() -> runOnThread(entityId, isReplacedEntity, controllerName, animName));
    }

    private static void runOnThread(int entityId, boolean isReplacedEntity, String controllerName, String animName) {
        Entity entity = ClientUtils.getLevel().getEntity(entityId);

        if (entity == null)
            return;

        if (!isReplacedEntity) {
            if (entity instanceof GeoEntity geoEntity)
                geoEntity.stopTriggeredAnimation(controllerName.isEmpty() ? null : controllerName, animName);

            return;
        }

        if (RenderUtils.getReplacedAnimatable(entity.getType()) instanceof GeoReplacedEntity replacedEntity)
            replacedEntity.stopTriggeredAnimation(entity, controllerName.isEmpty() ? null : controllerName, animName.isEmpty() ? null : animName);
    }
}
