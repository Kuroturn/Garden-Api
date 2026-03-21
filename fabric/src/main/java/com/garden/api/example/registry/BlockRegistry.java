package com.garden.api.example.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import com.garden.api.example.block.FertilizerBlock;
import com.garden.api.example.block.GeckoHabitatBlock;
import com.garden.api.GardenApi;

public class BlockRegistry {

    public static final GeckoHabitatBlock GECKO_HABITAT_BLOCK = registerBlock("gecko_habitat", new GeckoHabitatBlock());
    public static final FertilizerBlock FERTILIZER_BLOCK = registerBlock("fertilizer", new FertilizerBlock());

    public static <B extends Block> B registerBlock(String name, B block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(GardenApi.MOD_ID, name), block);
    }
}
