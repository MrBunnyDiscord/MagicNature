package net.magic.magicnature.item;

import net.magic.magicnature.MagicNature;
import net.magic.magicnature.item.custom.runes.FireRune;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEM =
            DeferredRegister.create (ForgeRegistries.ITEMS, MagicNature.MOD_ID);

    public static final RegistryObject<Item> FIRE_RUNE = ITEM.register ("fire_rune",
            () -> new FireRune (new Item.Properties ().stacksTo (1).durability (428)));

    public static void register(IEventBus eventBus){
        ITEM.register (eventBus);
    }
}
