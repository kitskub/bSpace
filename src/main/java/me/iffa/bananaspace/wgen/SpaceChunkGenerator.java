// Package Declaration
package me.iffa.bananaspace.wgen;

// Java Imports
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

// BananaSpace Imports
import me.iffa.bananaspace.wgen.populators.SpaceStonePopulator;
import me.iffa.bananaspace.wgen.populators.SpaceGlowstonePopulator;
import me.iffa.bananaspace.wgen.populators.SpaceSatellitePopulator;

/**
 * Generates space world (without planets).
 * 
 * @author iffa
 * @author Canis85
 */
public class SpaceChunkGenerator extends ChunkGenerator {
    /**
     * Generates empty chunks.
     * 
     * @param world World
     * @param random Random
     * @param cx X-coord
     * @param cz Z-coord
     * 
     * @return byte[] Bytes
     */
    public byte[] generate(World world, Random random, int cx, int cz) {
        return new byte[32768];
    }

    /**
     * Gets the chunk generator's populators.
     * 
     * @param world World
     * 
     * @return List of BlockPopulators
     */
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList((BlockPopulator) new SpaceGlowstonePopulator(), new SpaceStonePopulator(), new SpaceSatellitePopulator());
    }

    /**
     * Sets the can spawn-state of the world.
     * 
     * @param world World
     * @param x X-location
     * @param z Z-location
     * 
     * @return True
     */
    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    /**
     * Gets a fixed spawn location.
     * 
     * @param world World
     * @param random Random
     * 
     * @return Spawn location
     */
    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 0, 64);
    }
}
