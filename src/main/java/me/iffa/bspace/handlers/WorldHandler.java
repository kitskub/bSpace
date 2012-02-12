// Package Declaation
package me.iffa.bspace.handlers;

import me.iffa.bspace.api.SpaceWorldHandler;
import org.bukkit.World;

/**
 * Class that handles space worlds.
 * Internal use only
 *
 * @author Jack
 */
public class WorldHandler extends SpaceWorldHandler {
    /**
     * Removes a space world from the list of space worlds.
     * 
     * @param world World to remove
     */
    public static void removeSpaceWorld(World world) {
        if (spaceWorldNames.contains(world.getName())) {
            spaceWorldNames.remove(world.getName());
        }
    }
    private WorldHandler() {
    }
}
