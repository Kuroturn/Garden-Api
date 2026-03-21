package com.garden.api.mixins.fabric;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import com.garden.api.animatable.GeoItem;

import java.util.Objects;

/**
 * Injection into ItemStack functionality to handle duplication and splitting with GardenApi stack identifiers
 */
@Mixin(ItemStack.class)
public class ItemStackMixin {
    /**
     * Remove the GardenApi stack ID when splitting up a stack into two
     */
    @WrapOperation(method = "split", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copyWithCount(I)Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack garden_api$removeGeckolibIdOnCopy(ItemStack instance, int count, Operation<ItemStack> original) {
        ItemStack copy = original.call(instance, count);

        if (count < instance.getCount())
            copy.removeTagKey(GeoItem.ID_NBT_KEY);

        return copy;
    }

    /**
     * Consider ItemStacks equal if the only difference is their GardenApi stack ID
     */
    @WrapOperation(method = "isSameItemSameTags", at = @At(value = "INVOKE", target = "Ljava/util/Objects;equals(Ljava/lang/Object;Ljava/lang/Object;)Z"))
    private static boolean garden_api$skipGeckolibIdOnCompare(Object a, Object b, Operation<Boolean> original) {
        if (original.call(a, b))
            return true;

        if (!(a instanceof CompoundTag tag1) || !(b instanceof CompoundTag tag2))
            return false;

        return garden_api$areTagsMatchingIgnoringGardenApiId(tag1, tag2);
    }

    /**
     * Perform an {@link Object#equals(Object)} check on two {@link CompoundTag}s,
     * ignoring any GardenApi stack ids that may be present.
     */
    @Unique
    private static boolean garden_api$areTagsMatchingIgnoringGardenApiId(CompoundTag tag1, CompoundTag tag2) {
        boolean patched = false;

        if (tag1.contains(GeoItem.ID_NBT_KEY)) {
            tag1 = tag1.copy();
            patched = true;

            tag1.remove(GeoItem.ID_NBT_KEY);
        }

        if (tag2.contains(GeoItem.ID_NBT_KEY)) {
            tag2 = tag2.copy();
            patched = true;

            tag2.remove(GeoItem.ID_NBT_KEY);
        }

        return patched && Objects.equals(tag1, tag2);
    }
}
