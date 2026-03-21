package com.garden.api.example.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import com.garden.api.GardenApi;
import com.garden.api.animatable.GeoItem;
import com.garden.api.core.animatable.instance.AnimatableInstanceCache;
import com.garden.api.core.animation.AnimatableManager;
import com.garden.api.model.DefaultedBlockGeoModel;
import com.garden.api.renderer.GeoItemRenderer;
import com.garden.api.util.GardenApiUtil;

import java.util.function.Consumer;

public class GeckoHabitatItem extends BlockItem implements GeoItem {
	private final AnimatableInstanceCache geoCache = GardenApiUtil.createInstanceCache(this);

	public GeckoHabitatItem(Block block, Properties properties) {
		super(block, properties);
	}

	// Utilise the existing forge hook to define our custom renderer (which we created in createRenderer)
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private GeoItemRenderer<GeckoHabitatItem> renderer = null;

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (this.renderer == null)
                    this.renderer = new GeoItemRenderer<>(new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(GardenApi.MOD_ID, "gecko_habitat")));

				return this.renderer;
			}
		});
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.geoCache;
	}
}
