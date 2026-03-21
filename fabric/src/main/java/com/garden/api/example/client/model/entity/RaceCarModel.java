package com.garden.api.example.client.model.entity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.client.renderer.entity.RaceCarRenderer;
import com.garden.api.example.entity.RaceCarEntity;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedEntityGeoModel;
import com.garden.api.model.GeoModel;

/**
 * Example {@link GeoModel} for the {@link RaceCarEntity}
 * @see RaceCarRenderer
 */
public class RaceCarModel extends DefaultedEntityGeoModel<RaceCarEntity> {
	public RaceCarModel() {
		super(new ResourceLocation(GardenApi.MOD_ID, "race_car"));
	}

	// We want our model to render using the translucent render type
	@Override
	public RenderType getRenderType(RaceCarEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}