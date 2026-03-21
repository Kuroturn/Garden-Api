package com.garden.api.mixin;

import com.garden.api.SoundPhysicsMod;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SoundEvent.class)
public class SoundEventMixin {

    @ModifyConstant(method = "getRange", constant = @Constant(floatValue = 16F), expect = 2)
    private float allowance1(float value) {
        if (!SoundPhysicsMod.CONFIG.enabled.get()) {
            return value;
        }
        return value * SoundPhysicsMod.CONFIG.soundDistanceAllowance.get();
    }

}

