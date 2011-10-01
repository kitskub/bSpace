// Package Declaration
package me.iffa.bananaspace.wgen.populators;

// Java Imports
import java.util.Random;

// Bukkit Imports
import me.iffa.bananaspace.api.SpaceConfigHandler;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 * Populates a world with satellites.
 * 
 * @author iffa
 */
public class SpaceSatellitePopulator extends BlockPopulator {
    /**
     * Populates a world with satellites.
     * 
     * @param world World
     * @param random Random
     * @param source Source chunk
     */
    @Override
    public void populate(World world, Random random, Chunk source) {
        if (!SpaceConfigHandler.getSatellitesEnabled(world)) {
            return;
        }
        if (random.nextInt(200) <= SpaceConfigHandler.getSatelliteChance(world)) {
            // TODO: Satellite code
        }
    }
    
}
