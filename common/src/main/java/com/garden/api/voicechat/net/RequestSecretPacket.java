package com.garden.api.voicechat.net;

import com.garden.api.voicechat.Voicechat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class RequestSecretPacket implements Packet<RequestSecretPacket> {

    public static final ResourceLocation REQUEST_SECRET = new ResourceLocation(Voicechat.MODID, "request_secret");

    private int compatibilityVersion;

    public RequestSecretPacket() {

    }

    public RequestSecretPacket(int compatibilityVersion) {
        this.compatibilityVersion = compatibilityVersion;
    }

    public int getCompatibilityVersion() {
        return compatibilityVersion;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return REQUEST_SECRET;
    }

    @Override
    public RequestSecretPacket fromBytes(FriendlyByteBuf buf) {
        compatibilityVersion = buf.readInt();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(compatibilityVersion);
    }

}
