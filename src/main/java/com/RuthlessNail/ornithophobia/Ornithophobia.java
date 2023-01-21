package com.RuthlessNail.ornithophobia;

import com.RuthlessNail.ornithophobia.world.ModRegion;
import com.RuthlessNail.ornithophobia.world.ModSurfaceRuleData;
import com.RuthlessNail.ornithophobia.block.ModBlock;
import com.RuthlessNail.ornithophobia.item.ModItems;
import com.RuthlessNail.ornithophobia.painting.ModPaintings;
import com.RuthlessNail.ornithophobia.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Ornithophobia.MOD_ID)
public class Ornithophobia
{
    //add a comment
    public static final String MOD_ID = "ornithophobia";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public Ornithophobia()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlock.register(eventBus);
        ModPaintings.register(eventBus);
        ModSounds.register(eventBus);


        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void clientSetup(final  FMLCommonSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(ModBlock.EYE_ROSE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlock.POTTED_EYE_ROSE.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlock.FLESH_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlock.FLESH_SAPLING.get(), RenderType.cutout());
    }


    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlock.EYE_ROSE.getId(), ModBlock.POTTED_EYE_ROSE);

            Regions.register(new ModRegion(new ResourceLocation(MOD_ID, "overworld"), 2));

            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRuleData.makeRules());
        });
    }

}
