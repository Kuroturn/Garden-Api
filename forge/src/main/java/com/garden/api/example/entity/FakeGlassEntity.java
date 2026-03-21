package com.garden.api.example.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import com.garden.api.example.client.model.entity.FakeGlassModel;
import com.garden.api.example.client.renderer.entity.FakeGlassRenderer;
import com.garden.api.animatable.GeoEntity;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.renderer.DynamicGeoEntityRenderer;
import com.garden.api.util.GardenApiUtil;

/**
 * Example {@link GeoAnimatable} implementation of an entity that uses the texture-per-bone feature of
 * {@link DynamicGeoEntityRenderer}
 * @see FakeGlassModel
 * @see FakeGlassRenderer
 */
public class FakeGlassEntity extends PathfinderMob implements GeoEntity {
	private final AnimatableInstanceCache cache = GardenApiUtil.createInstanceCache(this);

	public FakeGlassEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
	}

	// We don't care about animations for this one
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
