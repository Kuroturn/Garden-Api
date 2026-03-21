package com.garden.api.animatable.stateless;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animation.AnimationController;
import com.garden.api.core.animation.AnimationState;
import com.garden.api.core.animation.RawAnimation;
import com.garden.api.core.object.PlayState;

/**
 * Stateless wrapper for {@link AnimationController}
 *
 * @see StatelessAnimatable
 */
public class StatelessAnimationController extends AnimationController<GeoAnimatable> {
    @Nullable
    protected RawAnimation currentAnim = null;

    public StatelessAnimationController(GeoAnimatable animatable, String name) {
        super(animatable, name, state -> PlayState.STOP);
    }

    /**
     * Set the current animation for this controller
     * <p>
     * This will be used to handle the {@link AnimationState} at each render pass
     */
    public void setCurrentAnimation(@Nullable RawAnimation animation) {
        this.currentAnim = animation;
    }

    /**
     * Get the current animation state for this controller
     */
    @Nullable
    public RawAnimation getCurrentAnim() {
        return this.currentAnim;
    }

    @Override
    public AnimationStateHandler<GeoAnimatable> getStateHandler() {
        return this::overrideStateHandler;
    }

    @ApiStatus.Internal
    protected PlayState overrideStateHandler(AnimationState<GeoAnimatable> test) {
        return getCurrentAnim() == null ? PlayState.STOP : test.setAndContinue(getCurrentAnim());
    }
}
