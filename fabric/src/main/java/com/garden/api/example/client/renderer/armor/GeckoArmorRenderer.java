package com.garden.api.example.client.renderer.armor;

import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.item.GeckoArmorItem;
import com.garden.api.example.item.WolfArmorItem;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedItemGeoModel;
import com.garden.api.renderer.GeoArmorRenderer;
import com.garden.api.renderer.GeoRenderer;
import com.garden.api.renderer.layer.AutoGlowingGeoLayer;

/**
 * Example {@link GeoRenderer} for the {@link WolfArmorItem} example item
 */
public final class GeckoArmorRenderer extends GeoArmorRenderer<GeckoArmorItem> {
	public GeckoArmorRenderer() {
		super(new DefaultedItemGeoModel<>(new ResourceLocation(GardenApi.MOD_ID, "armor/gecko_armor")));

		addRenderLayer(new AutoGlowingGeoLayer<>(this));
	}
}
