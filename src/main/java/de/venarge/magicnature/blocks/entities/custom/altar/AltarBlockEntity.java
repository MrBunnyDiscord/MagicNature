package de.venarge.magicnature.blocks.entities.custom.altar;

import de.venarge.magicnature.MagicNature;
import de.venarge.magicnature.blocks.custom.AltarBlock;
import de.venarge.magicnature.blocks.entities.ModBlockEntitys;
import de.venarge.magicnature.items.ModItems;
import de.venarge.magicnature.util.coord.Coord;
import de.venarge.magicnature.util.powersource.IPowerSource;
import de.venarge.magicnature.util.powersource.PowerSource;
import de.venarge.magicnature.util.powersource.PowerSources;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;

public class AltarBlockEntity extends BlockEntity implements MenuProvider {
    private Coord core;
    private float power;
    private float maxPower;
    private int powerScale;
    private int rechargeScale;
    private int enhancementLevel;
    private int rangeScale = 1;
    long lastPowerUpdate = 0L;
    private static final int SCAN_DISTANCE = 14;
    private ArrayList<Integer> extraNatureIDs = null;

    private ItemStackHandler itemHandler = new ItemStackHandler ( 9
    ){
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged (slot);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty ();

    public AltarBlockEntity( BlockPos pPos, BlockState pState) {
        super (ModBlockEntitys.ALTAR_BLOCK_ENTITY.get (),pPos, pState);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return lazyItemHandler.cast ();
        }

        return super.getCapability (cap);
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent ("Altar");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return null;
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps ( );
        lazyItemHandler.invalidate ();
    }

    @Override
    public void onLoad() {
        super.onLoad ( );
        lazyItemHandler = LazyOptional.of (() -> itemHandler);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load (nbt);
        itemHandler.deserializeNBT (nbt.getCompound ("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        p_187471_.put ("inventory", itemHandler.serializeNBT ());
        super.saveAdditional (p_187471_);
    }


    public void drops() {
        SimpleContainer inventory = new SimpleContainer (itemHandler.getSlots ());
        for(int i = 0;i <itemHandler.getSlots ( ); i++){
            inventory.setItem (i, itemHandler.getStackInSlot (i));
        }

        Containers.dropContents (this.level, this.worldPosition, inventory );
    }


    // FIRST USE

    public static void tick(Level pLevel,BlockPos pPos, BlockState pState, AltarBlockEntity pBlockEntity){
        if(hasRecipe (pBlockEntity) && hasNotReachedStackLimit (pBlockEntity)) {
            craftItem (pBlockEntity);
        }
    }

    private static void craftItem(AltarBlockEntity entity){
        entity.itemHandler.extractItem (0,1,false);
        entity.itemHandler.extractItem (1,1,false);

        entity.itemHandler.setStackInSlot (9, new ItemStack (ModItems.CHARGED_ATTUNED_STONE.get (),
                entity.itemHandler.getStackInSlot (3).getCount () + 1));
    }

    private static boolean hasRecipe(AltarBlockEntity entity){
        boolean hastItemInSlot1 = entity.itemHandler.getStackInSlot (1).getItem () == ModItems.ATTUNED_STONE.get ();
        boolean hasItemInSlot2 = entity.itemHandler.getStackInSlot (2).getItem () == ModItems.REDSTONE_SUP.get ();
        boolean hasItemInSlot3;
        boolean hasItemInSlot4;
        boolean hasItemInSlot5;
        boolean hasItemInSlot6;
        boolean hasItemInSlot7;
        boolean hasItemInSlot8;
        boolean hasItemInSlot9;

        return hastItemInSlot1 && hasItemInSlot2;
    }

    private static boolean hasNotReachedStackLimit(AltarBlockEntity entity) {
        return entity.itemHandler.getStackInSlot (9).getCount ( )<entity.itemHandler.getStackInSlot (3).getMaxStackSize ( );
    }

    public void setCore(Coord core) {
        this.core = core;
        IPowerSource source = new IPowerSource ( ) {
            @Override
            public Level getWorld() {
                return null;
            }

            @Override
            public Coord getLocation() {
                return null;
            }

            @Override
            public boolean isLocationEqual(Coord var1) {
                return false;
            }

            @Override
            public boolean consumePower(float var1) {
                return false;
            }

            @Override
            public float getCurrentPower() {
                return 0;
            }

            @Override
            public float getRange() {
                return 0;
            }

            @Override
            public int getEnchantcementLevel() {
                return 0;
            }

            @Override
            public boolean isPowerInvalid() {
                return false;
            }
        };

        if (this.isCore ( )) {
            PowerSources.instance ( ).registerPowerSource (source);
        }
        if (core == null) {
            PowerSources.instance ( ).removePowerSources (source);
            this.power = 0.0F;
            this.maxPower = 0.0F;
            this.powerScale = 1;
            this.rechargeScale = 1;
            this.rangeScale = 1;
            this.enhancementLevel = 1;
        }
    }

    private boolean isCore() {
        return this.core != null && this.core.isAtPosition(this);
    }
}
