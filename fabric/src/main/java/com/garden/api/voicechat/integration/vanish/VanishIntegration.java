package com.garden.api.voicechat.fabric.integration.vanish;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.fabric.events.VanishEvents;
import com.garden.api.voicechat.intercompatibility.CommonCompatibilityManager;
import net.minecraft.server.level.ServerPlayer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class VanishIntegration {

    private static Boolean loaded;

    public static boolean isLoaded() {
        if (loaded == null) {
            loaded = checkLoaded();
        }
        return loaded;
    }

    private static boolean checkLoaded() {
        if (CommonCompatibilityManager.INSTANCE.isModLoaded("melius-vanish") || CommonCompatibilityManager.INSTANCE.isModLoaded("vanish")) {
            try {
                Class.forName("me.drex.vanish.api.VanishAPI");
                Voicechat.LOGGER.info("Enabling vanish compatibility");
                return true;
            } catch (Throwable t) {
                Voicechat.LOGGER.warn("Failed to load vanish compatibility", t);
            }
        }
        return false;
    }

    public static void init() {
        if (!isLoaded()) {
            return;
        }
        try {
            Class<?> vanishEventsClass = Class.forName("me.drex.vanish.api.VanishEvents");
            Object event = vanishEventsClass.getField("VANISH_EVENT").get(null);
            Method register = event.getClass().getMethod("register", Class.forName("me.drex.vanish.api.VanishEvents$VanishEvent"));
            Class<?> callbackClass = register.getParameterTypes()[0];
            InvocationHandler handler = (proxy, method, args) -> {
                ServerPlayer vanishPlayer = (ServerPlayer) args[0];
                boolean vanish = (boolean) args[1];
                for (ServerPlayer player : vanishPlayer.getServer().getPlayerList().getPlayers()) {
                    if (vanish) {
                        if (CommonCompatibilityManager.INSTANCE.canSee(player, vanishPlayer)) {
                            continue;
                        }
                        VanishEvents.ON_VANISH.invoker().accept(vanishPlayer, player);
                    } else {
                        if (!CommonCompatibilityManager.INSTANCE.canSee(player, vanishPlayer)) {
                            continue;
                        }
                        VanishEvents.ON_UNVANISH.invoker().accept(vanishPlayer, player);
                    }
                }
                return null;
            };
            Object callback = Proxy.newProxyInstance(callbackClass.getClassLoader(), new Class[]{callbackClass}, handler);
            register.invoke(event, callback);
        } catch (Throwable t) {
            Voicechat.LOGGER.warn("Failed to use vanish compatibility", t);
            loaded = false;
        }
    }

    public static boolean canSee(ServerPlayer player, ServerPlayer other) {
        if (isLoaded()) {
            try {
                Class<?> vanishApiClass = Class.forName("me.drex.vanish.api.VanishAPI");
                Method canSeePlayer = vanishApiClass.getMethod("canSeePlayer", ServerPlayer.class, ServerPlayer.class);
                return (boolean) canSeePlayer.invoke(null, other, player);
            } catch (Throwable t) {
                Voicechat.LOGGER.warn("Failed to use vanish compatibility", t);
                loaded = false;
            }
        }
        return true;
    }

}
