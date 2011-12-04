// Package Declaration
package me.iffa.bspace.runnables;

// Java Imports
import java.util.HashMap;
import java.util.Map;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceSpoutHandler;
import me.iffa.bspace.wgen.blocks.BlackHole;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

// Spout Imports
import org.getspout.spoutapi.block.SpoutBlock;

/**
 * Another class to be called pretty often.
 * 
 * @author iffamies
 */
public class SpoutBlackHoleAreaRunnable implements Runnable {
    // Variables
    public static Map<Entity, Integer> scheduleMap = new HashMap<Entity, Integer>();

    /**
     * Magic happens.
     */
    @Override
    public void run() {
        if (Space.getWorldHandler().getSpaceWorlds().isEmpty()) {
            return;
        }
        if (BlackHole.getHolesList().isEmpty()) {
            return;
        }
        for (World world : Space.getWorldHandler().getSpaceWorlds()) {
            for (Entity entity : world.getEntities()) {
                for (SpoutBlock block : BlackHole.getHolesList()) {
                    if (SpaceSpoutHandler.isInsideRadius(entity, block.getLocation() , 16)) {
                        scheduleMap.put(entity, Space.scheduler.scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("bSpace"), new SpoutBlackHoleChaosRunnable(entity, block), 1, 1));
                    }
                }
            }
        }
    }
}
