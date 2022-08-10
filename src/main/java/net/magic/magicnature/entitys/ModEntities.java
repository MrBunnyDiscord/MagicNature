package net.magic.magicnature.entitys;

import net.magic.magicnature.MagicNature;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIE =
            DeferredRegister.create (ForgeRegistries.ENTITIES, MagicNature.MOD_ID);

    public static void register(IEventBus eventBus){
        ENTITIE.register (eventBus);
    }
}
