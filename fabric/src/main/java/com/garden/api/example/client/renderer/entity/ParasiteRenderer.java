package com.garden.api.example.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.garden.api.example.client.model.entity.ParasiteModel;
import com.garden.api.example.entity.ParasiteEntity;
import com.garden.api.renderer.GeoEntityRenderer;

/**
 * Example {@link com.garden.api.renderer.GeoRenderer} implementation of an entity
 * @see ParasiteModel
 * @see ParasiteEntity
 */
public class ParasiteRenderer extends GeoEntityRenderer<ParasiteEntity> {
	public ParasiteRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ParasiteModel());
	}
}
