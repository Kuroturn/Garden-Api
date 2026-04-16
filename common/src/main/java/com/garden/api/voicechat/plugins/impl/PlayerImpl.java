package com.garden.api.voicechat.plugins.impl;

import com.garden.api.voicechat.api.Player;
import com.garden.api.voicechat.intercompatibility.CommonCompatibilityManager;

public class PlayerImpl extends EntityImpl implements Player {

    public PlayerImpl(net.minecraft.world.entity.player.Player entity) {
        super(entity);
    }

    @Override
    public Object getPlayer() {
        return CommonCompatibilityManager.INSTANCE.createRawApiPlayer(getRealPlayer());
    }

    public net.minecraft.world.entity.player.Player getRealPlayer() {
        return (net.minecraft.world.entity.player.Player) entity;
    }

}
