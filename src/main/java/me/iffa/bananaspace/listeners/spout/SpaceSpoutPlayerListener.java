// Package Declaration
package me.iffa.bananaspace.listeners.spout;

// Java Imports
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.runnables.SpaceSpoutRunnable;

// Bukkit Imports
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
	    sky.setCloudsVisible(player, false);
	    
	    sky.setMoonVisible(player, false); //set the moon invisible
	    sky.setCloudsVisible(player, false); //set clouds invisible
	    sky.setStarFrequency(player, 5000); //set star frequency higher
	    BananaSpace.scheduler.scheduleSyncDelayedTask(plugin, new SpaceSpoutRunnable(event.getPlayer()), 10L);
	    SpaceMessageHandler.debugPrint(Level.INFO, "Made clouds and the moon invisible for player '" + player.getName() + "'. Starting runnable thread to setup Player movements...");
	}
	/* Player teleports out of spaceworld */
	if (BananaSpace.worldHandler.isSpaceWorld(event.getFrom().getWorld()) && !BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld())) {
	    sky.setCloudsVisible(player, true);
	    sky.setMoonVisible(player, true);
	    sky.setStarFrequency(player, 500);
	    SpaceMessageHandler.debugPrint(Level.INFO, "Made clouds visible for player '" + player.getName() + "'.");
	    player.setCanFly(false);
	    player.resetMovement();
	    SpaceMessageHandler.debugPrint(Level.INFO, "Reset player '" + player.getName() + "'s gravity and visual settings.");
	}
    }
}
