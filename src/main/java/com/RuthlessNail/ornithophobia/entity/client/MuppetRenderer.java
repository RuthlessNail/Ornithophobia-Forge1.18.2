package com.RuthlessNail.ornithophobia.entity.client;

import com.RuthlessNail.ornithophobia.Ornithophobia;
import com.RuthlessNail.ornithophobia.entity.custom.MuppetEntity;
import com.RuthlessNail.ornithophobia.entity.custom.SparrowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MuppetRenderer extends GeoEntityRenderer<MuppetEntity> {
    public MuppetRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MuppetModel());
        this.shadowRadius = 1f;
    }

    @Override
    public ResourceLocation getTextureLocation(MuppetEntity instance) {
        return new ResourceLocation(Ornithophobia.MOD_ID, "textures/entity/muppet/muppet.png");
    }

    @Override
    public RenderType getRenderType(MuppetEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
