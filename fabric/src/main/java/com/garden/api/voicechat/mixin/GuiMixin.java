package com.garden.api.voicechat.fabric.mixin;

import com.garden.api.voicechat.fabric.events.RenderEvents;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Gui.class, priority = 999)
public class GuiMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderEffects(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    private void onHUDRender(GuiGraphics poseStack, float f, CallbackInfo ci) {
        RenderEvents.RENDER_HUD.invoker().accept(poseStack);
    }

}
