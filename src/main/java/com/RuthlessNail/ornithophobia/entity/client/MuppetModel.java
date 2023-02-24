package com.RuthlessNail.ornithophobia.entity.client;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.entity.custom.MuppetEntity;
import com.RuthlessNail.ornithophobia.entity.custom.SparrowEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MuppetModel extends AnimatedGeoModel<MuppetEntity> {
    @Override
    public ResourceLocation getModelLocation(MuppetEntity object) {
        return new ResourceLocation(Ornithophobia.MOD_ID, "geo/muppet.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MuppetEntity object) {
        return new ResourceLocation(Ornithophobia.MOD_ID, "textures/entity/muppet/muppet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MuppetEntity animatable) {
        return new ResourceLocation(Ornithophobia.MOD_ID, "animations/muppet.animation.json");
    }
}
