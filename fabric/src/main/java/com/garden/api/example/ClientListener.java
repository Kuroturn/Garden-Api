package com.garden.api.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.EntityType;
import com.garden.api.example.client.renderer.block.FertilizerBlockRenderer;
import com.garden.api.example.client.renderer.block.GeckoHabitatBlockRenderer;
import com.garden.api.example.client.renderer.entity.BatRenderer;
import com.garden.api.example.client.renderer.entity.BikeRenderer;
import com.garden.api.example.client.renderer.entity.CoolKidRenderer;
import com.garden.api.example.client.renderer.entity.FakeGlassRenderer;
import com.garden.api.example.client.renderer.entity.GremlinRenderer;
import com.garden.api.example.client.renderer.entity.MutantZombieRenderer;
import com.garden.api.example.client.renderer.entity.ParasiteRenderer;
import com.garden.api.example.client.renderer.entity.RaceCarRenderer;
import com.garden.api.example.client.renderer.entity.ReplacedCreeperRenderer;
import com.garden.api.example.registry.BlockEntityRegistry;
import com.garden.api.example.registry.BlockRegistry;
import com.garden.api.example.registry.EntityRegistry;
import com.garden.api.network.GardenApiNetwork;

public final class ClientListener implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		if (GardenApiMod.shouldRegisterExamples())
			registerRenderers();
		registerNetwork();
	}

	private static void registerRenderers() {
		EntityRendererRegistry.register(EntityRegistry.BAT, BatRenderer::new);
		EntityRendererRegistry.register(EntityRegistry.BIKE, BikeRenderer::new);
		EntityRendererRegistry.register(EntityRegistry.RACE_CAR, RaceCarRenderer::new);

		EntityRendererRegistry.register(EntityRegistry.PARASITE, ParasiteRenderer::new);
		EntityRendererRegistry.register(EntityRegistry.COOL_KID, CoolKidRenderer::new);
		EntityRendererRegistry.register(EntityRegistry.MUTANT_ZOMBIE, MutantZombieRenderer::new);
		EntityRendererRegistry.register(EntityRegistry.GREMLIN, GremlinRenderer::new);

		EntityRendererRegistry.register(EntityRegistry.FAKE_GLASS, FakeGlassRenderer::new);
		EntityRendererRegistry.register(EntityType.CREEPER, ReplacedCreeperRenderer::new);

		BlockEntityRendererRegistry.register(BlockEntityRegistry.GECKO_HABITAT,
				context -> new GeckoHabitatBlockRenderer());
		BlockEntityRendererRegistry.register(BlockEntityRegistry.FERTILIZER_BLOCK,
				context -> new FertilizerBlockRenderer());

		BlockRenderLayerMapImpl.INSTANCE.putBlock(BlockRegistry.GECKO_HABITAT_BLOCK, RenderType.translucent());
	}

	private static void registerNetwork() {
		GardenApiNetwork.registerClientReceiverPackets();
	}
}
