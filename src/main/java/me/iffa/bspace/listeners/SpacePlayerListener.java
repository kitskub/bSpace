// Package Declaration
package me.iffa.bspace.listeners;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceConfigHandler;
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpacePlayerHandler;
import me.iffa.bspace.api.event.area.AreaEnterEvent;
import me.iffa.bspace.api.event.area.AreaLeaveEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.economy.Economy;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

/**
 * PlayerListener for general space related actions.
 * 
 * @author iffa
 */
public class SpacePlayerListener extends PlayerListener {
    // Variables
    private final Map<Player, Boolean> inArea = new HashMap<Player, Boolean>();
    private final Map<Player, Boolean> fixDupe = new HashMap<Player, Boolean>();


    /**
     * Called when a player attempts to teleport.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (!fixDupe.containsKey(event.getPlayer())) {
            if (Space.getWorldHandler().isSpaceWorld(event.getTo().getWorld()) && event.getTo().getWorld() != player.getWorld()) {
                if (Bukkit.getPluginManager().getPlugin("Register") != null && !Economy.enter(player)) {
                    SpaceMessageHandler.sendNotEnoughMoneyMessage(player);
                    event.setCancelled(true);
                    return;
                }
                if (SpaceConfigHandler.isHelmetGiven()) {
                    event.getPlayer().getInventory().setHelmet(
                            new ItemStack(SpaceConfigHandler.getHelmetBlock(), 1));
                }
                if (SpaceConfigHandler.isSuitGiven()) {
                    SpacePlayerHandler.giveSpaceSuit(SpaceConfigHandler.getArmorType(), player);
                }
                /* Notify listeners start */
                SpaceEnterEvent e = new SpaceEnterEvent(event.getPlayer(), event.getFrom(), event.getTo());
                Bukkit.getServer().getPluginManager().callEvent(e);
                if (e.isCancelled()) {
                    event.setCancelled(true);
                }
                /* Notify listeners end */
                SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + event.getPlayer().getName() + "' teleported to space.");
                fixDupe.put(event.getPlayer(), true);
            } else if (!Space.getWorldHandler().isSpaceWorld(event.getTo().getWorld())
                    && Space.getWorldHandler().isSpaceWorld(event.getFrom().getWorld())) {
                if (Bukkit.getPluginManager().getPlugin("Register") != null && !Economy.exit(player)) {
                    event.setCancelled(true);
                    return;
                }
                /* Notify listeners start */
                SpaceLeaveEvent e = new SpaceLeaveEvent(event.getPlayer(), event.getFrom(), event.getTo());
                Bukkit.getServer().getPluginManager().callEvent(e);
                if (e.isCancelled()) {
                    event.setCancelled(true);
                }
                /* Notify listeners end */
                
                if (SpaceConfigHandler.isHelmetGiven()) {
                    event.getPlayer().getInventory().setHelmet(new ItemStack(0, 1));
                }
                if (SpaceConfigHandler.isSuitGiven()) {
                    SpacePlayerHandler.giveSpaceSuit("air", player);
                }
            }
        } else {
            fixDupe.clear();
        }
    }

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
        if (Space.getWorldHandler().isInAnySpace(event.getPlayer())) {
            int i = 0;
            Block block = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP);
            boolean insideArea = false;
            while (i < SpaceConfigHandler.getRoomHeight(event.getPlayer().getWorld())) {
                if (block.getTypeId() != 0) {
                    insideArea = true;
                    i = 0;
                    break;
                }
                i++;
                block = block.getRelative(BlockFace.UP);
            }
            if (insideArea == true) {
                if (inArea.containsKey(event.getPlayer())) {
                    if (inArea.get(event.getPlayer()) == false) {
                        inArea.put(event.getPlayer(), true);
                        /* Notify listeners start */
                        AreaEnterEvent e = new AreaEnterEvent(event.getPlayer());
                        Bukkit.getServer().getPluginManager().callEvent(e);
                        /* Notify listeners end */
                        SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + event.getPlayer().getName() + "' entered an area.");
                    }
                } else {
                    inArea.put(event.getPlayer(), true);
                    /* Notify listeners start */
                    AreaEnterEvent e = new AreaEnterEvent(event.getPlayer());
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    /* Notify listeners end */
                }
            } else {
                if (inArea.containsKey(event.getPlayer())) {
                    if (inArea.get(event.getPlayer()) == true) {
                        inArea.put(event.getPlayer(), false);
                        /* Notify listeners start */
                        AreaLeaveEvent e = new AreaLeaveEvent(event.getPlayer());
                        Bukkit.getServer().getPluginManager().callEvent(e);
                        event.setCancelled(e.isCancelled());
                        /* Notify listeners end */
                        SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + event.getPlayer().getName() + "' left an area.");
                    }
                } else {
                    inArea.put(event.getPlayer(), false);
                    /* Notify listeners start */
                    AreaLeaveEvent e = new AreaLeaveEvent(event.getPlayer());
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    event.setCancelled(e.isCancelled());
                    /* Notify listeners end */
                }
            }
        }
    }


    /**
     * Called when a player quits the game.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (SpaceSuffocationListener.taskid.containsKey(event.getPlayer())) {
            if (Bukkit.getScheduler().isCurrentlyRunning(SpaceSuffocationListener.taskid.get(event.getPlayer()))) {
                Bukkit.getScheduler().cancelTask(SpaceSuffocationListener.taskid.get(event.getPlayer()));
                SpaceMessageHandler.debugPrint(Level.INFO, "Cancelled suffocation task for player '" + event.getPlayer().getName() + "'. (reason: left server)");
            }
        }
    }
}