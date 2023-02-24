package com.RuthlessNail.ornithophobia.entity;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.entity.custom.MuppetEntity;
import com.RuthlessNail.ornithophobia.entity.custom.SparrowEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Ornithophobia.MOD_ID);

    public static final RegistryObject<EntityType<SparrowEntity>> SPARROW =
            ENTITY_TYPES.register("sparrow",
                    () -> EntityType.Builder.of(SparrowEntity::new, MobCategory.MONSTER)
                            .sized(0.8f, 0.8f)
                            .build(new ResourceLocation(Ornithophobia.MOD_ID, "sparrow").toString()));

    public static final RegistryObject<EntityType<MuppetEntity>> MUPPET =
            ENTITY_TYPES.register("muppet",
                    () -> EntityType.Builder.of(MuppetEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 2f)
                            .build(new ResourceLocation(Ornithophobia.MOD_ID, "muppet").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
