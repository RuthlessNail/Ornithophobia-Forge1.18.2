package com.RuthlessNail.ornithophobia.sound;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Ornithophobia.MOD_ID);

    public static final RegistryObject<SoundEvent> SPARROW_AMBIENT =
            registrySoundEvent("sparrow_ambient");

    public static final RegistryObject<SoundEvent> SPARROW_HURT =
            registrySoundEvent("sparrow_hurt");

    public static final RegistryObject<SoundEvent> SPARROW_DEATH =
            registrySoundEvent("sparrow_death");

    public static final RegistryObject<SoundEvent> SPARROW_SWOOP =
            registrySoundEvent("sparrow_swoop");

    public static final RegistryObject<SoundEvent> MUPPET_AMBIENT1 =
            registrySoundEvent("muppet_ambient1");

    public static final RegistryObject<SoundEvent> MUPPET_AMBIENT2 =
            registrySoundEvent("muppet_ambient2");

    public static final RegistryObject<SoundEvent> MUPPET_AMBIENT3 =
            registrySoundEvent("muppet_ambient3");

    public static final RegistryObject<SoundEvent> MUPPET_DEATH =
            registrySoundEvent("muppet_death");

    public static final RegistryObject<SoundEvent> MUPPET_HURT =
            registrySoundEvent("muppet_hurt");

    public static final RegistryObject<SoundEvent> MUPPET_ANGRY =
            registrySoundEvent("muppet_angry");


    public static final RegistryObject<SoundEvent> UWU =
            registrySoundEvent("uwu");

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }

    private static RegistryObject<SoundEvent> registrySoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Ornithophobia.MOD_ID, name)));
    }
}
