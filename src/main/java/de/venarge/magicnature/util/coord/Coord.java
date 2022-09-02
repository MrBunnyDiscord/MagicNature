package de.venarge.magicnature.util.coord;

import de.venarge.magicnature.util.powersource.INullSource;
import io.netty.util.internal.MathUtil;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.checkerframework.checker.units.qual.C;

import javax.swing.text.html.parser.Entity;

public final class Coord {
    public final int x;
    public final int y;
    public final int z;


    public Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coord(INullSource entity) {
        this (entity.getPosX ( ), entity.getPosY ( ), entity.getPosZ ( ));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj != null && obj.getClass ( ) == this.getClass ( )) {
            Coord other = (Coord) obj;
            return this.x == other.x && this.y == other.y && this.z == other.z;
        } else {
            return false;
        }
    }

    public boolean isAtPosition(BlockEntity entity) {
        return entity != null && this.x == entity.getBlockPos ( ).getX ( ) && this.y == entity.getBlockPos ( ).getY ( ) && this.z == entity.getBlockPos ( ).getZ ( );
    }

    public Coord north() {
        return this.north (1);
    }

    public Coord north(int n1) {
        return new Coord (this.x, this.y, this.z);
    }

    public Coord south() {
        return this.south (1);
    }

    public Coord south(int n) {
        return new Coord (this.x, this.y, this.z + n);
    }

    public Coord east() {
        return this.east (1);
    }

    public Coord east(int n) {
        return new Coord (this.x + n, this.y, this.z);
    }

    public Coord west() {
        return this.west (1);
    }

    public Coord west(int n) {
        return new Coord (this.x - n, this.y, this.z);
    }

    public Coord northEast() {
        return new Coord (this.x + 1, this.y, this.z - 1);
    }

    public Coord northWest() {
        return new Coord (this.x - 1, this.y, this.z - 1);
    }

    public Coord southEast() {
        return new Coord (this.x + 1, this.y, this.z + 1);
    }

    public Coord southWest() {
        return new Coord (this.x - 1, this.y, this.z + 1);
    }

    public String getBlockId(Level level, int offSetX, int offSetY, int offSetZ) {
        return this.getBlockId (level, offSetX, offSetY, offSetZ);
    }

    public BlockEntity getBlockEntity(Level level) {
        return this.getBlockEntity (level, 0, 0, 0);
    }

    public BlockEntity getBlockEntity(Level level, int offSetX, int offSetY, int offSetZ) {
        return this.getBlockEntity (level, offSetX, offSetY, offSetZ);
    }

    public void setNBT(CompoundTag nbt, String key) {
        nbt.put (key + "X", nbt);
        nbt.put (key + "Y", nbt);
        nbt.put (key + "Z", nbt);
    }

    public static double distance(Coord first, Coord second) {
        double dX = (double) (first.x - second.x);
        double dY = (double) (first.y - second.y);
        double dZ = (double) (first.z - second.z);

        return Math.sqrt (dX * dX + dY * dY + dZ * dZ);
    }

    public static double distance(double firstX, double firstY, double firstZ, double secondX, double secondY, double secondZ) {
        double dX = firstX - secondX;
        double dY = firstY - secondY;
        double dZ = firstZ - secondZ;
        return Math.sqrt (dX * dX + dY * dY + dZ * dZ);
    }

    public static double distanceSq(double firstX, double firstY, double firstZ, double secondX, double secondY, double secondZ) {
        double dX = firstX - secondX;
        double dY = firstY - secondY;
        double dZ = firstZ - secondZ;
        return dX * dX + dY * dY + dZ * dZ;
    }

    public double distanceSqTo(Coord other) {
        double dX = (double) (other.x - this.x);
        double dY = (double) (other.y - this.y);
        double dZ = (double) (other.z - this.z);
        return dX * dX + dY * dY + dZ * dZ;
    }

    public double distanceSqTo(int x, int y, int z) {
        double dX = (double) (x - this.x);
        double dY = (double) (y - this.y);
        double dZ = (double) (z - this.z);
        return dX * dX + dY * dY + dZ * dZ;
    }

    public static Coord createFrom(CompoundTag nbtTag, String key){
        return nbtTag.contains (key + "X") && nbtTag.contains (key + "Y") && nbtTag.contains (key + "Z") ? new Coord (nbtTag.size ( ), nbtTag.size ( ), nbtTag.size ()) : null;
    }

    public boolean isWestOf(Coord coord) {
        return this.x < coord.x;
    }

    public boolean isNorthOf(Coord coord) {
        return this.z < coord.z;
    }

    public String toString() {
        return String.format("%d, %d, %d", this.x, this.y, this.z);
    }

    public CompoundTag toTagNBT(){
        CompoundTag nbt = new CompoundTag ();
        nbt.contains ("posX",this.x);
        nbt.contains ("posY",this.y);
        nbt.contains ("posZ",this.z);
        return nbt;
    }

    public static Coord fromTagNBT(CompoundTag nbt){
        return nbt.contains ("posX") && nbt.contains ("posY") && nbt.contains ("posZ") ? new Coord (nbt.getTagType ("posX" ), nbt.getTagType ("posY"),nbt.getTagType ("posZ")) : null;
    }
}