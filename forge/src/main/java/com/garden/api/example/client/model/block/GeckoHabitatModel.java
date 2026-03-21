// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GardenApiMod
// Paste this class into your mod and follow the documentation for GardenApiMod to use animations. You can find the documentation here: https://github.com/bernie-g/garden_api
// Blockbench plugin created by Gecko
package com.garden.api.example.client.model.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.block.entity.GeckoHabitatBlockEntity;
import com.garden.api.example.client.renderer.block.GeckoHabitatBlockRenderer;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedBlockGeoModel;
import com.garden.api.model.GeoModel;

/**
 * Example {@link GeoModel} for the {@link GeckoHabitatBlockEntity}
 * @see GeckoHabitatBlockEntity
 * @see GeckoHabitatBlockRenderer
 */
public class GeckoHabitatModel extends DefaultedBlockGeoModel<GeckoHabitatBlockEntity> {
	public GeckoHabitatModel() {
        super(ResourceLocation.fromNamespaceAndPath(GardenApi.MOD_ID, "gecko_habitat"));
	}

	@Override
	public RenderType getRenderType(GeckoHabitatBlockEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
