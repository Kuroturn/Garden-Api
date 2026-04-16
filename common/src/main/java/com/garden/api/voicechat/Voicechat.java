package com.garden.api.voicechat;

import de.maxhenkel.configbuilder.ConfigBuilder;
import com.garden.api.voicechat.command.VoicechatCommands;
import com.garden.api.voicechat.config.ServerConfig;
import com.garden.api.voicechat.config.Translations;
import com.garden.api.voicechat.intercompatibility.CommonCompatibilityManager;
import com.garden.api.voicechat.logging.Log4JVoicechatLogger;
import com.garden.api.voicechat.logging.VoicechatLogger;
import com.garden.api.voicechat.plugins.PluginManager;
import com.garden.api.voicechat.voice.server.ServerVoiceEvents;

import java.nio.file.Path;
import java.util.regex.Pattern;

public abstract class Voicechat {

    public static final String MODID = "voicechat";
    public static final VoicechatLogger LOGGER = new Log4JVoicechatLogger(MODID);
    public static ServerVoiceEvents SERVER;
    public static ServerConfig SERVER_CONFIG;
    public static Translations TRANSLATIONS;

    public static int COMPATIBILITY_VERSION = BuildConstants.COMPATIBILITY_VERSION;

    public static final int MAX_GROUP_NAME_LENGTH = 24;
    public static final Pattern GROUP_REGEX = Pattern.compile("^[^\\p{C}\\s][^\\p{C}]{0,23}$");

    public void initialize() {
        if (debugMode()) {
            LOGGER.warn("Running in debug mode - Don't leave this enabled in production!");
        }

        LOGGER.info("Compatibility version {}", COMPATIBILITY_VERSION);

        initializeConfigs();

        CommonCompatibilityManager.INSTANCE.getNetManager().init();
        SERVER = new ServerVoiceEvents();
        initPlugins();
        registerCommands();
    }

    protected void initPlugins() {
        PluginManager.instance().init();
    }

    protected void registerCommands() {
        CommonCompatibilityManager.INSTANCE.onRegisterServerCommands(VoicechatCommands::register);
    }

    public void initializeConfigs() {
        SERVER_CONFIG = ConfigBuilder.builder(ServerConfig::new).path(getVoicechatConfigFolderInternal().resolve("voicechat-server.properties")).build();
        TRANSLATIONS = ConfigBuilder.builder(Translations::new).path(getVoicechatConfigFolderInternal().resolve("translations.properties")).build();
    }

    public static boolean debugMode() {
        return CommonCompatibilityManager.INSTANCE.isDevEnvironment() || System.getProperty("voicechat.debug") != null;
    }

    protected Path getVoicechatConfigFolderInternal() {
        return getVoicechatConfigFolder();
    }

    public static Path getVoicechatConfigFolder() {
        return getConfigFolder().resolve(MODID);
    }

    public static Path getConfigFolder() {
        return Path.of(".").resolve("config");
    }

}
