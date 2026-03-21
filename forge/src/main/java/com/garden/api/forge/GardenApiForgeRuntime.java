package com.garden.api.forge;

import com.garden.api.GardenApi;
import com.garden.api.network.GardenApiNetwork;

/**
 * Base class for Geckolib!<br>
 * Hello World!<br>
 * There's not much to really see here, but feel free to stay a while and have a snack or something.
 * @see com.garden.api.util.GardenApiUtil
 * @see <a href="https://github.com/bernie-g/garden_api/wiki/Getting-Started">GardenApi Wiki - Getting Started</a>
 */
public final class GardenApiForgeRuntime {
	private static volatile boolean hasInitialized;

	private GardenApiForgeRuntime() {
	}

	/**
	 * This method <u><b>MUST</b></u> be called in your mod's constructor or during {@code onInitializeClient} in Fabric/Quilt.<br>
	 * If shadowing {@code GardenApi}, you should instead call {@link GardenApi#shadowInit}
	 * Note that doing so will prevent {@link com.garden.api.renderer.GeoItemRenderer Items} from animating properly
	 */
	synchronized public static void initialize() {
		if (!hasInitialized) {
			GardenApiNetwork.init();
		}

		hasInitialized = true;
	}

	/**
	 * Call this method instead of {@link GardenApi#initialize} if you are shadowing the mod.
	 */
	synchronized public static void shadowInit() {
		hasInitialized = true;
	}
}
