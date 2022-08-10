package net.magic.magicnature.block.blockentities;

import net.magic.magicnature.MagicNature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY =
            DeferredRegister.create (ForgeRegistries.BLOCK_ENTITIES,MagicNature.MOD_ID);



    public static void register(IEventBus eventBus){
        BLOCK_ENTITY.register (eventBus);
    }
}
