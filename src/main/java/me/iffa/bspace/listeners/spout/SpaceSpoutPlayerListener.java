// Package Declaration
package me.iffa.bspace.listeners.spout;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.handlers.PlayerHandler;
import me.iffa.bspace.handlers.SpoutHandler;
import me.iffa.bspace.handlers.WorldHandler;

// Bukkit Imports
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SkyManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * PlayerListener for things which require Spout.
 * 
 * @author iffa
 * @author HACKhalo2
 */
public class SpaceSpoutPlayerListener implements Listener {
    // Variables
    private final SkyManager sky = SpoutManager.getSkyManager();
    private final Space plugin;

    /**
     * Constructor for SpaceSpoutPlayerListener.
     * 
     * @param plugin bSpace instance
     */
    public SpaceSpoutPlayerListener(Space plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when a player attempts to teleport.
     * 
     * 
     * @param event Event data
     * @deprecated Why?
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        if (event.isCancelled() || !player.isSpoutCraftEnabled() || event.getFrom().getWorld().equals(event.getTo().getWorld())
                || (WorldHandler.isSpaceWorld(event.getFrom().getWorld()) && WorldHandler.isSpaceWorld(event.getTo().getWorld()))) {
            //Return if the event is canceled, if player doesn't have spoutcraft, if teleporting interworld, or it teleporting between space worlds
            return;
        }
        /* Player teleports to spaceworld */
        if (WorldHandler.isSpaceWorld(event.getTo().getWorld())) {
            SpoutHandler.setOrReset(plugin, player, event.getTo());
            if(!PlayerHandler.insideArea(player)){
                SpoutHandler.setGravity(player);
            }
        }
        /* Player teleports out of spaceworld */
        if (WorldHandler.isSpaceWorld(event.getFrom().getWorld())) {
            SpoutHandler.setOrReset(plugin, player, event.getFrom());
            SpoutHandler.resetGravity(player);
        }
    }
    
    /**
     * Called when a player respawns.
     * 
     * @param event Event data
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        SpoutHandler.setOrReset(plugin, player, event.getRespawnLocation());
        if (WorldHandler.isSpaceWorld(event.getRespawnLocation().getWorld())) {
            if(!PlayerHandler.insideArea(player)){
                SpoutHandler.setGravity(player);
                return;
            }
        }
        SpoutHandler.resetGravity(player);
    }
}
