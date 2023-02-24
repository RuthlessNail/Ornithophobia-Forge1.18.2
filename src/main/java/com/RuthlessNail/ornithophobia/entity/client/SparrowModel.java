package com.RuthlessNail.ornithophobia.entity.client;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.entity.custom.SparrowEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SparrowModel extends AnimatedGeoModel<SparrowEntity> {
    @Override
    public ResourceLocation getModelLocation(SparrowEntity object) {
        return new ResourceLocation(Ornithophobia.MOD_ID, "geo/sparrow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SparrowEntity object) {
        return new ResourceLocation(Ornithophobia.MOD_ID, "textures/entity/sparrow/sparrow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SparrowEntity animatable) {
        return new ResourceLocation(Ornithophobia.MOD_ID, "animations/sparrow.animation.json");
    }
}
