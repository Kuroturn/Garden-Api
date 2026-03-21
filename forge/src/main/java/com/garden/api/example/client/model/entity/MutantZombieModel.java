package com.garden.api.example.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import com.garden.api.example.client.renderer.entity.MutantZombieRenderer;
import com.garden.api.example.entity.DynamicExampleEntity;
import com.garden.api.GardenApi;
import com.garden.api.model.DefaultedEntityGeoModel;
import com.garden.api.model.GeoModel;

/**
 * Example {@link GeoModel} for the {@link DynamicExampleEntity}
 * @see MutantZombieRenderer
 */
public class MutantZombieModel extends DefaultedEntityGeoModel<DynamicExampleEntity> {
	public MutantZombieModel() {
        super(ResourceLocation.fromNamespaceAndPath(GardenApi.MOD_ID, "mutant_zombie"));
	}
}
