package com.garden.api.voicechat.forge;

import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.forge.config.ConfigMigrator;
import com.garden.api.voicechat.intercompatibility.CommonCompatibilityManager;
import com.garden.api.voicechat.permission.PermissionManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Objects;

public class ForgeVoicechatMod extends Voicechat {

    public ForgeVoicechatMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ForgeVoicechatClientMod::new);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        initialize();
        MinecraftForge.EVENT_BUS.register(new ConfigMigrator());
        MinecraftForge.EVENT_BUS.register(CommonCompatibilityManager.INSTANCE);
        MinecraftForge.EVENT_BUS.register(PermissionManager.INSTANCE);
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> {
            return new IExtensionPoint.DisplayTest(() -> String.valueOf(Voicechat.COMPATIBILITY_VERSION), (incoming, isNetwork) -> {
                return Objects.equals(incoming, String.valueOf(Voicechat.COMPATIBILITY_VERSION));
            });
        });
    }

}
