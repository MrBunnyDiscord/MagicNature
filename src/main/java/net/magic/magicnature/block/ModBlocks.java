package net.magic.magicnature.block;

import net.magic.magicnature.MagicNature;
import net.magic.magicnature.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCK =
            DeferredRegister.create (ForgeRegistries.BLOCKS, MagicNature.MOD_ID);

    public static final RegistryObject<Block> RUNE_STONE = registerNatureBlock ("rune_stone",
            () -> new Block (BlockBehaviour.Properties.of (Material.STONE)
                    .requiresCorrectToolForDrops ().strength (3).sound (SoundType.STONE)
                    .destroyTime (5F)));

    public static final RegistryObject<Block> ANCIENT_RUNE_SLICER = registerMagicBlock ("ancient_rune_slicer",
            () -> new Block (BlockBehaviour.Properties.of (Material.METAL).requiresCorrectToolForDrops ()
                    .strength (4f).destroyTime (7F).noOcclusion ()));

    public static<T extends Block> RegistryObject<T> registerMagicBlock(String name, Supplier<T> magicBlock){
        RegistryObject<T> toreturn = BLOCK.register (name,magicBlock);

        registerMagicItemBlock (name,toreturn);

        return toreturn;
    }

    public static<T extends Block> void registerMagicItemBlock(String name, RegistryObject<T> magicBlock){
        ModItems.ITEM.register (name, () -> new BlockItem (magicBlock.get (),
                new Item.Properties ()));
    }

    public static<T extends Block> RegistryObject<T> registerNatureBlock(String name, Supplier<T> natureBlock){
        RegistryObject<T> toreturn = ModBlocks.BLOCK.register (name,natureBlock);

        registerNatureItemBlock (name,toreturn);

        return toreturn;
    }

    public static<T extends Block> void registerNatureItemBlock(String name, RegistryObject<T> natureBlock){
        ModItems.ITEM.register (name, () -> new BlockItem (natureBlock.get (),
                new Item.Properties ()));
    }

    public static void register(IEventBus eventBus){
        BLOCK.register (eventBus);
    }
}
