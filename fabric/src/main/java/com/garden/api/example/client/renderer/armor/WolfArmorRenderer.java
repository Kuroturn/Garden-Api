package com.garden.api.example.client.renderer.armor;

import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.item.WolfArmorItem;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedItemGeoModel;
import com.garden.api.renderer.GeoArmorRenderer;
import com.garden.api.renderer.GeoRenderer;

/**
 * Example {@link GeoRenderer} for the {@link WolfArmorItem} example item
 */
public final class WolfArmorRenderer extends GeoArmorRenderer<WolfArmorItem> {
	public WolfArmorRenderer() {
		super(new DefaultedItemGeoModel<>(new ResourceLocation(GardenApi.MOD_ID, "armor/wolf_armor")));
	}
}
