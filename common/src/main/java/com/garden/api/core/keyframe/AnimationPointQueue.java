/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package com.garden.api.core.keyframe;

import java.io.Serial;
import java.util.LinkedList;

/**
 * An {@link AnimationPoint} queue holds a queue of {@code AnimationPoints} which are used in
 * the {@link com.garden.api.core.animation.AnimationController} to lerp between values
 */
public final class AnimationPointQueue extends LinkedList<AnimationPoint> {
	@Serial
	private static final long serialVersionUID = 5472797438476621193L;
}
