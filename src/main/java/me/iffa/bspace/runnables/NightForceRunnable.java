// Package Declaration
package me.iffa.bspace.runnables;

// Bukkit Imports
import org.bukkit.World;

/**
 * A runnable class for forcing night.
 * 
 * @author iffa
 */
public class NightForceRunnable implements Runnable {
    // Variables
    private World world;

    /**
     * Constructor for NightForceRunnable.
     * 
     * @param world World
     */
    public NightForceRunnable(World world) {
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
