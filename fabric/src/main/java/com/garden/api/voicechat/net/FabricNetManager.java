package com.garden.api.voicechat.fabric.net;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.net.Channel;
import com.garden.api.voicechat.net.ClientServerChannel;
import com.garden.api.voicechat.net.NetManager;
import com.garden.api.voicechat.net.Packet;
import com.garden.api.voicechat.net.RequestSecretPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class FabricNetManager extends NetManager {

    private final Set<ResourceLocation> packets;

    public FabricNetManager() {
        packets = new HashSet<>();
    }

    public Set<ResourceLocation> getPackets() {
        return packets;
    }

    @Override
    public <T extends Packet<T>> Channel<T> registerReceiver(Class<T> packetType, boolean toClient, boolean toServer) {
        ClientServerChannel<T> c = new ClientServerChannel<>();
        try {
            T dummyPacket = packetType.getDeclaredConstructor().newInstance();
            ResourceLocation identifier = dummyPacket.getIdentifier();
            packets.add(identifier);
            if (toServer) {
                ServerPlayNetworking.registerGlobalReceiver(identifier, (server, player, handler, buf, responseSender) -> {
                    try {
                        if (!Voicechat.SERVER.isCompatible(player) && !packetType.equals(RequestSecretPacket.class)) {
                            return;
                        }
                        T packet = packetType.getDeclaredConstructor().newInstance();
                        packet.fromBytes(buf);
                        c.onServerPacket(server, player, handler, packet);
                    } catch (Exception e) {
                        Voicechat.LOGGER.error("Failed to process packet", e);
                    }
                });
            }
            if (toClient && FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
                ClientPlayNetworking.registerGlobalReceiver(identifier, (client, handler, buf, responseSender) -> {
                    try {
                        T packet = packetType.getDeclaredConstructor().newInstance();
                        packet.fromBytes(buf);
                        client.execute(() -> c.onClientPacket(client, handler, packet));
                    } catch (Exception e) {
                        Voicechat.LOGGER.error("Failed to register packet receiver", e);
                    }
                });
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return c;
    }

}
