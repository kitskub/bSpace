// Package Declaration
package me.iffa.bspace.wgen.populators;

// Java Imports
import java.util.Random;

// bSpace Imports
import me.iffa.bspace.api.SpaceSpoutHandler;

// Bukkit Imports
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutBlock;

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
        if (random.nextInt(100) <= 10) { // If you are going to make a random amount, use 100/ something to make a real percentage to understand it easier
            //short[] blockIds = new short[16*16*128];
            int chunkX = source.getX();
            int chunkZ = source.getZ();
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int y = random.nextInt(127);
            //blockIds[(x * 16 + z) * 128 + y] = (short) SpaceSpoutHandler.blackHole.getCustomId();
            //SpoutManager.getChunkDataManager().setCustomBlockIds(world, chunkX, chunkZ, blockIds);
            SpoutBlock sb = (SpoutBlock)world.getBlockAt((chunkX*16+x), y, (chunkZ*16+z));
            //sb.setCustomBlock(SpaceSpoutHandler.blackHole);
            //SpoutManager.getMaterialManager().overrideBlock(world, (chunkX*16+x), y, (chunkZ*16+z), SpaceSpoutHandler.blackHole);
            SpoutManager.getMaterialManager().overrideBlock(sb, SpaceSpoutHandler.blackHole);
        }
    }
}
