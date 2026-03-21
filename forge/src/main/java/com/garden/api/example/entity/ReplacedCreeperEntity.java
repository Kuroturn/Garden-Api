package com.garden.api.example.entity;

import net.minecraft.world.entity.EntityType;
import com.garden.api.animatable.GeoEntity;
import com.garden.api.animatable.GeoReplacedEntity;
import com.garden.api.constant.DefaultAnimations;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.util.GardenApiUtil;

/**
 * Replacement {@link net.minecraft.world.entity.monster.Creeper} {@link GeoEntity} to showcase
 * replacing the model and animations of an existing entity
 * @see com.garden.api.renderer.GeoReplacedEntityRenderer
 * @see com.garden.api.example.client.renderer.entity.ReplacedCreeperRenderer
 * @see com.garden.api.example.client.model.entity.ReplacedCreeperModel
 */
public class ReplacedCreeperEntity implements GeoReplacedEntity {
	private final AnimatableInstanceCache cache = GardenApiUtil.createInstanceCache(this);

	// Register the idle + walk animations for the entity.<br>
	// In this situation we're going to use a generic controller that is already built for us
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(DefaultAnimations.genericWalkIdleController(this));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	public EntityType<?> getReplacingEntityType() {
		return EntityType.CREEPER;
	}
}
