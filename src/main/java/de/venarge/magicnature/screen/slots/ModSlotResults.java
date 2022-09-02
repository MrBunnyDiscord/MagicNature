package de.venarge.magicnature.screen.slots;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.CocartesianLike;
import com.mojang.datafixers.kinds.ListBox;
import com.mojang.datafixers.kinds.Traversable;
import com.mojang.datafixers.optics.profunctors.Cocartesian;
import com.mojang.serialization.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModSlotResults extends SlotItemHandler {

    public ModSlotResults(IItemHandler iItemHandler, int index, int x, int y){
        super(iItemHandler,index,x,y);
    }
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }
}
