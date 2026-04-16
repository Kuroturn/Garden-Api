package com.garden.api.voicechat.plugins.impl;

import com.garden.api.voicechat.api.ServerLevel;
import com.garden.api.voicechat.api.ServerPlayer;

public class ServerPlayerImpl extends PlayerImpl implements ServerPlayer {

    public ServerPlayerImpl(net.minecraft.server.level.ServerPlayer entity) {
        super(entity);
    }

    public net.minecraft.server.level.ServerPlayer getRealServerPlayer() {
        return (net.minecraft.server.level.ServerPlayer) entity;
    }

    @Override
    public ServerLevel getServerLevel() {
        return new ServerLevelImpl((net.minecraft.server.level.ServerLevel) entity.level());
    }
}
