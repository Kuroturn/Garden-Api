package com.garden.api.voicechat.fabric.mixin;

import com.garden.api.voicechat.fabric.events.PublishServerEvents;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin {

    @Inject(method = "publishServer", at = @At(value = "RETURN"))
    public void publishServer(@Nullable GameType gameType, boolean cheats, int port, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (!callbackInfo.getReturnValue()) {
            return;
        }
        PublishServerEvents.SERVER_PUBLISHED.invoker().accept(port);
    }

}
