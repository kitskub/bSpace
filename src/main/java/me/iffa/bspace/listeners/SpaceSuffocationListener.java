/*
 */
package me.iffa.bspace.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceConfigHandler;
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpacePlayerHandler;
import me.iffa.bspace.api.event.area.AreaEnterEvent;
import me.iffa.bspace.api.event.area.AreaLeaveEvent;
import me.iffa.bspace.api.event.area.SpaceAreaListener;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import me.iffa.bspace.api.event.misc.SpaceSuffocationEvent;
import me.iffa.bspace.runnables.SuffacationRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Jack
 */
public class SpaceSuffocationListener extends SpaceAreaListener {
    private static Map<Player,Boolean> isSuffocating = new HashMap<Player,Boolean>();
    public static Map<Player, Integer> taskid = new HashMap<Player, Integer>();
    private int taskInt;
    private Space plugin = (Space) Bukkit.getPluginManager().getPlugin("bSpace");

    @Override
    public void onAreaEnter(AreaEnterEvent event){
        if (isSuffocating.containsKey(event.getPlayer())) {
            if (isSuffocating.get(event.getPlayer()) == true) {
                Space.scheduler.cancelTask(taskid.get(event.getPlayer()));
                isSuffocating.put(event.getPlayer(), false);
            }
        }        
    }

    @Override
    public void onAreaLeave(AreaLeaveEvent event) {
        if (!event.getPlayer().hasPermission("bSpace.ignoresuitchecks")) {
            if (SpaceConfigHandler.getRequireHelmet(event.getPlayer().getWorld()) && SpaceConfigHandler.getRequireSuit(event.getPlayer().getWorld())) {
                checkNeedsSuffocation(SuitCheck.BOTH, event.getPlayer());
                return;
            } else if (SpaceConfigHandler.getRequireHelmet(event.getPlayer().getWorld())) {
                checkNeedsSuffocation(SuitCheck.HELMET_ONLY, event.getPlayer());
                return;
            } else if (SpaceConfigHandler.getRequireSuit(event.getPlayer().getWorld())) {
                checkNeedsSuffocation(SuitCheck.SUIT_ONLY, event.getPlayer());
                return;
            }
        }
    }

    @Override
    public void onSpaceLeave(SpaceLeaveEvent event) {
        if (isSuffocating.containsKey(event.getPlayer())) {
                if (isSuffocating.get(event.getPlayer()) == true) {
                    Space.scheduler.cancelTask(taskid.get(event.getPlayer()));
                }
        }
    }
    
    /**
     * Checks if a player should start suffocating.
     * 
     * @param suit SuitCheck
     * @param player Player
     */
    private void checkNeedsSuffocation(SuitCheck suit, Player player) {
        if (suit == SuitCheck.SUIT_ONLY) {
            if (isSuffocating.containsKey(player)) {
                if (isSuffocating.get(player) == true && SpacePlayerHandler.hasSuit(player, SpaceConfigHandler.getArmorType())) {
                    Space.scheduler.cancelTask(taskid.get(player));
                    isSuffocating.put(player, false);
                } else if (isSuffocating.get(player) == false && !SpacePlayerHandler.hasSuit(player, SpaceConfigHandler.getArmorType())) {
                    /* Notify listeners start */
                    SpaceSuffocationEvent e = new SpaceSuffocationEvent("SpaceSuffocationEvent", player);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (e.isCancelled()) {
                        return;
                    }
                    /* Notify listeners end */
                    SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + player.getName() + "' started suffocating in space.");
                    SuffacationRunnable task = new SuffacationRunnable(player);
                    taskInt = Space.scheduler.scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
                    taskid.put(player, taskInt);
                    isSuffocating.put(player, true);

                }
            } else {
                if (SpacePlayerHandler.hasSuit(player, SpaceConfigHandler.getArmorType())) {
                    isSuffocating.put(player, false);
                } else {
                    /* Notify listeners start */
                    SpaceSuffocationEvent e = new SpaceSuffocationEvent("SpaceSuffocationEvent", player);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (e.isCancelled()) {
                        return;
                    }
                    /* Notify listeners end */
                    SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + player.getName() + "' started suffocating in space.");
                    SuffacationRunnable task = new SuffacationRunnable(player);
                    taskInt = Space.scheduler.scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
                    taskid.put(player, taskInt);
                    isSuffocating.put(player, true);
                }
            }
        } else if (suit == SuitCheck.HELMET_ONLY) {
            if (isSuffocating.containsKey(player)) {
                if (isSuffocating.get(player) == true && player.getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock()) {
                    Space.scheduler.cancelTask(taskid.get(player));
                    isSuffocating.put(player, false);
                } else if (isSuffocating.get(player) == false && player.getInventory().getHelmet().getTypeId() != SpaceConfigHandler.getHelmetBlock()) {
                    /* Notify listeners start */
                    SpaceSuffocationEvent e = new SpaceSuffocationEvent("SpaceSuffocationEvent", player);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (e.isCancelled()) {
                        return;
                    }
                    /* Notify listeners end */
                    SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + player.getName() + "' started suffocating in space.");
                    SuffacationRunnable task = new SuffacationRunnable(player);
                    taskInt = Space.scheduler.scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
                    taskid.put(player, taskInt);
                    isSuffocating.put(player, true);

                }
            } else {
                if (player.getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock()) {
                    isSuffocating.put(player, false);
                } else {
                    /* Notify listeners start */
                    SpaceSuffocationEvent e = new SpaceSuffocationEvent("SpaceSuffocationEvent", player);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (e.isCancelled()) {
                        return;
                    }
                    /* Notify listeners end */
                    SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + player.getName() + "' started suffocating in space.");
                    SuffacationRunnable task = new SuffacationRunnable(player);
                    taskInt = Space.scheduler.scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
                    taskid.put(player, taskInt);
                    isSuffocating.put(player, true);
                }
            }
        } else if (suit == SuitCheck.BOTH) {
            if (isSuffocating.containsKey(player)) {
                if (isSuffocating.get(player) == true && player.getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock() && SpacePlayerHandler.hasSuit(player, SpaceConfigHandler.getArmorType())) {
                    Space.scheduler.cancelTask(taskid.get(player));
                    isSuffocating.put(player, false);
                } else if (isSuffocating.get(player) == false && player.getInventory().getHelmet().getTypeId() != SpaceConfigHandler.getHelmetBlock() || !SpacePlayerHandler.hasSuit(player, SpaceConfigHandler.getArmorType())) {
                    /* Notify listeners start */
                    SpaceSuffocationEvent e = new SpaceSuffocationEvent("SpaceSuffocationEvent", player);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (e.isCancelled()) {
                        return;
                    }
                    /* Notify listeners end */
                    SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + player.getName() + "' started suffocating in space.");
                    SuffacationRunnable task = new SuffacationRunnable(player);
                    taskInt = Space.scheduler.scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
                    taskid.put(player, taskInt);
                    isSuffocating.put(player, true);
                }
            } else {
                if (player.getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock() && SpacePlayerHandler.hasSuit(player, SpaceConfigHandler.getArmorType())) {
                    isSuffocating.put(player, false);
                } else {
                    /* Notify listeners start */
                    SpaceSuffocationEvent e = new SpaceSuffocationEvent("SpaceSuffocationEvent", player);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (e.isCancelled()) {
                        return;
                    }
                    /* Notify listeners end */
                    SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + player.getName() + "' started suffocating in space.");
                    SuffacationRunnable task = new SuffacationRunnable(player);
                    taskInt = Space.scheduler.scheduleSyncRepeatingTask(plugin, task, 20L, 20L);
                    taskid.put(player, taskInt);
                    isSuffocating.put(player, true);
                }
            }
        }
    }
        
    /**
     * Enum to make things easier.
     */
    private enum SuitCheck {
        // Enums

        HELMET_ONLY,
        SUIT_ONLY,
        BOTH;
    }
    
}
