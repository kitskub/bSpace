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
    private int taskInt;
    private Space plugin = (Space) Bukkit.getPluginManager().getPlugin("bSpace");

    @Override
    public void onAreaEnter(AreaEnterEvent event){
        if (isVulnerable.containsKey(event.getPlayer())) {
            if (isVulnerable.get(event.getPlayer()) == true) {
                Bukkit.getScheduler().cancelTask(taskid.get(event.getPlayer()));
                isVulnerable.put(event.getPlayer(), false);
            }
        }        
    }

    @Override
    public void onAreaLeave(AreaLeaveEvent event) {
        if (!event.getPlayer().hasPermission("bSpace.ignoresuitchecks")) {
            Player player = event.getPlayer();
            boolean suffocatingOn = (SpaceConfigHandler.getRequireHelmet(player.getWorld())||SpaceConfigHandler.getRequireSuit(player.getWorld()));
            if(suffocatingOn){
                SuffacationRunnable task = new SuffacationRunnable(player);
                taskInt = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
                taskid.put(player, taskInt);
                isVulnerable.put(player, true);
            }
        }
    }

    @Override
    public void onSpaceLeave(SpaceLeaveEvent event) {
        if (isVulnerable.containsKey(event.getPlayer())) {
                if (isVulnerable.get(event.getPlayer()) == true) {
                    Bukkit.getScheduler().cancelTask(taskid.get(event.getPlayer()));
                }
        }
    }
    
    @Override
    public void onSpaceEnter(SpaceEnterEvent event) {
        if (!event.getPlayer().hasPermission("bSpace.ignoresuitchecks")) {
            Player player = event.getPlayer();
            boolean suffocatingOn = (SpaceConfigHandler.getRequireHelmet(player.getWorld())||SpaceConfigHandler.getRequireSuit(player.getWorld()));
            if(suffocatingOn){
                SuffacationRunnable task = new SuffacationRunnable(player);
                taskInt = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
                taskid.put(player, taskInt);
                isVulnerable.put(player, true);
            }
        }
    }
    
}
