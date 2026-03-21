package com.garden.api.example.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.garden.api.example.client.model.block.GeckoHabitatModel;
import com.garden.api.example.client.renderer.block.GeckoHabitatBlockRenderer;
import com.garden.api.example.registry.BlockEntityRegistry;
import com.garden.api.animatable.GeoBlockEntity;
import com.garden.api.constant.DefaultAnimations;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animation.AnimationController;
import com.garden.api.util.GardenApiUtil;

/**
 * Example {@link BlockEntity} implementation using a GardenApi model.
 * @see GeckoHabitatModel
 * @see GeckoHabitatBlockRenderer
 */
public class GeckoHabitatBlockEntity extends BlockEntity implements GeoBlockEntity {
	private final AnimatableInstanceCache cache = GardenApiUtil.createInstanceCache(this);

	public GeckoHabitatBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.GECKO_HABITAT.get(), pos, state);
	}

	// We just want a permanent idle animation happening here
	// But if it's day time we want him to take a nap
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, state -> {
			if (getLevel().getDayTime() > 23000 || getLevel().getDayTime() < 13000) {
				return state.setAndContinue(DefaultAnimations.REST);
			}
			else {
				return state.setAndContinue(DefaultAnimations.IDLE);
			}
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
