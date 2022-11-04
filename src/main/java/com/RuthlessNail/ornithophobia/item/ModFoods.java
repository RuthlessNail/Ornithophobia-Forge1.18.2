package com.RuthlessNail.ornithophobia.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties EYEBALL = (new FoodProperties.Builder()).fast().nutrition(2).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8F).effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.4f).meat().build();
}
