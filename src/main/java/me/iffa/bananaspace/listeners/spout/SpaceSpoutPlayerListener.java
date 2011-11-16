// Package Declaration
package me.iffa.bananaspace.listeners.spout;

// Java Imports
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceConfigHandler;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.api.SpaceSpoutHandler;
import me.iffa.bananaspace.runnables.SpoutFixRunnable;

// Bukkit Imports
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
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
public class SpaceSpoutPlayerListener extends PlayerListener {
    // Variables
    private final SkyManager sky = SpoutManager.getSkyManager();
    private final BananaSpace plugin;

    /**
     * Constructor for SpaceSpoutPlayerListener.
     * 
     * @param plugin BananaSpace instance
     */
    public SpaceSpoutPlayerListener(BananaSpace plugin) {
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
                || (BananaSpace.getWorldHandler().isSpaceWorld(event.getFrom().getWorld()) && BananaSpace.getWorldHandler().isSpaceWorld(event.getTo().getWorld()))) {
            //Return if the event is canceled, if player doesn't have spoutcraft, if teleporting interworld, or it teleporting between space worlds
            return;
        }
        /* Player teleports to spaceworld */
        if (BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld())) {
            SpaceSpoutHandler.setOrReset(plugin, player, event.getTo());
        }
        /* Player teleports out of spaceworld */
        if (BananaSpace.worldHandler.isSpaceWorld(event.getFrom().getWorld())) {
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
    
    /**
     * Called when a player moves.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        
    }
}
