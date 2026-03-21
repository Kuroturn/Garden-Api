package com.garden.api.example.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import com.garden.api.example.entity.BatEntity;
import com.garden.api.example.entity.BikeEntity;
import com.garden.api.example.entity.CoolKidEntity;
import com.garden.api.example.entity.FakeGlassEntity;
import com.garden.api.example.entity.DynamicExampleEntity;
import com.garden.api.example.entity.ParasiteEntity;
import com.garden.api.example.entity.RaceCarEntity;
import com.garden.api.GardenApi;

public class EntityRegistry {

    public static final EntityType<BatEntity> BAT = registerMob("bat", BatEntity::new, 0.7f, 1.3f);
    public static final EntityType<BikeEntity> BIKE = registerMob("bike", BikeEntity::new, 0.5f, 0.6f);

    public static final EntityType<RaceCarEntity> RACE_CAR = registerMob("race_car", RaceCarEntity::new, 1.5f, 1.5f);
    public static final EntityType<ParasiteEntity> PARASITE = registerMob("parasite", ParasiteEntity::new, 1.5f, 1.5f);

    public static final EntityType<DynamicExampleEntity> GREMLIN = registerMob("gremlin", DynamicExampleEntity::new, 0.5f, 1.9f);
    public static final EntityType<DynamicExampleEntity> MUTANT_ZOMBIE = registerMob("mutant_zombie", DynamicExampleEntity::new, 0.5f, 1.9f);
    public static final EntityType<FakeGlassEntity> FAKE_GLASS = registerMob("fake_glass", FakeGlassEntity::new, 1, 1);

    public static final EntityType<CoolKidEntity> COOL_KID = registerMob("cool_kid", CoolKidEntity::new, 0.45f, 1f);

    public static <T extends Mob> EntityType<T> registerMob(String name, EntityType.EntityFactory<T> entity,
                                                            float width, float height) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE,
                new ResourceLocation(GardenApi.MOD_ID, name),FabricEntityTypeBuilder.create(MobCategory.CREATURE, entity).dimensions(EntityDimensions.scalable(width, height)).build());
    }
}
