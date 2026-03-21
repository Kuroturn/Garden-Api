package com.garden.api.core.keyframe.event;

import com.garden.api.core.animatable.GeoAnimatable;
import com.garden.api.core.animation.AnimationController;
import com.garden.api.core.keyframe.event.data.ParticleKeyframeData;

/**
 * The {@link KeyFrameEvent} specific to the {@link AnimationController#particleKeyframeHandler}.<br>
 * Called when a particle instruction keyframe is encountered
 */
public class ParticleKeyframeEvent<T extends GeoAnimatable> extends KeyFrameEvent<T, ParticleKeyframeData> {
	public ParticleKeyframeEvent(T animatable, double animationTick, AnimationController<T> controller, ParticleKeyframeData particleKeyFrameData) {
		super(animatable, animationTick, controller, particleKeyFrameData);
	}

	/**
	 * Get the {@link ParticleKeyframeData} relevant to this event call
	 */
	@Override
	public ParticleKeyframeData getKeyframeData() {
		return super.getKeyframeData();
	}
}
