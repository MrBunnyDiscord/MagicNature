package net.magic.magicnature;

import com.mojang.logging.LogUtils;
import net.magic.magicnature.block.ModBlocks;
import net.magic.magicnature.block.blockentities.ModBlockEntities;
import net.magic.magicnature.entitys.ModEntities;
import net.magic.magicnature.item.ModItems;
import net.magic.magicnature.screen.ModMenus;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.minecraftforge.fml.common.Mod;

import java.util.stream.Collectors;
@Mod(MagicNature.MOD_ID)
public class MagicNature {

    private static final Logger LOGGER = LogUtils.getLogger ( );

    public static final String MOD_ID = "magicnature";

    public MagicNature(){
        IEventBus eventBus = FMLJavaModLoadingContext.get ( ).getModEventBus ( );

        ModBlocks.register (eventBus);
        ModItems.register (eventBus);
        ModEntities.register (eventBus);
        ModMenus.register (eventBus);
        ModBlockEntities.register (eventBus);

        eventBus.addListener (this::setup);
        eventBus.addListener (this::enqueueIMC);
        eventBus.addListener (this::processIMC);

        eventBus.addListener (this::ClientSetupEvent);

        MinecraftForge.EVENT_BUS.register (this);
    }

    private void setup(final FMLCommonSetupEvent event){
        LOGGER.info ("PREINIT START");
    }

    private void enqueueIMC(final InterModEnqueueEvent event){
        InterModComms.sendTo (MagicNature.MOD_ID,"helloworld", () -> {
            return "hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event){
        LOGGER.info ("Got IMC {}", event.getIMCStream ( )
                .map (m->m.messageSupplier ()).collect (Collectors.toList ( )));
    }

    private void ClientSetupEvent(final FMLClientSetupEvent event){

    }
}
