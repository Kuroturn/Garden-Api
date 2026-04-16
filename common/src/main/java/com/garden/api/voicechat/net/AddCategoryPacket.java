package com.garden.api.voicechat.net;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.plugins.impl.VolumeCategoryImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class AddCategoryPacket implements Packet<AddCategoryPacket> {

    public static final ResourceLocation ADD_CATEGORY = new ResourceLocation(Voicechat.MODID, "add_category");

    private VolumeCategoryImpl category;

    public AddCategoryPacket() {

    }

    public AddCategoryPacket(VolumeCategoryImpl category) {
        this.category = category;
    }

    public VolumeCategoryImpl getCategory() {
        return category;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return ADD_CATEGORY;
    }

    @Override
    public AddCategoryPacket fromBytes(FriendlyByteBuf buf) {
        category = VolumeCategoryImpl.fromBytes(buf);
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        category.toBytes(buf);
    }

}
