package com.garden.api.voicechat;

import com.sun.jna.Platform;
import de.maxhenkel.configbuilder.ConfigBuilder;
import com.garden.api.voicechat.config.CategoryVolumeConfig;
import com.garden.api.voicechat.config.ClientConfig;
import com.garden.api.voicechat.config.PlayerVolumeConfig;
import com.garden.api.voicechat.integration.clothconfig.ClothConfig;
import com.garden.api.voicechat.intercompatibility.ClientCompatibilityManager;
import com.garden.api.voicechat.intercompatibility.CommonCompatibilityManager;
import com.garden.api.voicechat.macos.VersionCheck;
import com.garden.api.voicechat.natives.LameManager;
import com.garden.api.voicechat.natives.OpusManager;
import com.garden.api.voicechat.natives.RNNoiseManager;
import com.garden.api.voicechat.natives.SpeexManager;
import com.garden.api.voicechat.profile.UsernameCache;
import com.garden.api.voicechat.resourcepacks.VoiceChatResourcePack;
import com.garden.api.voicechat.voice.client.ClientManager;
import com.garden.api.voicechat.voice.client.KeyEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public abstract class VoicechatClient {

    public static ClientConfig CLIENT_CONFIG;
    public static PlayerVolumeConfig PLAYER_VOLUME_CONFIG;
    public static CategoryVolumeConfig CATEGORY_VOLUME_CONFIG;
    public static UsernameCache USERNAME_CACHE;

    public static VoiceChatResourcePack CLASSIC_ICONS;
    public static VoiceChatResourcePack WHITE_ICONS;
    public static VoiceChatResourcePack BLACK_ICONS;

    public VoicechatClient() {
        KeyEvents.registerKeyBinds();

        CLASSIC_ICONS = new VoiceChatResourcePack("classic_icons");
        WHITE_ICONS = new VoiceChatResourcePack("white_icons");
        BLACK_ICONS = new VoiceChatResourcePack("black_icons");

        Minecraft mc = Minecraft.getInstance();
        // Don't add a pack source while datagen is running
        if (mc != null) {
            ClientCompatibilityManager.INSTANCE.addResourcePackSource(mc.getResourcePackRepository(), consumer -> {
                consumer.accept(CLASSIC_ICONS.toPack(Component.translatable("resourcepack.voicechat.classic_icons")));
                consumer.accept(WHITE_ICONS.toPack(Component.translatable("resourcepack.voicechat.white_icons")));
                consumer.accept(BLACK_ICONS.toPack(Component.translatable("resourcepack.voicechat.black_icons")));
            });
        }

    }

    public void initializeConfigs() {
        CLIENT_CONFIG = ConfigBuilder.builder(ClientConfig::new).path(Voicechat.getVoicechatConfigFolder().resolve("voicechat-client.properties")).migration(ClientConfig::migrate).build();
        PLAYER_VOLUME_CONFIG = new PlayerVolumeConfig(Voicechat.getVoicechatConfigFolder().resolve("player-volumes.properties"));
        CATEGORY_VOLUME_CONFIG = new CategoryVolumeConfig(Voicechat.getVoicechatConfigFolder().resolve("category-volumes.properties"));
        USERNAME_CACHE = new UsernameCache(Voicechat.getVoicechatConfigFolder().resolve("username-cache.json").toFile());
    }

    public void initializeClient() {
        initializeConfigs();

        //Load instance
        ClientManager.instance();

        ClothConfig.init();

        OpusManager.init();
        RNNoiseManager.init();
        SpeexManager.init();
        LameManager.init();

        if (Platform.isMac()) {
            if (!VersionCheck.isMacOSNativeCompatible()) {
                Voicechat.LOGGER.warn("Your MacOS version is incompatible with {}", CommonCompatibilityManager.INSTANCE.getModName());
            }
        }
    }
}
