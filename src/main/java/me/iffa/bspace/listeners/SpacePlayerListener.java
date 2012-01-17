// Package Declaration
package me.iffa.bspace.listeners;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.api.event.area.AreaEnterEvent;
import me.iffa.bspace.api.event.area.AreaLeaveEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.PlayerHandler;
import me.iffa.bspace.handlers.WorldHandler;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * PlayerListener for general space related actions.
 * 
 * @author iffa
 */
public class SpacePlayerListener extends PlayerListener {
    // Variables
    private final Map<Player, Boolean> inArea = new HashMap<Player, Boolean>();
    //private final Map<Player, Boolean> fixDupe = new HashMap<Player, Boolean>();


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
        if (WorldHandler.isSpaceWorld(event.getTo().getWorld()) && event.getTo().getWorld() != event.getFrom().getWorld()) {
            /* Notify listeners start */
            SpaceEnterEvent e = new SpaceEnterEvent(event.getPlayer(), event.getFrom(), event.getTo());
            Bukkit.getServer().getPluginManager().callEvent(e);
            if (e.isCancelled()) {
                event.setCancelled(true);
                return;
            }
            /* Notify listeners end */
            MessageHandler.debugPrint(Level.INFO, "Player '" + event.getPlayer().getName() + "' teleported to space.");
            PlayerHandler.giveSuitOrHelmet(player);
        } else if (!WorldHandler.isSpaceWorld(event.getTo().getWorld())
                && WorldHandler.isSpaceWorld(event.getFrom().getWorld())) {
            /* Notify listeners start */
            SpaceLeaveEvent e = new SpaceLeaveEvent(event.getPlayer(), event.getFrom(), event.getTo());
            Bukkit.getServer().getPluginManager().callEvent(e);
            if (e.isCancelled()) {
                event.setCancelled(true);
                return;
            }
            /* Notify listeners end */
            PlayerHandler.removeSuitOrHelmet(player);
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
        if (WorldHandler.isInAnySpace(event.getPlayer())) {
            if(!inArea.containsKey(event.getPlayer())) inArea.put(event.getPlayer(), false);
            boolean insideArea=PlayerHandler.insideArea(event.getPlayer());
            if (insideArea == true) {
                if (inArea.get(event.getPlayer()) == false) {
                    inArea.put(event.getPlayer(), true);
                    /* Notify listeners start */
                    AreaEnterEvent e = new AreaEnterEvent(event.getPlayer());
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    /* Notify listeners end */
                    MessageHandler.debugPrint(Level.INFO, "Player '" + event.getPlayer().getName() + "' entered an area.");
                }
            } else {
                if (inArea.get(event.getPlayer()) == true) {
                    inArea.put(event.getPlayer(), false);
                    /* Notify listeners start */
                    AreaLeaveEvent e = new AreaLeaveEvent(event.getPlayer());
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    /* Notify listeners end */
                    MessageHandler.debugPrint(Level.INFO, "Player '" + event.getPlayer().getName() + "' left an area.");
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
        if(SpaceSuffocationListener.stopSuffocating(event.getPlayer())){
            MessageHandler.debugPrint(Level.INFO, "Cancelled suffocation task for player '" + event.getPlayer().getName() + "'. (reason: left server)");
        }
       if (inArea.containsKey(event.getPlayer())) {
           inArea.remove(event.getPlayer());
       }
    }
    
    /**
     * Called when a player quits the game.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(!WorldHandler.isSpaceWorld(
            event.getPlayer().getWorld())) return;
        boolean insideArea = PlayerHandler.insideArea(event.getPlayer());
        inArea.put(event.getPlayer(), insideArea);
        SpaceEnterEvent e = new SpaceEnterEvent(event.getPlayer(),null,event.getPlayer().getLocation());
        Bukkit.getServer().getPluginManager().callEvent(e);
    }
    /**
     * Called on player respawn
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if(WorldHandler.isSpaceWorld(event.getRespawnLocation().getWorld())){
            PlayerHandler.giveSuitOrHelmet(event.getPlayer());
            boolean insideArea = PlayerHandler.insideArea(event.getPlayer());
            inArea.put(event.getPlayer(), insideArea);
            SpaceEnterEvent e = new SpaceEnterEvent(event.getPlayer(),null,event.getRespawnLocation());
            Bukkit.getServer().getPluginManager().callEvent(e);
        }
        else{
            SpaceSuffocationListener.stopSuffocating(event.getPlayer());
        }
    }
    
    
}