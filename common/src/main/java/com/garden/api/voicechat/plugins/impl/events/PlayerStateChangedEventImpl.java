package com.garden.api.voicechat.plugins.impl.events;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.events.PlayerStateChangedEvent;
import com.garden.api.voicechat.plugins.impl.VoicechatConnectionImpl;
import com.garden.api.voicechat.voice.common.PlayerState;
import com.garden.api.voicechat.voice.server.Server;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerStateChangedEventImpl extends ServerEventImpl implements PlayerStateChangedEvent {

    protected final PlayerState state;
    @Nullable
    protected VoicechatConnectionImpl connection;

    public PlayerStateChangedEventImpl(PlayerState state) {
        this.state = state;
    }

    @Override
    public boolean isDisabled() {
        return state.isDisabled();
    }

    @Override
    public boolean isDisconnected() {
        return state.isDisconnected();
    }

    @Override
    public UUID getPlayerUuid() {
        return state.getUuid();
    }

    @Override
    @Nullable
    public VoicechatConnection getConnection() {
        if (connection == null) {
            Server server = Voicechat.SERVER.getServer();
            if (server == null) {
                return null;
            }
            ServerPlayer player = server.getServer().getPlayerList().getPlayer(state.getUuid());
            if (player == null) {
                return null;
            }
            connection = VoicechatConnectionImpl.fromPlayer(player);
        }
        return connection;
    }
}
