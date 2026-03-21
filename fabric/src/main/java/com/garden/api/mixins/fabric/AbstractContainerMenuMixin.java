package com.garden.api.mixins.fabric;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import com.garden.api.animatable.GeoItem;

/**
 * Injection into the base container functionality to handle ItemStack duplication and splitting with GardenApi stack identifiers
 */
@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    /**
     * Remove the GardenApi stack ID from a stack when copying it with middle-click
     */
    @WrapOperation(method = "doClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copyWithCount(I)Lnet/minecraft/world/item/ItemStack;", ordinal = 1))
    public ItemStack garden_api$removeGeckolibIdOnCopy(ItemStack stack, int count, Operation<ItemStack> original) {
        ItemStack copy = original.call(stack, count);

        if (copy.hasTag() && copy.getTag().contains(GeoItem.ID_NBT_KEY))
            copy.getTag().remove(GeoItem.ID_NBT_KEY);

        return copy;
    }

    /**
     * Force ItemStacks that don't match their GardenApi stack ID to sync, even though GardenApi tells the game they're equivalent
     */
    @WrapOperation(method = "synchronizeSlotToRemote", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;matches(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"))
    public boolean garden_api$forceGeckolibIdSync(ItemStack stack, ItemStack other, Operation<Boolean> original) {
        return original.call(stack, other) && garden_api$xnorGeckolibStackIds(stack.getTag(), other.getTag());
    }

    /**
     * Force ItemStacks that don't match their GardenApi stack ID to trigger slot listeners, even though GardenApi tells the game they're equivalent
     */
    @WrapOperation(method = "triggerSlotListeners", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;matches(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"))
    public boolean garden_api$forceGeckolibSlotChange(ItemStack stack, ItemStack other, Operation<Boolean> original) {
        return original.call(stack, other) && garden_api$xnorGeckolibStackIds(stack.getTag(), other.getTag());
    }

    @Unique
    private static boolean garden_api$xnorGeckolibStackIds(@Nullable CompoundTag tag1, @Nullable CompoundTag tag2) {
        return (tag1 == null ? -1 : tag1.getInt(GeoItem.ID_NBT_KEY)) == (tag2 == null ? -1 : tag2.getInt(GeoItem.ID_NBT_KEY));
    }
}
