package com.garden.api.example.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.garden.api.example.client.model.entity.BikeModel;
import com.garden.api.example.entity.BikeEntity;
import com.garden.api.renderer.GeoEntityRenderer;

/**
 * Example {@link com.garden.api.renderer.GeoRenderer} for {@link BikeEntity}
 * @see BikeModel
 */
public class BikeRenderer extends GeoEntityRenderer<BikeEntity> {
	public BikeRenderer(EntityRendererProvider.Context context) {
		super(context, new BikeModel());
	}
}
