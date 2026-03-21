package com.garden.api.example.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.garden.api.GardenApi;

public final class SoundRegistry {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			GardenApi.MOD_ID);

	public static RegistryObject<SoundEvent> JACK_MUSIC = SOUNDS.register("jack_in_the_box_music",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(GardenApi.MOD_ID, "jack_in_the_box_music")));
}
