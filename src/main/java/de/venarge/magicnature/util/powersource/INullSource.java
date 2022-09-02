package de.venarge.magicnature.util.powersource;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WorldData;

public interface INullSource {
    BlockPos getWorld();

    int getPosX();

    int getPosY();

    int getPosZ();

    float getRange();

    boolean isPowerInvalid();
}
