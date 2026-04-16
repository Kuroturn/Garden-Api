package com.garden.api.voicechat.voice.server;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.intercompatibility.CommonCompatibilityManager;
import com.garden.api.voicechat.net.AddCategoryPacket;
import com.garden.api.voicechat.net.NetManager;
import com.garden.api.voicechat.net.RemoveCategoryPacket;
import com.garden.api.voicechat.plugins.CategoryManager;
import com.garden.api.voicechat.plugins.impl.VolumeCategoryImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

public class ServerCategoryManager extends CategoryManager {

    private final Server server;

    public ServerCategoryManager(Server server) {
        this.server = server;
    }

    public void onPlayerCompatibilityCheckSucceeded(ServerPlayer player) {
        Voicechat.LOGGER.debug("Synchronizing {} volume categories with {}", categories.size(), player.getName().getString());
        for (VolumeCategoryImpl category : getCategories()) {
            broadcastAddCategory(server.getServer(), category);
        }
    }

    @Override
    public void addCategory(VolumeCategoryImpl category) {
        super.addCategory(category);
        Voicechat.LOGGER.debug("Synchronizing volume category {} with all players", category.getId());
        broadcastAddCategory(server.getServer(), category);
    }

    @Override
    @Nullable
    public VolumeCategoryImpl removeCategory(String categoryId) {
        VolumeCategoryImpl volumeCategory = super.removeCategory(categoryId);
        Voicechat.LOGGER.debug("Removing volume category {} for all players", categoryId);
        broadcastRemoveCategory(server.getServer(), categoryId);
        return volumeCategory;
    }

    private void broadcastAddCategory(MinecraftServer server, VolumeCategoryImpl category) {
        AddCategoryPacket packet = new AddCategoryPacket(category);
        server.getPlayerList().getPlayers().forEach(p -> NetManager.sendToClient(p, packet));
    }

    private void broadcastRemoveCategory(MinecraftServer server, String categoryId) {
        RemoveCategoryPacket packet = new RemoveCategoryPacket(categoryId);
        server.getPlayerList().getPlayers().forEach(p -> NetManager.sendToClient(p, packet));
    }

}
