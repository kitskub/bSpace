// Package Declaration
package me.iffa.bspace.listeners.misc;

// Java Imports
import java.util.HashMap;
import java.util.Map;

// bSpace Imports
import me.iffa.bspace.api.SpaceSpoutHandler;
import me.iffa.bspace.runnables.SpoutBlackHoleChaosRunnable;
import me.iffa.bspace.wgen.blocks.BlackHole;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

// Spout Imports
import org.getspout.spoutapi.block.SpoutBlock;

/**
 * Player listener to trigger black hole sucking.
 * 
 * @author iffamies
 */
public class BlackHolePlayerListener extends PlayerListener {
    // Variables
    private static Map<Entity, Integer> runnables = new HashMap<Entity, Integer>();

    /**
     * Called when a player attempts to move.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (runnables.containsKey(event.getPlayer())) {
            return;
        }
        for (SpoutBlock block : BlackHole.getHolesList()) {
            if (SpaceSpoutHandler.isInsideRadius(event.getPlayer(), block.getLocation(), 16)) {
                runnables.put(event.getPlayer(), Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("bSpace"), new SpoutBlackHoleChaosRunnable(event.getPlayer(), block), 1, 1));
            }
        }
    }

    /**
     * Gets all running suck tasks.
     * 
     * @return All tasks
     */
    public static Map<Entity, Integer> getRunningTasks() {
        return runnables;
    }
}
