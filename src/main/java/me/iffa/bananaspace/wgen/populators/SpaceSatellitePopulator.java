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
            int height = random.nextInt(128);
            // TODO: Satellite code
            buildGlassPane1(world, height, source);
        }
    }

    /**
     * Builds the first glass pane "wing".
     * 
     * @param world World
     * @param height Height
     * @param source Source chunk
     */
    private void buildGlassPane1(World world, int height, Chunk source) {
        // GLASS PANE
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 3; y++) {
                source.getBlock(y, height, x).setTypeId(102);
            }
        }
        // GLASS
        for (int y = 0; y < 3; y++) {
            source.getBlock(y, height, 5).setType(Material.GLASS);
        }
        source.getBlock(1, height, 6).setType(Material.FENCE_GATE);
    }

    /**
     * Builds the second glass pane "wing".
     * 
     * @param world World
     * @param source Source chunk
     */
    private void buildGlassPane2(World world, Chunk source) {
    }
}
