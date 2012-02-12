// Package Declaration
package me.iffa.bspace.listeners.misc;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.handlers.ConfigHandler;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.WorldHandler;
import me.iffa.bspace.wgen.planets.PlanetsChunkGenerator;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

/**
 * Listener for world load events etc.
 *
 * @author iffamies
 */
public class SpaceWorldListener implements Listener {

    /**
     * Called when a world is loaded.
     *
     * @param event Event data
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        if (!(world.getGenerator() instanceof PlanetsChunkGenerator)) {
            return;
        }
        String id = ConfigHandler.getID(world);
        if (ConfigHandler.forceNight(id)) {
            WorldHandler.startForceNightTask(world);
            MessageHandler.debugPrint(Level.INFO, "Started night forcing task for world '" + world.getName() + "'.");
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldUnload(WorldUnloadEvent event) {
        World world = event.getWorld();
        if (!(world.getGenerator() instanceof PlanetsChunkGenerator)) {
            return;
        }
        String id = ConfigHandler.getID(world);
        if (ConfigHandler.forceNight(id)) {
            WorldHandler.stopForceNightTask(world);
            MessageHandler.debugPrint(Level.INFO, "Stopped night forcing task for world '" + world.getName() + "'. Reason: World unload");
        }
        WorldHandler.removeSpaceWorld(world);
    }
}
