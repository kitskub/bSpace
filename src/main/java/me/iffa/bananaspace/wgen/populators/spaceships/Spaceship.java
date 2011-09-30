// Package Declaration
package me.iffa.bananaspace.wgen.populators.spaceships;

// Java Imports
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Bukkit Imports
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 * Represents a standard spaceship populator. Should be extended for new spaceships.
 * 
 * @author iffa
 */
public class Spaceship extends BlockPopulator {
    // Variables
    private static List<Spaceship> spaceShips = new ArrayList<Spaceship>();
    
    /**
     * Populates a chunk with the spaceship.
     * 
     * @param world World
     * @param random Random
     * @param source Source chunk
     */
    @Override
    public void populate(World world, Random random, Chunk source) {
    }
    
    /**
     * Gets the list of spaceships.
     * 
     * @return List of spaceships
     */
    public static List<Spaceship> getSpaceships() {
        return spaceShips;
    }
}
