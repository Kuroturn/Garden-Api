package com.garden.api.voicechat.plugins.impl;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.api.Group;
import com.garden.api.voicechat.api.ServerPlayer;
import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.voice.common.PlayerState;
import com.garden.api.voicechat.voice.server.PlayerStateManager;
import com.garden.api.voicechat.voice.server.Server;

import javax.annotation.Nullable;

public class VoicechatConnectionImpl implements VoicechatConnection {

    private final ServerPlayer player;
    private final net.minecraft.server.level.ServerPlayer serverPlayer;
    private final PlayerState state;
    @Nullable
    private final Group group;

    public VoicechatConnectionImpl(net.minecraft.server.level.ServerPlayer player, PlayerState state) {
        this.serverPlayer = player;
        this.player = new ServerPlayerImpl(player);
        this.state = state;
        this.group = GroupImpl.create(state);
    }

    @Nullable
    public static VoicechatConnectionImpl fromPlayer(@Nullable net.minecraft.server.level.ServerPlayer player) {
        if (player == null) {
            return null;
        }
        Server server = Voicechat.SERVER.getServer();
        if (server == null) {
            return null;
        }
        PlayerState state = server.getPlayerStateManager().getState(player.getUUID());
        if (state == null) {
            return null;
        }
        return new VoicechatConnectionImpl(player, state);
    }

    @Nullable
    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public boolean isInGroup() {
        return group != null;
    }

    @Override
    public void setGroup(@Nullable Group group) {
        Server server = Voicechat.SERVER.getServer();
        if (server == null) {
            return;
        }
        if (group == null) {
            server.getGroupManager().leaveGroup(serverPlayer);
            return;
        }
        if (group instanceof GroupImpl g) {
            com.garden.api.voicechat.voice.server.Group actualGroup = server.getGroupManager().getGroup(g.getGroup().getId());
            if (actualGroup == null) {
                server.getGroupManager().addGroup(g.getGroup(), serverPlayer);
                actualGroup = g.getGroup();
            }
            server.getGroupManager().joinGroup(actualGroup, serverPlayer, g.getGroup().getPassword());
        }
    }

    @Override
    public boolean isConnected() {
        return !state.isDisconnected();
    }

    @Override
    public void setConnected(boolean connected) {
        if (isInstalled()) {
            return;
        }
        Server server = Voicechat.SERVER.getServer();
        if (server == null) {
            return;
        }
        PlayerStateManager manager = server.getPlayerStateManager();
        PlayerState actualState = manager.getState(state.getUuid());
        if (actualState == null) {
            return;
        }
        if (actualState.isDisconnected() != connected) {
            return;
        }
        actualState.setDisconnected(!connected);
        manager.broadcastState(null, actualState);
    }

    @Override
    public boolean isDisabled() {
        return state.isDisabled();
    }

    @Override
    public void setDisabled(boolean disabled) {
        if (isInstalled()) {
            return;
        }
        Server server = Voicechat.SERVER.getServer();
        if (server == null) {
            return;
        }
        PlayerStateManager manager = server.getPlayerStateManager();
        PlayerState actualState = manager.getState(state.getUuid());
        if (actualState == null) {
            return;
        }
        if (actualState.isDisabled() == disabled) {
            return;
        }
        actualState.setDisabled(disabled);
        manager.broadcastState(null, actualState);
    }

    @Override
    public boolean isInstalled() {
        return Voicechat.SERVER.isCompatible(serverPlayer);
    }

    @Override
    public ServerPlayer getPlayer() {
        return player;
    }

}
