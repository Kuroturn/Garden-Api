package com.garden.api.example.client.renderer.item;

import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.item.JackInTheBoxItem;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedItemGeoModel;
import com.garden.api.renderer.GeoItemRenderer;

/**
 * Example {@link GeoItemRenderer} for {@link JackInTheBoxItem}
 */
public class JackInTheBoxRenderer extends GeoItemRenderer<JackInTheBoxItem> {
	public JackInTheBoxRenderer() {
		super(new DefaultedItemGeoModel<>(new ResourceLocation(GardenApi.MOD_ID, "jack_in_the_box")));
	}
}
