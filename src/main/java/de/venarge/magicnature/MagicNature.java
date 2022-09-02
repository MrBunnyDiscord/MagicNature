package de.venarge.magicnature;

import com.mojang.logging.LogUtils;
import de.venarge.magicnature.blocks.ModBlocks;
import de.venarge.magicnature.blocks.entities.ModBlockEntitys;
import de.venarge.magicnature.entities.ModEntitys;
import de.venarge.magicnature.items.ModItems;
import de.venarge.magicnature.screen.AltarBlockScreen;
import de.venarge.magicnature.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod (MagicNature.MOD_ID)
public class MagicNature {
    public static final String MOD_ID = "magicnature";

    private static final Logger LOGGER = LogUtils.getLogger ();

    public MagicNature(){
        IEventBus eventBus = FMLJavaModLoadingContext.get ( ).getModEventBus ( );

        ModBlocks.register (eventBus);
        ModBlockEntitys.register (eventBus);

        ModItems.register (eventBus);

        ModEntitys.register (eventBus);

        ModMenuTypes.register (eventBus);

        eventBus.addListener (this::setup);
        eventBus.addListener (this::clientSetup);

        MinecraftForge.EVENT_BUS.register (this);
    }

    private void setup(final FMLCommonSetupEvent event){

    }

    private void clientSetup(final FMLClientSetupEvent event){

        MenuScreens.register(ModMenuTypes.ALTAR_BLOCK_MENU.get (), AltarBlockScreen::new);
    }
}
