package me.iffa.bspace.wgen.populators;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 *
 * @author kitskub
 */
public class SpaceDataPopulator extends BlockPopulator {
    public static Map<World, Map<WrappedCoords, Byte>> coords = new HashMap<World, Map<WrappedCoords, Byte>>();
    
    public static void addCoords(World world, int chunkX, int chunkZ, int x, int y, int z, byte data) {
        if (coords.get(world) == null) {
            coords.put(world, new HashMap<WrappedCoords, Byte>());
        }
        WrappedCoords key = new WrappedCoords();
        key.chunkX = chunkX;
        key.chunkZ = chunkZ;
        key.x = x;
        key.y = y;
        key.z = z;
        coords.get(world).put(key, data);
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (coords.get(world) == null) return;
        for (WrappedCoords c : coords.get(world).keySet()) {
            if (c.chunkX == chunk.getX() && c.chunkZ == chunk.getZ()) {
                chunk.getBlock(c.x, c.y, c.z).setData(coords.get(world).get(c));
            }
        }
    }
    
    public static class WrappedCoords {
        public int chunkX;
        public int chunkZ;
        public int x;
        public int y;
        public int z;

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final WrappedCoords other = (WrappedCoords) obj;
            if (this.chunkX != other.chunkX) {
                return false;
            }
            if (this.chunkZ != other.chunkZ) {
                return false;
            }
            if (this.x != other.x) {
                return false;
            }
            if (this.y != other.y) {
                return false;
            }
            if (this.z != other.z) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 59 * hash + this.chunkX;
            hash = 59 * hash + this.chunkZ;
            hash = 59 * hash + this.x;
            hash = 59 * hash + this.y;
            hash = 59 * hash + this.z;
            return hash;
        }
        
    }
}
