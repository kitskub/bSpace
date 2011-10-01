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
    private static List<Spaceship> spaceships = new ArrayList<Spaceship>();
    // Spaceships
    public static TestSpaceship testSpaceship = new TestSpaceship();
    public static AnotherSpaceship anotherSpaceship = new AnotherSpaceship();

    /**
     * Populates a chunk with a random spaceship.
     * 
     * @param world World
     * @param random Random
     * @param source Source chunk
     */
    @Override
    public void populate(World world, Random random, Chunk source) {
        // TODO: Spaceship chance code, let's call it 20 for now.
        if (random.nextInt(100) < 50) {
            getRandomShip().populate(world, random, source);
        }
    }

    /**
     * Gets the list of spaceships.
     * 
     * @return List of spaceships
     */
    public static List<Spaceship> getSpaceships() {
        return spaceships;
    }

    /**
     * Adds a spaceship to the list of spaceships.
     * 
     * @param ship Spaceship to add
     */
    public static void addSpaceship(Spaceship ship) {
        spaceships.add(ship);
    }

    /**
     * Gets a random spaceship.
     * 
     * @return Random spaceship
     */
    public static Spaceship getRandomShip() {
        return spaceships.get(new Random().nextInt(spaceships.size()));
    }
}
