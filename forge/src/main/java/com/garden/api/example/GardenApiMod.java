/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package com.garden.api.example;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import com.garden.api.example.registry.*;
import com.garden.api.GardenApi;

public final class GardenApiMod {
	public static final String DISABLE_EXAMPLES_PROPERTY_KEY = "garden_api.disable_examples";

    public GardenApiMod() {
        if (!shouldRegisterExamples()) {
            return;
        }

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityRegistry.ENTITIES.register(bus);
        ItemRegistry.ITEMS.register(bus);
        ItemRegistry.TABS.register(bus);
        BlockEntityRegistry.TILES.register(bus);
        BlockRegistry.BLOCKS.register(bus);
        SoundRegistry.SOUNDS.register(bus);
    }

	/**
	 * By default, GardenApi will register and activate several example entities,
	 * items, and blocks when in dev.<br>
	 * These examples are <u>not</u> present when in a production environment
	 * (normal players).<br>
	 * This can be disabled by setting the
	 * {@link GardenApiMod#DISABLE_EXAMPLES_PROPERTY_KEY} to false in your run args
	 */
	static boolean shouldRegisterExamples() {
		return !FMLEnvironment.production && !Boolean.getBoolean(DISABLE_EXAMPLES_PROPERTY_KEY);
	}
}
