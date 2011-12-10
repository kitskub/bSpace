// Package Declaration
package me.iffa.bspace.listeners.misc;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.api.SpaceConfigHandler;
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpaceWorldHandler;
import me.iffa.bspace.wgen.planets.PlanetsChunkGenerator;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

/**
 * Listener for world load events etc.
 * 
 * @author iffamies
 */
public class SpaceWorldListener extends WorldListener {

    /**
     * Called when a world is loaded.
     * 
     * @param event Event data
     */
    @Override
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        if (!(world.getGenerator() instanceof PlanetsChunkGenerator)) {
            return;
        }
        if (SpaceConfigHandler.forceNight(world)) {
            SpaceWorldHandler.startForceNightTask(world);
            SpaceMessageHandler.debugPrint(Level.INFO, "Started night forcing task for world '" + world.getName() + "'.");
        }

    }
}
