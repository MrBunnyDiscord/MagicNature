package de.venarge.magicnature.util;

import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.CapabilityProvider;

import java.util.logging.Logger;

public class Log {
    static final Log INSTANCE = new Log();

    public static Log instance() {
        return INSTANCE;
    }

    static String getModPrefix() {
        return "magicnature: ";
    }

}
