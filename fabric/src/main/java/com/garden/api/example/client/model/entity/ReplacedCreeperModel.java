package com.garden.api.example.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.entity.ReplacedCreeperEntity;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedEntityGeoModel;

/**
 * Example {@link com.garden.api.model.GeoModel} for dynamically replacing an
 * existing entity's renderer with a GardenApi model (in this case, {@link net.minecraft.world.entity.monster.Creeper}
 * @see com.garden.api.renderer.GeoReplacedEntityRenderer
 * @see com.garden.api.example.client.renderer.entity.ReplacedCreeperRenderer
 */
public class ReplacedCreeperModel extends DefaultedEntityGeoModel<ReplacedCreeperEntity> {
	public ReplacedCreeperModel() {
		super(new ResourceLocation(GardenApi.MOD_ID, "creeper"));
	}
}
