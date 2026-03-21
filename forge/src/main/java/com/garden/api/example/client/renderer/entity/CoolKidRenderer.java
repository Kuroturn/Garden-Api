package com.garden.api.example.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.client.renderer.entity.layer.CoolKidGlassesLayer;
import com.garden.api.example.entity.CoolKidEntity;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedEntityGeoModel;
import com.garden.api.renderer.GeoEntityRenderer;

/**
 * Example {@link com.garden.api.renderer.GeoRenderer} implementation of an entity that uses a {@link com.garden.api.renderer.layer.GeoRenderLayer render layer}
 * @see CoolKidGlassesLayer
 * @see CoolKidEntity
 */
public class CoolKidRenderer extends GeoEntityRenderer<CoolKidEntity> {
	public CoolKidRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(GardenApi.MOD_ID, "cool_kid")));

		this.shadowRadius = 0.25f;

		// Add our render layer
		addRenderLayer(new CoolKidGlassesLayer(this));
    }
}
