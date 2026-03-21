package com.garden.api.example.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.garden.api.example.client.model.entity.RaceCarModel;
import com.garden.api.example.entity.RaceCarEntity;
import com.garden.api.renderer.GeoEntityRenderer;

/**
 * Example {@link com.garden.api.renderer.GeoRenderer} implementation of an entity
 * @see RaceCarModel
 * @see RaceCarEntity
 */
public class RaceCarRenderer extends GeoEntityRenderer<RaceCarEntity> {
	public RaceCarRenderer(EntityRendererProvider.Context context) {
		super(context, new RaceCarModel());
	}
}