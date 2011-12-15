/*
 */
package me.iffa.bspace.listeners;

import java.util.HashMap;
import java.util.Map;
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceConfigHandler;
import me.iffa.bspace.api.event.area.AreaEnterEvent;
import me.iffa.bspace.api.event.area.AreaLeaveEvent;
import me.iffa.bspace.api.event.area.SpaceAreaListener;
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import me.iffa.bspace.runnables.SuffacationRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Jack
 */
public class SpaceSuffocationListener extends SpaceAreaListener {
    private static Map<Player,Boolean> isVulnerable = new HashMap<Player,Boolean>();
    public static Map<Player, Integer> taskid = new HashMap<Player, Integer>();
    private static Space plugin = (Space) Bukkit.getPluginManager().getPlugin("bSpace");

    public SpaceSuffocationListener(Space plugin) {
        SpaceSuffocationListener.plugin = plugin;
    }
    
    @Override
    public void onAreaEnter(AreaEnterEvent event){
        stopSuffocating(event.getPlayer());
    }

    @Override
    public void onAreaLeave(AreaLeaveEvent event) {
        startSuffocating(event.getPlayer());
    }

    @Override
    public void onSpaceLeave(SpaceLeaveEvent event) {
        stopSuffocating(event.getPlayer());
    }
    
    @Override
    public void onSpaceEnter(SpaceEnterEvent event) {     
        startSuffocating(event.getPlayer());
    }
    
    public static void setVulnerable(Player player, boolean v){
        isVulnerable.put(player, v);
    }
    
    public static void startSuffocating(Player player){
        if (player.hasPermission("bSpace.ignoresuitchecks")) return;
        boolean suffocatingOn = (SpaceConfigHandler.getRequireHelmet(player.getWorld())||SpaceConfigHandler.getRequireSuit(player.getWorld()));
            if(suffocatingOn){
                SuffacationRunnable task = new SuffacationRunnable(player);
                taskid.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, 20L, 20L));
                isVulnerable.put(player, true);
            }
    }
    
    public static boolean stopSuffocating(Player player){
        if (isVulnerable.containsKey(player) && Bukkit.getScheduler().isCurrentlyRunning(taskid.get(player))) {
            if (isVulnerable.get(player) == true) {
                Bukkit.getScheduler().cancelTask(taskid.get(player));
                isVulnerable.put(player, false);
                taskid.remove(player);
            }
            return true;
        }
        return false;
    }
}
