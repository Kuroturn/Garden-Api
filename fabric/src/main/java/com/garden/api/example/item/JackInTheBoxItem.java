package com.garden.api.example.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.garden.api.example.client.renderer.item.JackInTheBoxRenderer;
import com.garden.api.example.registry.SoundRegistry;
import com.garden.api.animatable.GeoItem;
import com.garden.api.animatable.SingletonGeoAnimatable;
import com.garden.api.animatable.client.RenderProvider;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.core.animation.AnimationController;
import com.garden.api.core.animation.RawAnimation;
import com.garden.api.core.object.PlayState;
import com.garden.api.util.ClientUtils;
import com.garden.api.util.GardenApiUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Example {@link GeoItem} implementation in the form of a Jack-in-the-Box.<br>
 */
public final class JackInTheBoxItem extends Item implements GeoItem {
	private static final RawAnimation POPUP_ANIM = RawAnimation.begin().thenPlay("use.popup");
	private final AnimatableInstanceCache cache = GardenApiUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public JackInTheBoxItem(Properties properties) {
		super(properties);

		// Register our item as server-side handled.
		// This enables both animation data syncing and server-side animation triggering
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	// Utilise our own render hook to define our custom renderer
	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private JackInTheBoxRenderer renderer;

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (this.renderer == null)
					this.renderer = new JackInTheBoxRenderer();

				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

	// Let's add our animation controller
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "popup_controller", 20, state -> PlayState.STOP)
				.triggerableAnim("box_open", POPUP_ANIM)
				// We've marked the "box_open" animation as being triggerable from the server
				.setSoundKeyframeHandler(event -> {
					// Use helper method to avoid client-code in common class
					Player player = ClientUtils.getClientPlayer();

					if (player != null)
						player.playSound(SoundRegistry.JACK_MUSIC, 1, 1);
				}));
	}

	// Let's handle our use method so that we activate the animation when right-clicking while holding the box
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (level instanceof ServerLevel serverLevel)
			triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "popup_controller", "box_open");

		return super.use(level, player, hand);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
