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

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }

    private static RegistryObject<SoundEvent> registrySoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Ornithophobia.MOD_ID, name)));
    }
}
