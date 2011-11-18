// Package Declaration
package me.iffa.bspace.wgen.populators;

// Java Imports
import java.util.Random;

// bSpace Imports
import me.iffa.bspace.wgen.blocks.BlackHole;

// Bukkit Imports
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.Plugin;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;

/**
 * Populates
 * 
 * @author Jack
 * @author iffa
 */
public class SpaceBlackHolePopulator extends BlockPopulator {
    // Variables
    private Plugin plugin;
    
    /**
     * Constructor of SpaceBlackHolePopulator.
     * 
     * @param plugin Plugin instance
     */
    public SpaceBlackHolePopulator(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Populates a chunk with black holes.
     * 
     * @param world World
     * @param random Random
     * @param source Source chunk
     */
    @Override
    public void populate(World world, Random random, Chunk source) {
        if (random.nextInt(100) <= 50) {//TODO replace with chance
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int y = random.nextInt(127);
            Block currentBlock = source.getBlock(x, y, z);
            SpoutManager.getMaterialManager().overrideBlock(currentBlock, new BlackHole(plugin));
        }
    }
}
