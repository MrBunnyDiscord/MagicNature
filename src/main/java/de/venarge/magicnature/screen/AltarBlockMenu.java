package de.venarge.magicnature.screen;

import de.venarge.magicnature.blocks.ModBlocks;
import de.venarge.magicnature.blocks.entities.ModBlockEntitys;
import de.venarge.magicnature.blocks.entities.custom.altar.AltarBlockEntity;
import de.venarge.magicnature.screen.slots.ModSlotResults;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class AltarBlockMenu extends AbstractContainerMenu {
    private final AltarBlockEntity blockEntity;
    private final Level level;


    public AltarBlockMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level.getBlockEntity (extraData.readBlockPos ()));
    }

    public AltarBlockMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super (ModMenuTypes.ALTAR_BLOCK_MENU.get (),pContainerId);

        checkContainerSize (inv, 4);
        blockEntity = ((AltarBlockEntity) entity);
        this.level = inv.player.level;

        addPlayerInventory (inv);
        addPlayerHotbar (inv);

        this.blockEntity.getCapability (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent (handler -> {
            this.addSlot (new SlotItemHandler (handler, 0, 128,52));
            this.addSlot (new SlotItemHandler (handler, 1, 175,70));
            //this.addSlot(new SlotItemHandler(handler,[number],[coord X], [coord Y]))
            this.addSlot (new ModSlotResults (handler ,8,128,128));
        });

    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 9;

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int index) {
        Slot sourceSlot = slots.get (index);
        if (sourceSlot == null || !sourceSlot.hasItem ( )) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem ( );
        ItemStack copyOfSourceStack = sourceStack.copy ( );

        // Check if the slot clicked is one of the vanilla container slots
        if (index<VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo (sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index<TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo (sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println ("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount ( ) == 0) {
            sourceSlot.set (ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged ( );
        }
        sourceSlot.onTake (pPlayer, sourceStack);
        return copyOfSourceStack;
    }

    private void addPlayerInventory(Inventory playerInventory){
        for (int i = 0; i < 9;i++) {
            for(int l = 0; l < 9; l++){
                this.addSlot (new Slot (playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory){
        for(int i = 0; i < 9; ++i){
            this.addSlot (new Slot (playerInventory, i, 8 + i * 18, 144));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid (ContainerLevelAccess.create (level,
                blockEntity.getBlockPos ()),
                pPlayer, ModBlocks.Altar.get ());
    }
}
