package com.garden.api.voicechat.voice.client;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.gui.group.JoinGroupList;
import com.garden.api.voicechat.intercompatibility.ClientCompatibilityManager;
import com.garden.api.voicechat.intercompatibility.CommonCompatibilityManager;
import com.garden.api.voicechat.net.ClientServerNetManager;
import com.garden.api.voicechat.voice.common.ClientGroup;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientGroupManager {

    private final Map<UUID, ClientGroup> groups;

    public ClientGroupManager() {
        groups = new ConcurrentHashMap<>();
        ClientServerNetManager.setClientListener(CommonCompatibilityManager.INSTANCE.getNetManager().addGroupChannel, (client, handler, packet) -> {
            groups.put(packet.getGroup().getId(), packet.getGroup());
            Voicechat.LOGGER.debug("Added group '{}' ({})", packet.getGroup().getName(), packet.getGroup().getId());
            JoinGroupList.update();
        });
        ClientServerNetManager.setClientListener(CommonCompatibilityManager.INSTANCE.getNetManager().removeGroupChannel, (client, handler, packet) -> {
            groups.remove(packet.getGroupId());
            Voicechat.LOGGER.debug("Removed group {}", packet.getGroupId());
            JoinGroupList.update();
        });
        ClientCompatibilityManager.INSTANCE.onDisconnect(this::clear);
    }

    @Nullable
    public ClientGroup getGroup(UUID id) {
        return groups.get(id);
    }

    public Collection<ClientGroup> getGroups() {
        return groups.values();
    }

    public void clear() {
        groups.clear();
    }

}
