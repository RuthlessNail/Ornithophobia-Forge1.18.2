package com.RuthlessNail.ornithophobia.painting;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<Motive> PAINTING_MOTIVES =
            DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, Ornithophobia.MOD_ID);

    public static final RegistryObject<Motive> YOUB1GA =
            PAINTING_MOTIVES.register("youb1ga", () -> new Motive(32,32));
    public static final RegistryObject<Motive> JUSTICE =
            PAINTING_MOTIVES.register("justice", () -> new Motive(16,32));
    public static final RegistryObject<Motive> ORDER =
            PAINTING_MOTIVES.register("order", () -> new Motive(64,48));
    public static final RegistryObject<Motive> NICE_RING =
            PAINTING_MOTIVES.register("nice_ring", () -> new Motive(16,16));
    public static final RegistryObject<Motive> CATA =
            PAINTING_MOTIVES.register("cata", () -> new Motive(32,16));
    public static final RegistryObject<Motive> SCHOLAR =
            PAINTING_MOTIVES.register("scholar", () -> new Motive(48,64));
    public static final RegistryObject<Motive> DUEL =
            PAINTING_MOTIVES.register("duel", () -> new Motive(64,48));

    public static void register(IEventBus eventBus) {
        PAINTING_MOTIVES.register(eventBus);
    }
}
