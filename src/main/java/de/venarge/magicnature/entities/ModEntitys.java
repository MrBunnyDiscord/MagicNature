package de.venarge.magicnature.entities;

import de.venarge.magicnature.MagicNature;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntitys {
    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create (ForgeRegistries.ENTITIES, MagicNature.MOD_ID);

    public static void register(IEventBus eventBus){
        ENTITY.register (eventBus);
    }
}
