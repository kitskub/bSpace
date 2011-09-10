// Package Declaration
package me.iffa.bananaspace.listeners.spout;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.runnable.SpaceSpoutRunnable;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerTeleportEvent;
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
    private final SkyManager sky = SpoutManager.getSkyManager();
    private final BananaSpace plugin;

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
	SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
	if (event.isCancelled() || !player.isSpoutCraftEnabled() || event.getFrom().getWorld().equals(event.getTo().getWorld())) {
	    return;
	}
	/* Player teleports to spaceworld */
	if (BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld())) {
	    sky.setCloudsVisible(player, false);
	    BananaSpace.debugLog("Made clouds invisible for player '" + player.getName() + "'.");
	    
	    sky.setMoonVisible(player, false);
	    sky.setCloudsVisible(player, false);
	    sky.setStarFrequency(player, 5000);
	    BananaSpace.scheduler.scheduleSyncDelayedTask(plugin, new SpaceSpoutRunnable(event.getPlayer()), 20L);
	}
	/* Player teleports out of spaceworld */
	if (BananaSpace.worldHandler.isSpaceWorld(event.getFrom().getWorld()) && !BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld())) {
	    sky.setCloudsVisible(player, true);
	    sky.setMoonVisible(player, true);
	    sky.setStarFrequency(player, 500);
	    BananaSpace.debugLog("Made clouds visible for player '" + player.getName() + "'.");
	    player.setCanFly(false);
	    player.resetMovement();
	    BananaSpace.debugLog("Reset player '" + player.getName() + "'s gravity settings.");
	}
    }
}
