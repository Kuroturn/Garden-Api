package com.garden.api.voicechat.fabric.permission;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.permission.Permission;
import com.garden.api.voicechat.permission.PermissionManager;
import com.garden.api.voicechat.permission.PermissionType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;

import java.lang.reflect.Method;

public class FabricPermissionManager extends PermissionManager {

    @Override
    public Permission createPermissionInternal(String modId, String node, PermissionType type) {
        return new Permission() {
            @Override
            public boolean hasPermission(ServerPlayer player) {
                try {
                    if (isFabricPermissionsAPILoaded()) {
                        Class<?> permissionsClass = Class.forName("me.lucko.fabric.api.permissions.v0.Permissions");
                        Method check = permissionsClass.getMethod("check", ServerPlayer.class, String.class, boolean.class);
                        return (boolean) check.invoke(null, player, modId + "." + node, type.hasPermission(player));
                    }
                } catch (Throwable t) {
                    loaded = false;
                    Voicechat.LOGGER.warn("Failed to use fabric-permissions-api-v0", t);
                    Voicechat.LOGGER.info("Disabling fabric-permissions-api-v0 integration");
                }
                return type.hasPermission(player);
            }

            @Override
            public PermissionType getPermissionType() {
                return type;
            }
        };
    }

    private static Boolean loaded;

    private static boolean isFabricPermissionsAPILoaded() {
        if (loaded == null) {
            loaded = FabricLoader.getInstance().isModLoaded("fabric-permissions-api-v0");
            if (loaded) {
                Voicechat.LOGGER.info("Using Fabric Permissions API");
            }
        }
        return loaded;
    }

}
