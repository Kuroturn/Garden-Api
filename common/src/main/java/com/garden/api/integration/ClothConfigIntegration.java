package com.garden.api.integration;

import java.util.LinkedHashMap;
import java.util.Map;

import com.garden.api.Loggers;
import com.garden.api.SoundPhysicsMod;
import com.garden.api.config.blocksound.BlockDefinition;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.FloatListEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Method;

public class ClothConfigIntegration {

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder
                .create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("cloth_config.garden_api.settings"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("cloth_config.garden_api.category.general"));

        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.enabled"),
                Component.translatable("cloth_config.garden_api.enabled.description"),
                SoundPhysicsMod.CONFIG.enabled
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.simple_voice_chat_integration"),
                Component.translatable("cloth_config.garden_api.simple_voice_chat_integration.description"),
                SoundPhysicsMod.CONFIG.simpleVoiceChatIntegration
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.simple_voice_chat_hear_self"),
                Component.translatable("cloth_config.garden_api.simple_voice_chat_hear_self.description"),
                SoundPhysicsMod.CONFIG.hearSelf
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.attenuation_factor"),
                Component.translatable("cloth_config.garden_api.attenuation_factor.description"),
                SoundPhysicsMod.CONFIG.attenuationFactor
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.reverb_attenuation_distance"),
                Component.translatable("cloth_config.garden_api.reverb_attenuation_distance.description"),
                SoundPhysicsMod.CONFIG.reverbAttenuationDistance
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.reverb_gain"),
                Component.translatable("cloth_config.garden_api.reverb_gain.description"),
                SoundPhysicsMod.CONFIG.reverbGain
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.reverb_brightness"),
                Component.translatable("cloth_config.garden_api.reverb_brightness.description"),
                SoundPhysicsMod.CONFIG.reverbBrightness
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.reverb_distance"),
                Component.translatable("cloth_config.garden_api.reverb_distance.description"),
                SoundPhysicsMod.CONFIG.reverbDistance
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.block_absorption"),
                Component.translatable("cloth_config.garden_api.block_absorption.description"),
                SoundPhysicsMod.CONFIG.blockAbsorption
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.occlusion_variation"),
                Component.translatable("cloth_config.garden_api.occlusion_variation.description"),
                SoundPhysicsMod.CONFIG.occlusionVariation
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.default_block_reflectivity"),
                Component.translatable("cloth_config.garden_api.default_block_reflectivity.description"),
                SoundPhysicsMod.CONFIG.defaultBlockReflectivity
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.default_block_occlusion_factor"),
                Component.translatable("cloth_config.garden_api.default_block_occlusion_factor.description"),
                SoundPhysicsMod.CONFIG.defaultBlockOcclusionFactor
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.sound_distance_allowance"),
                Component.translatable("cloth_config.garden_api.sound_distance_allowance.description"),
                SoundPhysicsMod.CONFIG.soundDistanceAllowance
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.air_absorption"),
                Component.translatable("cloth_config.garden_api.air_absorption.description"),
                SoundPhysicsMod.CONFIG.airAbsorption
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.underwater_filter"),
                Component.translatable("cloth_config.garden_api.underwater_filter.description"),
                SoundPhysicsMod.CONFIG.underwaterFilter
        ));
        general.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.evaluate_ambient_sounds"),
                Component.translatable("cloth_config.garden_api.evaluate_ambient_sounds.description"),
                SoundPhysicsMod.CONFIG.evaluateAmbientSounds
        ));

        ConfigCategory performance = builder.getOrCreateCategory(Component.translatable("cloth_config.garden_api.category.performance"));

        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.environment_evaluation_ray_count"),
                Component.translatable("cloth_config.garden_api.environment_evaluation_ray_count.description"),
                SoundPhysicsMod.CONFIG.environmentEvaluationRayCount
        ));
        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.environment_evaluation_ray_bounces"),
                Component.translatable("cloth_config.garden_api.environment_evaluation_ray_bounces.description"),
                SoundPhysicsMod.CONFIG.environmentEvaluationRayBounces
        ));
        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.non_full_block_occlusion_factor"),
                Component.translatable("cloth_config.garden_api.non_full_block_occlusion_factor.description"),
                SoundPhysicsMod.CONFIG.nonFullBlockOcclusionFactor
        ));
        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.max_occlusion_rays"),
                Component.translatable("cloth_config.garden_api.max_occlusion_rays.description"),
                SoundPhysicsMod.CONFIG.maxOcclusionRays
        ));
        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.max_occlusion"),
                Component.translatable("cloth_config.garden_api.max_occlusion.description"),
                SoundPhysicsMod.CONFIG.maxOcclusion
        ));
        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.strict_occlusion"),
                Component.translatable("cloth_config.garden_api.strict_occlusion.description"),
                SoundPhysicsMod.CONFIG.strictOcclusion
        ));
        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.sound_direction_evaluation"),
                Component.translatable("cloth_config.garden_api.sound_direction_evaluation.description"),
                SoundPhysicsMod.CONFIG.soundDirectionEvaluation
        ));
        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.redirect_non_occluded_sounds"),
                Component.translatable("cloth_config.garden_api.redirect_non_occluded_sounds.description"),
                SoundPhysicsMod.CONFIG.redirectNonOccludedSounds
        ));
        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.update_moving_sounds"),
                Component.translatable("cloth_config.garden_api.update_moving_sounds.description"),
                SoundPhysicsMod.CONFIG.updateMovingSounds
        ));
        performance.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.sound_update_interval"),
                Component.translatable("cloth_config.garden_api.sound_update_interval.description"),
                SoundPhysicsMod.CONFIG.soundUpdateInterval
        ));

        ConfigCategory reflectivity = builder.getOrCreateCategory(Component.translatable("cloth_config.garden_api.category.reflectivity"));

        Map<BlockDefinition, Float> defaultReflectivityMap = new LinkedHashMap<>();
        SoundPhysicsMod.REFLECTIVITY_CONFIG.addDefaults(defaultReflectivityMap);

        for (Map.Entry<BlockDefinition, Float> entry : SoundPhysicsMod.REFLECTIVITY_CONFIG.getBlockDefinitions().entrySet()) {
            FloatListEntry e = entryBuilder
                    .startFloatField(entry.getKey().getName(), entry.getValue())
                    .setMin(0.01F)
                    .setMax(10F)
                    .setDefaultValue(defaultReflectivityMap.getOrDefault(entry.getKey(), SoundPhysicsMod.CONFIG.defaultBlockReflectivity.get()))
                    .setSaveConsumer(value -> SoundPhysicsMod.REFLECTIVITY_CONFIG.setBlockDefinitionValue(entry.getKey(), value)).build();
            reflectivity.addEntry(e);
        }

        ConfigCategory occlusion = builder.getOrCreateCategory(Component.translatable("cloth_config.garden_api.category.occlusion"));

        Map<BlockDefinition, Float> defaultOcclusionMap = new LinkedHashMap<>();
        SoundPhysicsMod.OCCLUSION_CONFIG.addDefaults(defaultOcclusionMap);

        for (Map.Entry<BlockDefinition, Float> entry : SoundPhysicsMod.OCCLUSION_CONFIG.getBlockDefinitions().entrySet()) {
            FloatListEntry e = entryBuilder
                    .startFloatField(entry.getKey().getName(), entry.getValue())
                    .setMin(0F)
                    .setMax(10F)
                    .setDefaultValue(defaultOcclusionMap.getOrDefault(entry.getKey(), SoundPhysicsMod.CONFIG.defaultBlockOcclusionFactor.get()))
                    .setSaveConsumer(value -> SoundPhysicsMod.OCCLUSION_CONFIG.setBlockDefinitionValue(entry.getKey(), value)).build();
            occlusion.addEntry(e);
        }

        ConfigCategory logging = builder.getOrCreateCategory(Component.translatable("cloth_config.garden_api.category.debug"));

        logging.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.debug_logging"),
                Component.translatable("cloth_config.garden_api.debug_logging.description"),
                SoundPhysicsMod.CONFIG.debugLogging
        ));
        logging.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.occlusion_logging"),
                Component.translatable("cloth_config.garden_api.occlusion_logging.description"),
                SoundPhysicsMod.CONFIG.occlusionLogging
        ));
        logging.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.environment_logging"),
                Component.translatable("cloth_config.garden_api.environment_logging.description"),
                SoundPhysicsMod.CONFIG.environmentLogging
        ));
        logging.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.performance_logging"),
                Component.translatable("cloth_config.garden_api.performance_logging.description"),
                SoundPhysicsMod.CONFIG.performanceLogging
        ));
        logging.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.render_sound_bounces"),
                Component.translatable("cloth_config.garden_api.render_sound_bounces.description"),
                SoundPhysicsMod.CONFIG.renderSoundBounces
        ));
        logging.addEntry(fromConfigEntry(entryBuilder,
                Component.translatable("cloth_config.garden_api.render_occlusion"),
                Component.translatable("cloth_config.garden_api.render_occlusion.description"),
                SoundPhysicsMod.CONFIG.renderOcclusion
        ));

        builder.setSavingRunnable(() -> {
            Loggers.log("Saving configs");
            SoundPhysicsMod.CONFIG.enabled.save();
            SoundPhysicsMod.REFLECTIVITY_CONFIG.save();
            SoundPhysicsMod.OCCLUSION_CONFIG.save();
            SoundPhysicsMod.SOUND_RATE_CONFIG.save();
            SoundPhysicsMod.CONFIG.reloadClient();
        });

        return builder.build();
    }

    @SuppressWarnings("unchecked")
    private static <T> AbstractConfigListEntry<T> fromConfigEntry(ConfigEntryBuilder entryBuilder, Component name, Component description, Object entry) {
        String typeName = entry.getClass().getSimpleName();

        if ("DoubleConfigEntry".equals(typeName)) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startDoubleField(name, invoke(entry, "get"))
                    .setTooltip(description)
                    .setMin(invoke(entry, "getMin"))
                    .setMax(invoke(entry, "getMax"))
                    .setDefaultValue(() -> invoke(entry, "getDefault"))
                    .setSaveConsumer(d -> invoke(entry, "set", d))
                    .build();
        } else if ("FloatConfigEntry".equals(typeName)) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startFloatField(name, invoke(entry, "get"))
                    .setTooltip(description)
                    .setMin(invoke(entry, "getMin"))
                    .setMax(invoke(entry, "getMax"))
                    .setDefaultValue(() -> invoke(entry, "getDefault"))
                    .setSaveConsumer(d -> invoke(entry, "set", d))
                    .build();
        } else if ("IntegerConfigEntry".equals(typeName)) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startIntField(name, invoke(entry, "get"))
                    .setTooltip(description)
                    .setMin(invoke(entry, "getMin"))
                    .setMax(invoke(entry, "getMax"))
                    .setDefaultValue(() -> invoke(entry, "getDefault"))
                    .setSaveConsumer(i -> invoke(entry, "set", i))
                    .build();
        } else if ("BooleanConfigEntry".equals(typeName)) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startBooleanToggle(name, invoke(entry, "get"))
                    .setTooltip(description)
                    .setDefaultValue(() -> invoke(entry, "getDefault"))
                    .setSaveConsumer(b -> invoke(entry, "set", b))
                    .build();
        } else if ("StringConfigEntry".equals(typeName)) {
            return (AbstractConfigListEntry<T>) entryBuilder
                    .startStrField(name, invoke(entry, "get"))
                    .setTooltip(description)
                    .setDefaultValue(() -> invoke(entry, "getDefault"))
                    .setSaveConsumer(s -> invoke(entry, "set", s))
                    .build();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T invoke(Object target, String methodName, Object... args) {
        try {
            Class<?>[] parameterTypes = new Class<?>[args.length];

            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = primitiveType(args[i].getClass());
            }

            Method method = target.getClass().getMethod(methodName, parameterTypes);

            return (T) method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access config entry method " + methodName, e);
        }
    }

    private static Class<?> primitiveType(Class<?> type) {
        if (type == Double.class) {
            return double.class;
        }
        if (type == Float.class) {
            return float.class;
        }
        if (type == Integer.class) {
            return int.class;
        }
        if (type == Boolean.class) {
            return boolean.class;
        }

        return type;
    }

}

