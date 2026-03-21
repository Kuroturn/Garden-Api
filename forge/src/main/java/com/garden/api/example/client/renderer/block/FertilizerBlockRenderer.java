package com.garden.api.example.client.renderer.block;

import com.garden.api.example.block.entity.FertilizerBlockEntity;
import com.garden.api.example.client.model.block.FertilizerModel;
import com.garden.api.renderer.GeoBlockRenderer;

/**
 * Example {@link net.minecraft.world.level.block.entity.BlockEntity} renderer for {@link FertilizerBlockEntity}
 * @see FertilizerModel
 * @see FertilizerBlockEntity
 */
public class FertilizerBlockRenderer extends GeoBlockRenderer<FertilizerBlockEntity> {
	public FertilizerBlockRenderer() {
		super(new FertilizerModel());
	}
}
