package de.venarge.magicnature.blocks;

import de.venarge.magicnature.MagicNature;

import de.venarge.magicnature.blocks.custom.AltarBlock;
import de.venarge.magicnature.items.ModItems;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create (ForgeRegistries.BLOCKS, MagicNature.MOD_ID);

    public static final RegistryObject<Block> Altar = registerBlock ("altar",
            () -> new AltarBlock (BlockBehaviour.Properties.of (Material.STONE).sound(SoundType.STONE)
                    .strength (2f)));

    public static<T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toreturn = BLOCK.register (name,block);

        registerItemBlock (name,toreturn);

        return toreturn;
    }

    public static <T extends Block> void registerItemBlock(String name, RegistryObject<T> block){
        ModItems.ITEM.register (name, () -> new BlockItem (block.get (),
                new Item.Properties ()));
    }

    public static void register(IEventBus eventBus){
        BLOCK.register (eventBus);
    }
}
