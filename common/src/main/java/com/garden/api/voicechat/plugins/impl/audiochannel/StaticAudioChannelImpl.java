package com.garden.api.voicechat.plugins.impl.audiochannel;

import com.garden.api.voicechat.api.VoicechatConnection;
import com.garden.api.voicechat.api.audiochannel.StaticAudioChannel;
import com.garden.api.voicechat.api.packets.MicrophonePacket;
import com.garden.api.voicechat.plugins.impl.ServerPlayerImpl;
import com.garden.api.voicechat.plugins.impl.VoicechatServerApiImpl;
import com.garden.api.voicechat.voice.common.GroupSoundPacket;
import com.garden.api.voicechat.voice.server.ClientConnection;
import com.garden.api.voicechat.voice.server.Group;
import com.garden.api.voicechat.voice.server.Server;
import com.garden.api.voicechat.voice.server.ServerGroupManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

import java.util.*;

public class StaticAudioChannelImpl extends AudioChannelImpl implements StaticAudioChannel {

    protected boolean bypassGroupIsolation;
    protected final Set<UUID> targets;

    public StaticAudioChannelImpl(UUID channelId, Server server) {
        super(channelId, server);
        this.targets = new HashSet<>();
    }

    @Override
    public void send(byte[] opusData) {
        broadcast(new GroupSoundPacket(channelId, channelId, opusData, sequenceNumber.getAndIncrement(), category));
    }

    @Override
    public void send(MicrophonePacket packet) {
        send(packet.getOpusEncodedData());
    }

    @Override
    public void flush() {
        GroupSoundPacket packet = new GroupSoundPacket(channelId, channelId, new byte[0], sequenceNumber.getAndIncrement(), category);
        broadcast(packet);
    }

    private void broadcast(GroupSoundPacket packet) {
        synchronized (targets) {
            PlayerList playerList = server.getServer().getPlayerList();
            ServerGroupManager groupManager = server.getGroupManager();
            for (UUID target : targets) {
                ClientConnection connection = server.getConnection(target);
                if (connection == null) {
                    continue;
                }
                ServerPlayer player = playerList.getPlayer(target);
                if (player == null) {
                    continue;
                }
                if (!bypassGroupIsolation) {
                    Group playerGroup = groupManager.getPlayerGroup(player);
                    if (playerGroup != null && playerGroup.isIsolated()) {
                        continue;
                    }
                }
                if (filter != null) {
                    if (!filter.test(new ServerPlayerImpl(player))) {
                        continue;
                    }
                }
                VoicechatServerApiImpl.sendPacket(player, packet);
            }
        }
    }

    @Override
    public void setBypassGroupIsolation(boolean bypassGroupIsolation) {
        this.bypassGroupIsolation = bypassGroupIsolation;
    }

    @Override
    public boolean bypassesGroupIsolation() {
        return bypassGroupIsolation;
    }

    @Override
    public void addTarget(VoicechatConnection target) {
        synchronized (targets) {
            targets.add(target.getPlayer().getUuid());
        }
    }

    @Override
    public void removeTarget(VoicechatConnection target) {
        synchronized (targets) {
            targets.remove(target.getPlayer().getUuid());
        }
    }

    @Override
    public void clearTargets() {
        synchronized (targets) {
            targets.clear();
        }
    }
}
