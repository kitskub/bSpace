/*
 */
package me.iffa.bspace.listeners;

import java.util.HashMap;
import java.util.Map;
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceConfigHandler;
import me.iffa.bspace.api.SpacePlayerHandler;
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
        if(!SpacePlayerHandler.insideArea(event.getPlayer())){
            startSuffocating(event.getPlayer());
        }
    }
    
    public static void startSuffocating(Player player){
        if (player.hasPermission("bSpace.ignoresuitchecks")) return;
        boolean suffocatingOn = (SpaceConfigHandler.getRequireHelmet(player.getWorld())||SpaceConfigHandler.getRequireSuit(player.getWorld()));
        if(suffocatingOn){
            SuffacationRunnable task = new SuffacationRunnable(player);
            int taskInt = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
            taskid.put(player, taskInt);
            isVulnerable.put(player, true);
        }
    }
    
    public static boolean stopSuffocating(Player player){
        if(!taskid.containsKey(player)) return false;
        if (isVulnerable.containsKey(player)) {
            if(isVulnerable.get(player)==true && Bukkit.getScheduler().isQueued(taskid.get(player))){
                Bukkit.getScheduler().cancelTask(taskid.get(player));
                isVulnerable.put(player, false);
                taskid.remove(player);
            }
            return true;
        }
        return false;
    }
}
