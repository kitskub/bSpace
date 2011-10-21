/*
 */
package me.iffa.bananaspace.wgen.planets;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.config.SpacePlanetConfig;
import me.iffa.bananaspace.wgen.populators.PlanetPopulator;
import me.iffa.bananaspace.wgen.populators.SpaceGlowstonePopulator;
import me.iffa.bananaspace.wgen.populators.SpaceSatellitePopulator;
import me.iffa.bananaspace.wgen.populators.SpaceStonePopulator;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Jack
 */
public class NewPlanetsChunkGenerator extends ChunkGenerator {
    // Variables
    private int floorHeight; // Floor height
    private Material floorBlock; // BlockID for the floor

    public NewPlanetsChunkGenerator() {
        floorBlock = Material.matchMaterial(SpacePlanetConfig.getConfig().getString("floorBlock", "STATIONARY_WATER"));
        this.floorHeight = SpacePlanetConfig.getConfig().getInt("floorHeight", 0);
    }
    
    @Override
    public byte[] generate(World world, Random random, int x, int z) {
        byte[] retVal = new byte[32768];
        // Fill in the floor
        for (int floorY = 0; floorY < floorHeight; floorY++) {
            for (int floorX = 0; floorX < 16; floorX++) {
                for (int floorZ = 0; floorZ < 16; floorZ++) {
                    if (floorY == 0) {
                        retVal[floorX * 2048 + floorZ * 128 + floorY] = (byte) Material.BEDROCK.getId();
                    } else {
                        retVal[floorX * 2048 + floorZ * 128 + floorY] = (byte) floorBlock.getId();
                    }
                }
            }
        }
        return retVal;
    }
    
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList((BlockPopulator)
                new PlanetPopulator(), 
                new SpaceGlowstonePopulator(), 
                new SpaceStonePopulator(), 
                new SpaceSatellitePopulator());
    }
}
