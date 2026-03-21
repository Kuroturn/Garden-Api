package com.garden.api.example.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import com.garden.api.GardenApi;

public final class SoundRegistry {

	public static SoundEvent JACK_MUSIC = Registry.register(BuiltInRegistries.SOUND_EVENT, "jack_in_the_box_music",
			SoundEvent.createFixedRangeEvent(new ResourceLocation(GardenApi.MOD_ID, "jack_in_the_box_music"), 0));

}
