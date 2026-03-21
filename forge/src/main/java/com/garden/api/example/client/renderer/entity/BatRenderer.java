package com.garden.api.example.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import org.joml.Vector3d;
import com.garden.api.example.client.model.entity.BatModel;
import com.garden.api.example.entity.BatEntity;
import com.garden.api.cache.object.BakedGeoModel;
import com.garden.api.renderer.GeoEntityRenderer;
import com.garden.api.renderer.layer.AutoGlowingGeoLayer;

/**
 * Example {@link com.garden.api.renderer.GeoRenderer} for {@link BatEntity}
 * @see BatModel
 */
public class BatRenderer extends GeoEntityRenderer<BatEntity> {
	private int currentTick = -1;

	public BatRenderer(EntityRendererProvider.Context context) {
		super(context, new BatModel());

		// Add the glow layer to the bat so that it can live out its dreams of being rudolph
		addRenderLayer(new AutoGlowingGeoLayer<>(this));
	}

	// Add some particles around the ear when rendering
	@Override
	public void renderFinal(PoseStack poseStack, BatEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.currentTick < 0 || this.currentTick != animatable.tickCount) {
			this.currentTick = animatable.tickCount;

			// Find the earbone and use it as the point of reference
			this.model.getBone("leftear").ifPresent(ear -> {
				RandomSource rand = animatable.getRandom();
				Vector3d earPos = ear.getWorldPosition();

				animatable.getCommandSenderWorld().addParticle(ParticleTypes.PORTAL,
						earPos.x(),
						earPos.y(),
						earPos.z(),
						rand.nextDouble() - 0.5D,
						-rand.nextDouble(),
						rand.nextDouble() - 0.5D);
			});
		}

		super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
