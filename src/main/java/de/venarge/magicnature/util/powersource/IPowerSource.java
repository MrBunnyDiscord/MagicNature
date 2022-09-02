package de.venarge.magicnature.util.powersource;

import de.venarge.magicnature.util.coord.Coord;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.WorldData;

public interface IPowerSource {
    Level getWorld();

    Coord getLocation();

    boolean isLocationEqual(Coord var1);

    boolean consumePower(float var1);

    float getCurrentPower();

    float getRange();

    int getEnchantcementLevel();

    boolean isPowerInvalid();
}
