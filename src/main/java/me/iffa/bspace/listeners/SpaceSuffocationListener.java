// Package Declaration
package me.iffa.bspace.listeners;

// Java Imports
import java.util.HashMap;
import java.util.Map;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceConfigHandler;
import me.iffa.bspace.api.SpacePlayerHandler;
import me.iffa.bspace.api.event.area.AreaEnterEvent;
import me.iffa.bspace.api.event.area.AreaLeaveEvent;
import me.iffa.bspace.api.event.area.SpaceAreaListener;
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import me.iffa.bspace.runnables.SuffacationRunnable;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Jack
 */
public class SpaceSuffocationListener extends SpaceAreaListener {
    // Variables
    public static Map<Player, Integer> taskid = new HashMap<Player, Integer>();
    private static Space plugin;

    /**
     * Constructor of SpaceSuffocationListener.
     * 
     * @param plugin Space instance
     */
    public SpaceSuffocationListener(Space plugin) {
        SpaceSuffocationListener.plugin = plugin;
    }

    /**
     * Called when someone enters an area.
     * 
     * @param event Event data 
     */
    @Override
    public void onAreaEnter(AreaEnterEvent event) {
        stopSuffocating(event.getPlayer());
    }

    /**
     * Called when someone leaves an area.
     * 
     * @param event Event data 
     */
    @Override
    public void onAreaLeave(AreaLeaveEvent event) {
        startSuffocating(event.getPlayer());
    }

    /**
     * Called when someone leaves space.
     * 
     * @param event Event data 
     */
    @Override
    public void onSpaceLeave(SpaceLeaveEvent event) {
        stopSuffocating(event.getPlayer());
    }

    /**
     * Called when someone enters space.
     * 
     * @param event Event data 
     */
    @Override
    public void onSpaceEnter(SpaceEnterEvent event) {
        if (!SpacePlayerHandler.insideArea(event.getPlayer())) {
            startSuffocating(event.getPlayer());
        }
    }

    /**
     * Starts suffocation for a player.
     * ¨
     * @param player Player to suffocate
     */
    public static void startSuffocating(Player player) {
        if (player.hasPermission("bSpace.ignoresuitchecks")) {
            return;
        }
        boolean suffocatingOn = (SpaceConfigHandler.getRequireHelmet(player.getWorld()) || SpaceConfigHandler.getRequireSuit(player.getWorld()));
        if (suffocatingOn) {
            SuffacationRunnable task = new SuffacationRunnable(player);
            int taskInt = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
            taskid.put(player, taskInt);
        }
    }

    /**
     * Stops a player from suffocating.
     * 
     * @param player Player to stop suffocating
     * 
     * @return True if suffocating stopped
     */
    public static boolean stopSuffocating(Player player) {
        if (!taskid.containsKey(player)) {
            return false;
        }
        if (Bukkit.getScheduler().isQueued(taskid.get(player))) {
            Bukkit.getScheduler().cancelTask(taskid.get(player));
            taskid.remove(player);
            return true;
        }
        return false;
    }
}
