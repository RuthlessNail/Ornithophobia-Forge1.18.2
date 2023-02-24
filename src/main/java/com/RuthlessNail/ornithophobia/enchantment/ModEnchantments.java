package com.RuthlessNail.ornithophobia.enchantment;
import com.RuthlessNail.ornithophobia.Ornithophobia;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments implements net.minecraftforge.common.IExtensibleEnum {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Ornithophobia.MOD_ID);

    public static RegistryObject<Enchantment> THUMP =
            ENCHANTMENTS.register("thump",
                    () -> new HammerHurtEnchantment(Enchantment.Rarity.UNCOMMON,
                            null, EquipmentSlot.MAINHAND));

    public static RegistryObject<Enchantment> THOR =
            ENCHANTMENTS.register("thor",
                    () -> new HammerLightningEnchantment(Enchantment.Rarity.VERY_RARE,
                            null, EquipmentSlot.MAINHAND));



    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
