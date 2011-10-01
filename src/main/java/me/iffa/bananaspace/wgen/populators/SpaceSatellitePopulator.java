// Package Declaration
package me.iffa.bananaspace.wgen.populators;

// Java Imports
import java.util.Random;

// Bukkit Imports
import me.iffa.bananaspace.api.SpaceConfigHandler;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 * Populates a world with satellites.
 * 
 * @author iffa
 * @author NeonMaster (thanks for the original satellite design, too bad my mathematics blew it up!)
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
        if (random.nextInt(1337) <= SpaceConfigHandler.getSatelliteChance(world)) {
            int height = random.nextInt(128);
            buildSatellite(world, height, source);
            return;
        }
    }

    /**
     * Builds a satellite.
     * 
     * @param world World
     * @param height Height
     * @param source Source chunk
     */
    private void buildSatellite(World world, int height, Chunk source) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 3; y++) {
                source.getBlock(y, height, x).setTypeId(102);
            }
        }
        for (int x = 6; x < 11; x++) {
            for (int y = 0; y < 3; y++) {
                source.getBlock(y, height, x).setTypeId(102);
            }
        }
        for (int y = 0; y < 3; y++) {
            source.getBlock(y, height, 5).setType(Material.IRON_BLOCK);
        }
    }
}
