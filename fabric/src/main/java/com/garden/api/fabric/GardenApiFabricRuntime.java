/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package com.garden.api.fabric;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import com.garden.api.GardenApi;
import com.garden.api.cache.GardenApiCache;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class GardenApiFabricRuntime {
	private static boolean hasInitialized;

	private GardenApiFabricRuntime() {
	}

	public static void initialize() {
		if (!hasInitialized) {
			ResourceManagerHelper.get(PackType.CLIENT_RESOURCES)
					.registerReloadListener(new IdentifiableResourceReloadListener() {
						@Override
						public ResourceLocation getFabricId() {
							return new ResourceLocation(GardenApi.MOD_ID, "models");
						}

						@Override
						public CompletableFuture<Void> reload(PreparationBarrier synchronizer, ResourceManager manager,
								ProfilerFiller prepareProfiler, ProfilerFiller applyProfiler, Executor prepareExecutor,
								Executor applyExecutor) {
							return GardenApiCache.reload(synchronizer, manager, prepareProfiler,
									applyProfiler, prepareExecutor, applyExecutor);
						}
					});
		}
		hasInitialized = true;
	}
}
