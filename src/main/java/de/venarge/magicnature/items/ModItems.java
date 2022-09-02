package de.venarge.magicnature.items;

import de.venarge.magicnature.MagicNature;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;

public class ModItems {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create (ForgeRegistries.ITEMS, MagicNature.MOD_ID);

    public static final RegistryObject<Item> ATTUNED_STONE = ITEM.register ("attuned_stone",
            () -> new Item (new Item.Properties ().tab (ModCreativeTabs.MN_Magic)
                    .durability (2500)));

    public static final RegistryObject<Item> CHARGED_ATTUNED_STONE = ITEM.register ("charged_attuned_stone",
            () -> new Item (new Item.Properties ().tab (ModCreativeTabs.MN_Magic)));

    public static final RegistryObject<Item> REDSTONE_SUP = ITEM.register ("redstone_sup",
            () -> new Item (new Item.Properties ().setNoRepair ()));

    public static void register(IEventBus eventBus){
        ITEM.register (eventBus);
    }
}
