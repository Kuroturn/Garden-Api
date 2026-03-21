package com.garden.api.example.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import com.garden.api.animatable.GeoEntity;
import com.garden.api.constant.DefaultAnimations;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animation.AnimationController;
import com.garden.api.util.ClientUtils;
import com.garden.api.util.GardenApiUtil;

/**
 * Example {@link GeoAnimatable} implementation of an entity
 * @see com.garden.api.example.client.renderer.entity.BatRenderer
 * @see com.garden.api.example.client.model.entity.BatModel
 */
public class BatEntity extends PathfinderMob implements GeoEntity {
	private final AnimatableInstanceCache cache = GardenApiUtil.createInstanceCache(this);

	private boolean isFlying = false;

	public BatEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
		super(type, worldIn);
	}

	// Have the bat look at the player
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 12.0F));
		super.registerGoals();
	}

	// Adds a right-click toggle that turns on/off its animating pose
	@Override
	public InteractionResult interactAt(Player player, Vec3 hitPos, InteractionHand hand) {
		if (hand == InteractionHand.MAIN_HAND)
			this.isFlying = !this.isFlying;

		return super.interactAt(player, hitPos, hand);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(
				// Add our flying animation controller
				new AnimationController<>(this, 10, state -> state.setAndContinue(this.isFlying ? DefaultAnimations.FLY : DefaultAnimations.IDLE))
						// Handle the custom instruction keyframe that is part of our animation json
						.setCustomInstructionKeyframeHandler(state -> {
							Player player = ClientUtils.getClientPlayer();

							if (player != null)
								player.displayClientMessage(Component.literal("KeyFraming"), true);
						}),
				// Add our generic living animation controller
				DefaultAnimations.genericLivingController(this)
		);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
