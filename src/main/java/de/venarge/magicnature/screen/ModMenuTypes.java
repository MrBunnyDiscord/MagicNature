package de.venarge.magicnature.screen;

import de.venarge.magicnature.MagicNature;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes<T extends AbstractContainerMenu> {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create (ForgeRegistries.CONTAINERS, MagicNature.MOD_ID);

    public static final RegistryObject<MenuType<AltarBlockMenu>> ALTAR_BLOCK_MENU =
            registerMenu (AltarBlockMenu::new,"altar_menu");

        public RegistryObject<AbstractContainerMenu>

        public static void register(IEventBus eventBus){
        MENUS.register (eventBus);
    }
}
