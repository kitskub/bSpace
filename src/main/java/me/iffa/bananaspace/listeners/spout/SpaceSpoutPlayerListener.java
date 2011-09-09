// Package Declaration
package me.iffa.bananaspace.listeners.spout;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.runnable.SpaceRunnable3;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.AppearanceManager;
import org.getspout.spoutapi.player.EntitySkinType;
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
    private final AppearanceManager app = SpoutManager.getAppearanceManager();
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
	if (event.isCancelled() || !player.isSpoutCraftEnabled()) {
	    return;
	}
	/* Player teleports to spaceworld */
	if (BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld())) {
	    sky.setCloudsVisible(player, false);
	    BananaSpace.debugLog("Made clouds invisible for player '" + player.getName() + "'.");
	    for (LivingEntity entity : player.getWorld().getLivingEntities()) {
		if (entity instanceof Zombie) {
		    app.setEntitySkin(player, entity, "http://dl.dropbox.com/u/16261496/bananaspace_alien.png", EntitySkinType.DEFAULT);
		    BananaSpace.debugLog("Made zombie '" + entity.getEntityId() + "' have an alien skin for player '" + player.getName() + "'.");
		}
	    }
	    
	    sky.setMoonVisible(player, false);
	    sky.setCloudsVisible(player, false);
	    sky.setStarFrequency(player, 20000);
	    
	    int task = BananaSpace.scheduler.scheduleSyncDelayedTask(plugin, new SpaceRunnable3(event.getPlayer()), 20L);
	}
	/* Player teleports out of spaceworld */
	if (BananaSpace.worldHandler.isSpaceWorld(event.getFrom().getWorld()) && !BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld())) {
	    sky.setCloudsVisible(player, true);
	    BananaSpace.debugLog("Made clouds visible for player '" + player.getName() + "'.");
	    player.setCanFly(false);
	    player.resetMovement();
	    BananaSpace.debugLog("Reset player '" + player.getName() + "'s gravity settings.");
	}
    }
}
