package com.garden.api.example.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.entity.DynamicExampleEntity;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedEntityGeoModel;
import com.garden.api.model.GeoModel;

/**
 * Example {@link GeoModel} for the {@link DynamicExampleEntity}
 * @see com.garden.api.example.client.renderer.entity.GremlinRenderer
 */
public class GremlinModel extends DefaultedEntityGeoModel<DynamicExampleEntity> {
	public GremlinModel() {
		super(new ResourceLocation(GardenApi.MOD_ID, "gremlin"));
	}
}