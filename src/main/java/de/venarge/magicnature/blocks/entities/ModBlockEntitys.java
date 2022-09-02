package de.venarge.magicnature.blocks.entities;

import de.venarge.magicnature.MagicNature;
import de.venarge.magicnature.blocks.ModBlocks;
import de.venarge.magicnature.blocks.entities.custom.altar.AltarBlockEntity;
import mcp.client.Start;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create (ForgeRegistries.BLOCK_ENTITIES, MagicNature.MOD_ID);

    public static final RegistryObject<BlockEntityType<AltarBlockEntity>> ALTAR_BLOCK_ENTITY =
            BLOCK_ENTITY.register ("altar_block_entity", () ->
                    BlockEntityType.Builder.of (AltarBlockEntity::new, ModBlocks.Altar.get ()).build (null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITY.register (eventBus);
    }
}
