package net.magic.magicnature.item.tabs;

import net.magic.magicnature.block.ModBlocks;
import net.magic.magicnature.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemTabs {

    public static final CreativeModeTab NATURE = new CreativeModeTab ("mn_nature") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack (ModBlocks.RUNE_STONE.get ());
        }
    };

    public static final CreativeModeTab MAGIC = new CreativeModeTab ("mn_magic") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack (ModItems.FIRE_RUNE.get ());
        }
    };
}
