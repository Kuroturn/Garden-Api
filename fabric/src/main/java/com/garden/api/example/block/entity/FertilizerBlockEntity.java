package com.garden.api.example.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.garden.api.example.client.renderer.block.FertilizerBlockRenderer;
import com.garden.api.example.registry.BlockEntityRegistry;
import com.garden.api.animatable.GeoBlockEntity;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animation.AnimationController;
import com.garden.api.core.animation.RawAnimation;
import com.garden.api.util.GardenApiUtil;

/**
 * Example {@link BlockEntity} implementation using a GardenApi model.
 * @see com.garden.api.example.client.model.block.FertilizerModel
 * @see FertilizerBlockRenderer
 */
public class FertilizerBlockEntity extends BlockEntity implements GeoBlockEntity {
	private final AnimatableInstanceCache cache = GardenApiUtil.createInstanceCache(this);

	// We statically instantiate our RawAnimations for efficiency, consistency, and error-proofing
	private static final RawAnimation FERTILIZER_ANIMS = RawAnimation.begin().thenPlay("fertilizer.deploy").thenLoop("fertilizer.idle");
	private static final RawAnimation BOTARIUM_ANIMS = RawAnimation.begin().thenPlay("botarium.deploy").thenLoop("botarium.idle");

	public FertilizerBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.FERTILIZER_BLOCK, pos, state);
	}

	// Let's set our animations up
	// For this one, we want it to play the "Fertilizer" animation set if it's raining,
	// or switch to a botarium if it's not.
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, state -> {
			if (state.getAnimatable().getLevel().isRaining()) {
				return state.setAndContinue(FERTILIZER_ANIMS);
			}
			else {
				return state.setAndContinue(BOTARIUM_ANIMS);
			}
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
