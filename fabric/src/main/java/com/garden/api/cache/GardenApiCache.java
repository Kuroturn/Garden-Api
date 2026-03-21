package com.garden.api.cache;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import com.garden.api.GardenApi;
import com.garden.api.GardenApiException;
import com.garden.api.cache.object.BakedGeoModel;
import com.garden.api.core.animatable.model.CoreGeoModel;
import com.garden.api.loading.FileLoader;
import com.garden.api.loading.json.raw.Model;
import com.garden.api.loading.object.BakedAnimations;
import com.garden.api.loading.object.BakedModelFactory;
import com.garden.api.loading.object.GeometryTree;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Cache class for holding loaded {@link com.garden.api.core.animation.Animation Animations}
 * and {@link CoreGeoModel Models}
 */
public final class GardenApiCache {
	private static final Set<String> EXCLUDED_NAMESPACES = ObjectOpenHashSet.of("moreplayermodels", "customnpcs", "gunsrpg");

	private static Map<ResourceLocation, BakedAnimations> ANIMATIONS = Collections.emptyMap();
	private static Map<ResourceLocation, BakedGeoModel> MODELS = Collections.emptyMap();

	public static Map<ResourceLocation, BakedAnimations> getBakedAnimations() {
		if (!GardenApi.hasInitialized)
			throw new RuntimeException("GardenApi was never initialized! Please read the documentation!");

		return ANIMATIONS;
	}

	public static Map<ResourceLocation, BakedGeoModel> getBakedModels() {
		if (!GardenApi.hasInitialized)
			throw new RuntimeException("GardenApi was never initialized! Please read the documentation!");

		return MODELS;
	}

	public static void registerReloadListener() {
		Minecraft mc = Minecraft.getInstance();

		if (mc == null) {
			return;
		}

		if (!(mc.getResourceManager() instanceof ReloadableResourceManager resourceManager))
			throw new RuntimeException("GardenApi was initialized too early!");

		resourceManager.registerReloadListener(GardenApiCache::reload);
	}

	public static CompletableFuture<Void> reload(PreparationBarrier stage, ResourceManager resourceManager,
			ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor,
			Executor gameExecutor) {
		Map<ResourceLocation, BakedAnimations> animations = new Object2ObjectOpenHashMap<>();
		Map<ResourceLocation, BakedGeoModel> models = new Object2ObjectOpenHashMap<>();

		return CompletableFuture.allOf(
				loadAnimations(backgroundExecutor, resourceManager, animations::put),
				loadModels(backgroundExecutor, resourceManager, models::put))
				.thenCompose(stage::wait).thenAcceptAsync(empty -> {
					GardenApiCache.ANIMATIONS = animations;
					GardenApiCache.MODELS = models;
				}, gameExecutor);
	}

	private static CompletableFuture<Void> loadAnimations(Executor backgroundExecutor, ResourceManager resourceManager, BiConsumer<ResourceLocation, BakedAnimations> elementConsumer) {
		return loadResources(backgroundExecutor, resourceManager, "animations", resource -> {
			try {
				return FileLoader.loadAnimationsFile(resource, resourceManager);
			}
			catch (Exception ex) {
				throw new GardenApiException(resource, "Error loading animation file", ex);
			}
		}, elementConsumer);
	}

	private static CompletableFuture<Void> loadModels(Executor backgroundExecutor, ResourceManager resourceManager, BiConsumer<ResourceLocation, BakedGeoModel> elementConsumer) {
		return loadResources(backgroundExecutor, resourceManager, "geo", resource -> {
			try {
				Model model = FileLoader.loadModelFile(resource, resourceManager);

				if (model.formatVersion() == null) {
					GardenApi.LOGGER.warn("Unsupported geometry json version for model {}. Supported versions: 1.12.0", resource);
				}
				else {
					switch (model.formatVersion()) {
						case V_1_12_0 -> {}
						case V_1_14_0 -> GardenApi.LOGGER.warn("Unsupported geometry json version: 1.14.0 for model {}. This model may not appear as expected", resource);
						case V_1_21_0 -> GardenApi.LOGGER.warn("Unsupported geometry json version: 1.21.0 for model {}. Supported versions: 1.12.0. Remove any rotated face UVs and re-export the model to fix", resource);
						case V_1_21_2 -> GardenApi.LOGGER.warn("Unsupported geometry json version: 1.21.2 for model {}. Supported versions: 1.12.0. Remove any rotated face UVs and re-export the model to fix", resource);
						default -> GardenApi.LOGGER.warn("Unsupported geometry json version for model {}. Supported versions: 1.12.0", resource);
					}
				}

				return BakedModelFactory.getForNamespace(resource.getNamespace()).constructGeoModel(GeometryTree.fromModel(model));
			}
			catch (Exception ex) {
				throw new GardenApiException(resource, "Error loading model file", ex);
			}
		}, elementConsumer);
	}

	private static <T> CompletableFuture<Void> loadResources(Executor executor, ResourceManager resourceManager,
			String path, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map) {
		return CompletableFuture.supplyAsync(
				() -> resourceManager.listResources(path, fileName -> fileName.toString().endsWith(".json")), executor)
				.thenApplyAsync(resources -> {
					Map<ResourceLocation, CompletableFuture<T>> tasks = new Object2ObjectOpenHashMap<>();

					for (ResourceLocation resource : resources.keySet()) {
						tasks.put(resource, CompletableFuture.supplyAsync(() -> loader.apply(resource), executor));
					}

					return tasks;
				}, executor)
				.thenAcceptAsync(tasks -> {
					for (Entry<ResourceLocation, CompletableFuture<T>> entry : tasks.entrySet()) {
						// Skip known namespaces that use an "animation" folder as well
						if (!EXCLUDED_NAMESPACES.contains(entry.getKey().getNamespace().toLowerCase(Locale.ROOT)))
							map.accept(entry.getKey(), entry.getValue().join());
					}
				}, executor);
	}
}
