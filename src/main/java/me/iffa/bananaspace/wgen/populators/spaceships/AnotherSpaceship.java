// Package Declaration
package me.iffa.bananaspace.wgen.populators.spaceships;

// Java Imports
import java.util.Random;

// Bukkit Imports
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

/**
 * Test spaceship populator.
 * 
 * @author iffa
 */
public class AnotherSpaceship extends Spaceship {
    /**
     * Constructor of TestSpaceship.
     */
    public AnotherSpaceship() {
        Spaceship.addSpaceship(this);
    }
    
    /**
     * Populates a chunk with a test spaceship.
     * 
     * @param world World
     * @param random Random
     * @param source Source chunk
     */
    @Override
    public void populate(World world, Random random, Chunk source) {
        // TODO: Test populating code
        source.getBlock(0, 64, 0).setType(Material.IRON_BLOCK);
    }
}