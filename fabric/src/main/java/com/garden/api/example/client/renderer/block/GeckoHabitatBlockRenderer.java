package com.garden.api.example.client.renderer.block;

import com.garden.api.example.block.entity.GeckoHabitatBlockEntity;
import com.garden.api.example.client.model.block.GeckoHabitatModel;
import com.garden.api.renderer.GeoBlockRenderer;

/**
 * Example {@link net.minecraft.world.level.block.entity.BlockEntity} renderer for {@link GeckoHabitatBlockEntity}
 * @see GeckoHabitatModel
 * @see GeckoHabitatBlockEntity
 */
public class GeckoHabitatBlockRenderer extends GeoBlockRenderer<GeckoHabitatBlockEntity> {
	public GeckoHabitatBlockRenderer() {
		super(new GeckoHabitatModel());
	}
}
