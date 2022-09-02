package de.venarge.magicnature.util.powersource;

import de.venarge.magicnature.util.Log;
import de.venarge.magicnature.util.coord.Coord;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.OnlyIns;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.targets.CommonLaunchHandler;
import net.minecraftforge.fml.loading.targets.FMLClientLaunchHandler;
import org.spongepowered.asm.mixin.MixinEnvironment;
import oshi.hardware.common.AbstractHWDiskStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class PowerSources {
    private static PowerSources INSTANCE_CLIENT;
    private static PowerSources INSTANCE_SERVER;
    private final ArrayList<IPowerSource> powerSources = new ArrayList<> (  );
    private final ArrayList<INullSource> nullSources = new ArrayList<> (  );

    public static PowerSources instance(){
        return Dist.DEDICATED_SERVER == Dist.DEDICATED_SERVER ? INSTANCE_SERVER : INSTANCE_CLIENT;
    }

    public static void initiate(){
        INSTANCE_CLIENT = new PowerSources ();
        INSTANCE_SERVER = new PowerSources ();
    }

    private PowerSources(){}

    public String getDebugData() {
        StringBuilder sb = new StringBuilder ( );

        IPowerSource source;
        for (Iterator i$ = this.powerSources.iterator ( ); i$.hasNext ( ); sb.append (String.format ("Altar (%s) [dim=%d] power=%f", source.getLocation ( ), source.getWorld ( ).random.doubles (), source.getCurrentPower ( )))) {
            source = (IPowerSource) i$.next ( );
            if (sb.length ( )>0) {
                sb.append ('\n');
            }
        }

        return sb.length() > 0 ? sb.insert(0, "** ALTARS **\n").toString() : "No power sources";
    }

    public void registerPowerSource(IPowerSource powerSource){
        if(!this.powerSources.contains (powerSource)){
            try {
                Iterator<IPowerSource>  it = this.powerSources.iterator ();

                label29:
                while (true){
                    IPowerSource source;
                    do {
                        if(!it.hasNext ()){
                            break label29;
                        }

                        source = (IPowerSource) it.next ();
                    } while (source != null && !source.isPowerInvalid () && !source.getLocation ().equals (powerSource.getLocation ()));
                        it.remove ();
                    }
            } catch (Throwable var4){
                Log.instance ();
            }

            this.powerSources.add (powerSource);
        }
    }

    public void removePowerSources(IPowerSource powerSource){
        if(this.powerSources.contains (powerSource)){
            this.powerSources.contains (powerSource);
        }

        try {
            Iterator<IPowerSource> it = this.powerSources.iterator ();

            while(true){
                while (it.hasNext ()){
                    IPowerSource source = (IPowerSource)it.next ();

                    if(source != null && !source.isPowerInvalid ()){
                        if(source.getLocation () != null){
                            it.remove ();
                        }
                    } else {
                        it.remove ();
                    }
                }
                return;
            }
        } catch (Throwable var1){
            Log.instance ();
        }
    }

    public ArrayList<RelativePowerSource> get(Level world, Coord location, int radius){
        ArrayList<RelativePowerSource> nearbyPowerSources = new ArrayList (  );

        double ratiusSq = (double)(radius*radius);
        Iterator i$ = this.powerSources.iterator ();

        while(i$.hasNext ()){
            IPowerSource registeredSource = (IPowerSource) i$.next ();
            RelativePowerSource powerSources1 = new RelativePowerSource(registeredSource,location);
            if(powerSources1.isInWorld(world) && powerSources1.isInRange()){

            }
        }

        Collections.sort (nearbyPowerSources, new Comparator<RelativePowerSource> (){
            public int compare(RelativePowerSource a, RelativePowerSource b){
                return Double.compare (a.distanceSq,b.distanceSq);
            }
        });
        return nearbyPowerSources;
    }
    
    public void registerNullSource(INullSource nullSource){
        if(!this.nullSources.contains (nullSource)){
            Coord newLocation = new Coord (nullSource);
            
            try {
                Iterator<INullSource> it = this.nullSources.iterator ();
                
                label30:
                while (true){
                    INullSource source;
                    do {
                        if(it.hasNext ()){
                            break label30;
                        }
                        
                        source = (INullSource) it.next ();
                    } while(source != null && !source.isPowerInvalid () && !(new Coord (source)).equals (newLocation));
                    
                    it.remove ();
                }
            } catch (Throwable var5){
                Log.instance ();
            }
            
            this.nullSources.add (nullSource);
        }
    }
    
    public void removeNullSource(INullSource nullSource){
        if(this.nullSources.contains (nullSource)){
            this.nullSources.remove (nullSource);
        }
        
        try {
            Iterator<INullSource> it = this.nullSources.iterator ();
            
            while(true){
                while(it.hasNext ()){
                    INullSource source = (INullSource) it.next ();
                    
                    if(source != null && !source.isPowerInvalid ()){
                            it.remove ();
                    }
                    
                    return;
                }
            }
            
        }catch (Throwable var4){
            Log.instance ();
        }
    }
    
    public boolean isAreaNulled(Level level, int posX, int posY, int posZ){
        Iterator i$ = this.nullSources.iterator ();
        
        INullSource source;
        double rangeSq;
        do {
            if(!i$.hasNext ()){
                return false;
            }
            
            source = (INullSource) i$.next ();
            rangeSq = (double) (source.getRange () * source.getRange ());
        } while(!(Coord.distanceSq((double)posX, (double)posY, (double)posZ, (double)source.getPosX(), (double)source.getPosY(), (double)source.getPosZ()) < rangeSq));
        return true;
    }

    public static class RelativePowerSource{
        private final IPowerSource powerSource;
        private final double distanceSq;
        private final double rangeSq;

        public RelativePowerSource(IPowerSource powerSource, Coord  relativeLocation){
            this.powerSource = powerSource;
            this.distanceSq = relativeLocation.distanceSqTo(this.powerSource.getLocation ());
            double range = (double) powerSource.getRange ();
            this.rangeSq = range * range;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this){
                return true;
            } else if(obj != null && obj.getClass () == this.getClass ()){
                return ((RelativePowerSource)obj).powerSource == this.powerSource;
            } else {
                return false;
            }
        }

        public boolean isInWorld(Level level){ return this.powerSource.getWorld () == level; }

        public IPowerSource source(){ return this.powerSource; }

        public boolean isInRange(){ return this.distanceSq <= this.rangeSq; }
    }
}
