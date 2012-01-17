// Package Declaration
package me.iffa.bspace.wgen.populators;

// Java Imports
import java.util.Random;
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.api.schematic.SpaceSchematicHandler;
import me.iffa.bspace.api.schematic.Schematic;
import me.iffa.bspace.handlers.ConfigHandler;
import me.iffa.bspace.handlers.MessageHandler;

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
        int y = new Random().nextInt(128);
        String id = ConfigHandler.getID(world);
        Schematic randomSchematic = SpaceSchematicHandler.getSchematics().get(new Random().nextInt(SpaceSchematicHandler.getSchematics().size()));
        if (new Random().nextInt(200) <= ConfigHandler.getSchematicChance(id)) {
            MessageHandler.debugPrint(Level.INFO, "Starting schematic population process with schematic '" + randomSchematic.getName() + "'.");
            SpaceSchematicHandler.placeSchematic(randomSchematic, new Location(world, (chunk.getX() << 4) + new Random().nextInt(10), y, (chunk.getZ() << 4) + new Random().nextInt(10)));
        }
    }
}
