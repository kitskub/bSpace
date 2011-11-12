// Package Declaration
package me.iffa.bananaspace.wgen.populators;

// Java Imports
import java.util.Random;

// BananaSpace Imports
import java.util.logging.Level;
import me.iffa.bananaspace.api.SpaceConfigHandler;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.api.SpaceSchematicHandler;
import me.iffa.bananaspace.api.schematic.Schematic;

// Bukkit Imports
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 * Populates a world with schematics.
 * 
 * @author iffamies
 */
public class SpaceSchematicPopulator extends BlockPopulator {
    /**
     * Populates a chunk with schematics.
     * 
     * @param world World
     * @param random Random
     * @param chunk Source chunk
     */
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (SpaceSchematicHandler.getSchematics().isEmpty()) {
            return;
        }
        int y = new Random().nextInt(127);
        while (y < 30) {
            y = new Random().nextInt(127);
        }
        Schematic randomSchematic = SpaceSchematicHandler.getSchematics().get(new Random().nextInt(SpaceSchematicHandler.getSchematics().size()));
        if (new Random().nextInt(200) <= SpaceConfigHandler.getSchematicChance(world)) {
            SpaceMessageHandler.debugPrint(Level.INFO, "Starting schematic population process with schematic '" + randomSchematic.getName() + "'.");
            SpaceSchematicHandler.placeSchematic(randomSchematic, new Location(world, chunk.getX(), y, chunk.getZ()));
        }
    }
}
