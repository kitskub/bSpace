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
import me.iffa.bananaspace.wgen.populators.SpaceAsteroidPopulator;
import me.iffa.bananaspace.wgen.populators.SpaceGlowstonePopulator;

/**
 * Generates space world (without planets).
 * 
 * @author iffa
 * @author Canis85
 * 
 */
public class SpaceChunkGenerator extends ChunkGenerator {
    /**
     * Generates empty chunks.
     * 
     * @param world
     * @param random
     * @param cx
     * @param cz
     * 
     * @return byte[]
     */
    public byte[] generate(World world, Random random, int cx, int cz) {
        return new byte[32768];
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList((BlockPopulator) new SpaceGlowstonePopulator(), new SpaceAsteroidPopulator());
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 0, 64);
    }
}
