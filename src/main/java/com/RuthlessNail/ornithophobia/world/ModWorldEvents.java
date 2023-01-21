package com.RuthlessNail.ornithophobia.world;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.world.feature.ModOrePlacement;
import com.RuthlessNail.ornithophobia.world.feature.gen.ModFlowerGeneration;
import com.RuthlessNail.ornithophobia.world.feature.gen.ModOreGeneration;
import com.RuthlessNail.ornithophobia.world.feature.gen.ModTreeGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ornithophobia.MOD_ID)
public class ModWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModOreGeneration.generateOres(event);

        ModTreeGeneration.generateTrees(event);
        ModFlowerGeneration.generateFlowers(event);
    }
}
