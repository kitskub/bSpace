// Package Declaration
package me.iffa.bananaspace.runnable;

// Bukkit Imports
import org.bukkit.World;

/**
 * A runnable class for forcing night.
 * 
 * @author iffa
 */
public class SpaceRunnable implements Runnable {
    // Variables
    private World world;

    /**
     * Constructor for SpaceRunnable.
     * 
     * @param world World
     */
    public SpaceRunnable(World world) {
        this.world = world;
    }

    /**
     * Forces night when repeated every 8399 ticks.
     */
    @Override
    public void run() {
        world.setTime(13801);
    }
}
