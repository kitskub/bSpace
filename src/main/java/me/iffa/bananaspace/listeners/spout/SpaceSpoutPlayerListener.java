// Package Declaration
package me.iffa.bananaspace.listeners.spout;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;

// Bukkit Imports
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerTeleportEvent;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SkyManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * PlayerListener for things which require Spout.
 * 
 * @author iffa
 */
public class SpaceSpoutPlayerListener extends PlayerListener {
    // Variables
    private BananaSpace plugin;

    /**
     * Constructor for SpaceSpoutPlayerListener.
     * 
     * @param plugin BananaSpace
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
        if (event.isCancelled()) {
            return;
        }
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        SkyManager sky = SpoutManager.getSkyManager();
        if (BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld()) && !BananaSpace.worldHandler.isSpaceWorld(event.getFrom().getWorld())) {
            sky.setCloudsVisible(player, false);
            BananaSpace.debugLog("Made clouds invisible for player '" + player.getName() + "'.");
        }
        if (BananaSpace.worldHandler.isSpaceWorld(event.getFrom().getWorld()) && !BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld())) {
            sky.setCloudsVisible(player, true);
            BananaSpace.debugLog("Made clouds visible for player '" + player.getName() + "'.");
        }
    }
    
    /**
     * Called when a player joins the game.
     * 
     * @param event Event data 
     */
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        SkyManager sky = SpoutManager.getSkyManager();
        if (BananaSpace.worldHandler.isSpaceWorld(player.getWorld())) {
            sky.setCloudsVisible(player, false);
            BananaSpace.debugLog("Made clouds invisible for player '" + player.getName() + "'.");
        }
    }
}
