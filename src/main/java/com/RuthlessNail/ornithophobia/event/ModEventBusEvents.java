package com.RuthlessNail.ornithophobia.event;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.entity.ModEntityTypes;
import com.RuthlessNail.ornithophobia.entity.custom.MuppetEntity;
import com.RuthlessNail.ornithophobia.entity.custom.SparrowEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ornithophobia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.SPARROW.get(), SparrowEntity.setAttributes());
        event.put(ModEntityTypes.MUPPET.get(), MuppetEntity.setAttributes());
    }

}
