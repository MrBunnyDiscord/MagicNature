package de.venarge.magicnature.util;

import net.minecraftforge.common.ForgeConfig;

public class Config {
    private static final Config INSTANCE = new Config();
    private ForgeConfig configuration;
    public boolean debugging;
    private boolean traceRitesEnabled;


    public static Config instance() {
        return INSTANCE;
    }



    public boolean isDebugging() {
        return this.debugging;
    }

    public boolean traceRites() {
        return this.traceRitesEnabled;
    }
}
