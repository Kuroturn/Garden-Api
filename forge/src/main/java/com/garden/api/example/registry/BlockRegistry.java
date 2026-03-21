package com.garden.api.example.registry;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.garden.api.example.block.FertilizerBlock;
import com.garden.api.example.block.GeckoHabitatBlock;
import com.garden.api.GardenApi;

public final class BlockRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			GardenApi.MOD_ID);

	public static final RegistryObject<GeckoHabitatBlock> GECKO_HABITAT = BLOCKS.register("gecko_habitat",
			GeckoHabitatBlock::new);
	public static final RegistryObject<FertilizerBlock> FERTILIZER = BLOCKS.register("fertilizer",
			FertilizerBlock::new);
}
