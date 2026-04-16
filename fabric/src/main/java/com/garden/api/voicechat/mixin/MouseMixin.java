package com.garden.api.voicechat.fabric.mixin;

import com.garden.api.voicechat.fabric.events.InputEvents;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseMixin {

    @Inject(at = @At("HEAD"), method = "onPress")
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo info) {
        InputEvents.MOUSE_KEY.invoker().onMouseEvent(window, button, action, mods);
    }

}
