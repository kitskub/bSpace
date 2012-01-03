// Package Declaration
package me.iffa.bspace.listeners.spout;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceSpoutHandler;
import me.iffa.bspace.api.SpaceWorldHandler;

// Bukkit Imports
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SkyManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * PlayerListener for things which require Spout.
 * *Disabled*
 * 
 * @author iffa
 * @author HACKhalo2
 */
public class SpaceSpoutPlayerListener extends PlayerListener {
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
     * @param event Event data
     */
    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        if (event.isCancelled() || !player.isSpoutCraftEnabled() || event.getFrom().getWorld().equals(event.getTo().getWorld())
                || (SpaceWorldHandler.isSpaceWorld(event.getFrom().getWorld()) && SpaceWorldHandler.isSpaceWorld(event.getTo().getWorld()))) {
            //Return if the event is canceled, if player doesn't have spoutcraft, if teleporting interworld, or it teleporting between space worlds
            return;
        }
        /* Player teleports to spaceworld */
        if (SpaceWorldHandler.isSpaceWorld(event.getTo().getWorld())) {
            SpaceSpoutHandler.setOrReset(plugin, player, event.getTo());
        }
        /* Player teleports out of spaceworld */
        if (SpaceWorldHandler.isSpaceWorld(event.getFrom().getWorld())) {
            SpaceSpoutHandler.setOrReset(plugin, player, event.getFrom());
        }
    }
    
    /**
     * Called when a player respawns.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        SpaceSpoutHandler.setOrReset(plugin, player, event.getRespawnLocation());
    }
}
