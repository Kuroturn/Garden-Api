package com.garden.api.example.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.client.renderer.entity.BatRenderer;
import com.garden.api.example.entity.BatEntity;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedEntityGeoModel;
import com.garden.api.model.GeoModel;

/**
 * Example {@link GeoModel} for the {@link BatEntity}
 * @see BatRenderer
 */
public class BatModel extends DefaultedEntityGeoModel<BatEntity> {
	// We use the alternate super-constructor here to tell the model it should handle head-turning for us
	public BatModel() {
		super(new ResourceLocation(GardenApi.MOD_ID, "bat"), true);
	}
}
