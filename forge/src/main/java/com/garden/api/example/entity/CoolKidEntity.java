package com.garden.api.example.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import com.garden.api.animatable.GeoEntity;
import com.garden.api.constant.DefaultAnimations;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animation.AnimationController;
import com.garden.api.util.GardenApiUtil;

/**
 * Example {@link GeoAnimatable} implementation of an entity that uses a render layer
 */
public class CoolKidEntity extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GardenApiUtil.createInstanceCache(this);

    public CoolKidEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    // Add a goal to have the entity look at the player
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        super.registerGoals();
    }

    // Add a generic idle controller, with a 5-tick transition time
	@Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 5, state -> state.setAndContinue(DefaultAnimations.IDLE)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
