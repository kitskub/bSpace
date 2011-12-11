// Package Declaration
package me.iffa.bspace.wgen.populators;

// Java Imports
import java.util.Random;

// bSpace Imports
import me.iffa.bspace.wgen.blocks.BlackHole;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;

/**
 * Populates
 * 
 * @author Jack
 * @author iffa
 */
public class SpaceBlackHolePopulator extends BlockPopulator {
    /**
     * Populates a chunk with black holes.
     * 
     * @param world World
     * @param random Random
     * @param source Source chunk
     */
    @Override
    public void populate(World world, Random random, Chunk source) {
        if (random.nextInt(100) <= 15) { // If you are going to make a random amount, use 100/ something to make a real percentage to understand it easier
            short[] blockIds = new short[16*16*128];
            int chunkX = source.getX();
            int chunkZ = source.getZ();
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int y = random.nextInt(127);
            blockIds[(x * 16 + z) * 128 + y] = (short) new BlackHole(Bukkit.getPluginManager().getPlugin("bSpace")).getBlockId();
            SpoutManager.getChunkDataManager().setCustomBlockIds(world, chunkX, chunkZ, blockIds);
        }
    }
}
