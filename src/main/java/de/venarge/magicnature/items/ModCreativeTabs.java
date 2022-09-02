package de.venarge.magicnature.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {

    public static final CreativeModeTab MN_Magic = new CreativeModeTab ("mn_magic") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.REDSTONE_SUP.get());
        }
    };
}
