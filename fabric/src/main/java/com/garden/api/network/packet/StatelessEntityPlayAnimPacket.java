package com.garden.api.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import com.garden.api.animatable.stateless.StatelessAnimatable;
import com.garden.api.animatable.stateless.StatelessGeoEntity;
import com.garden.api.animatable.stateless.StatelessGeoReplacedEntity;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animation.RawAnimation;
import com.garden.api.network.AbstractPacket;
import com.garden.api.network.GardenApiNetwork;
import com.garden.api.util.ClientUtils;
import com.garden.api.util.NetworkUtil;
import com.garden.api.util.RenderUtils;

/**
 * Packet for instructing clients to set a play state for an animation for a {@link StatelessGeoEntity} or
 * {@link StatelessGeoReplacedEntity}
 */
public class StatelessEntityPlayAnimPacket extends AbstractPacket {
    private final int entityId;
    private final boolean isReplacedEntity;

    private final RawAnimation animation;

    public StatelessEntityPlayAnimPacket(int entityId, RawAnimation animation) {
        this(entityId, false, animation);
    }

    public StatelessEntityPlayAnimPacket(int entityId, boolean isReplacedEntity, RawAnimation animation) {
        this.entityId = entityId;
        this.isReplacedEntity = isReplacedEntity;
        this.animation = animation;
    }

    @Override
    public FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();

        buf.writeVarInt(this.entityId);
        buf.writeBoolean(this.isReplacedEntity);

        NetworkUtil.writeRawAnimationToBuffer(this.animation, buf);

        return buf;
    }

    @Override
    public ResourceLocation getPacketID() {
        return GardenApiNetwork.STATELESS_ENTITY_PLAY_ANIM_PACKET_ID;
    }

    public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        final int entityId = buf.readVarInt();
        final boolean isReplacedEntity = buf.readBoolean();

        final RawAnimation animation = NetworkUtil.readRawAnimationFromBuffer(buf);

        client.execute(() -> runOnThread(entityId, isReplacedEntity, animation));
    }

    private static void runOnThread(int entityId, boolean isReplacedEntity, RawAnimation animation) {
        Entity entity = ClientUtils.getLevel().getEntity(entityId);

        if (entity == null)
            return;

        GeoAnimatable animatable = isReplacedEntity ?
                                   RenderUtils.getReplacedAnimatable(entity.getType()) :
                                   entity instanceof GeoAnimatable entityAnimatable ? entityAnimatable : null;

        if (animatable instanceof StatelessAnimatable statelessAnimatable)
            statelessAnimatable.handleClientAnimationPlay(animatable, entityId, animation);
    }
}
