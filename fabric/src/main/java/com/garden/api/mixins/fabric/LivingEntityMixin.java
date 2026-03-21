package com.garden.api.mixins.fabric;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import com.garden.api.animatable.GeoItem;

/**
 * Injection into the equipment change handling to allow for bypassing GardenApi ItemStack ID parity
 */
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    /**
     * In {@code ItemStackMixin#garden_api$skipGeckolibIdOnCompare}, we tell Minecraft to ignore the contents of GardenApi
     * stack ids for the purposes of ItemStack parity.
     * <p>
     * We temporarily reinstate it here so that the game syncs changes to this specific component
     */
    @WrapOperation(method = "equipmentHasChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;matches(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"))
    public boolean garden_api$allowLazyStackIdParity(ItemStack remoteStack, ItemStack localStack, Operation<Boolean> original) {
        return original.call(remoteStack, localStack) && garden_api$xnorGeckolibStackIds(remoteStack.getTag(), localStack.getTag());
    }

    @Unique
    private static boolean garden_api$xnorGeckolibStackIds(@Nullable CompoundTag tag1, @Nullable CompoundTag tag2) {
        return (tag1 == null ? -1 : tag1.getInt(GeoItem.ID_NBT_KEY)) == (tag2 == null ? -1 : tag2.getInt(GeoItem.ID_NBT_KEY));
    }
}
