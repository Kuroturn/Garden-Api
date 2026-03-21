package com.garden.api.example.client.model.entity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.client.renderer.entity.ParasiteRenderer;
import com.garden.api.example.entity.ParasiteEntity;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedEntityGeoModel;
import com.garden.api.model.GeoModel;

/**
 * Example {@link GeoModel} for the {@link ParasiteEntity}
 * @see ParasiteRenderer
 */
public class ParasiteModel extends DefaultedEntityGeoModel<ParasiteEntity> {
	public ParasiteModel() {
		super(new ResourceLocation(GardenApi.MOD_ID, "parasite"));
	}

	// We want our model to render using the translucent render type
	@Override
	public RenderType getRenderType(ParasiteEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}