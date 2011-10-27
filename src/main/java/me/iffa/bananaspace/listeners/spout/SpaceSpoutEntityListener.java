// Package Declaration
package me.iffa.bananaspace.listeners.spout;

// Java Imports
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceMessageHandler;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.AppearanceManager;
import org.getspout.spoutapi.player.EntitySkinType;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * EntityListener for Spout features.
 * 
 * @author HACKhalo2
 * @author iffa
 */
public class SpaceSpoutEntityListener extends EntityListener {
    // Variables
    private final AppearanceManager app = SpoutManager.getAppearanceManager();

    /**
     * Called when an entity takes damage.
     * 
     * @param event Event data
     */
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
	Entity entity = event.getEntity();
	if(entity instanceof Player) {
	    SpoutPlayer player = SpoutManager.getPlayer((Player)entity);
	    if(BananaSpace.getWorldHandler().isInAnySpace(player) && event.getCause().equals(DamageCause.FALL) && player.isSpoutCraftEnabled()) {
		if(BananaSpace.locCache.containsKey(player)) {
		    Location landing = player.getLocation(); //The landing point
		    Location starting = BananaSpace.locCache.get(player); //The starting point
		    if(landing.getWorld().equals(starting.getWorld())) { //moar idiot proofing
			int startY = starting.getBlockY();
			int endY = landing.getBlockY();
			int offset = 15; //The offset (needs configuration node)

			//Simple Math
			if((startY+offset) <= endY) {
			    //I'm going to be adding more advanced math soon, this is to get it working
			    event.setDamage(1); //half heart of damage
			} else {
			    event.setDamage(0); //no damage!
			}
			BananaSpace.locCache.remove(player); //Remove the player reference
			//BananaSpace.debugLog("Removing "+player.getName()+" from the cache.");
			if(BananaSpace.jumpPressed) {
			    Location jumpLocation = player.getLocation();
			    BananaSpace.locCache.put(player, jumpLocation); //readd the player is the jump key is pressed
			    //BananaSpace.debugLog("Added player "+player.getName()+" to the Location Cache");
			}
		    } else {
			SpaceMessageHandler.debugPrint(Level.WARNING, "Worlds are not the same! Canceling event!");
			event.setCancelled(true);
		    }
		} else {
		    SpaceMessageHandler.debugPrint(Level.WARNING, "Player "+player.getName()+" wasn't in the Location Cache! Canceling event!");
		    event.setCancelled(true);
		}
	    }
	}
    }

    /**
     * Called when an entity spawns. This should correctly set all zombies skin to an alien skin when they spawn.
     * **This code is currently disabled**
     * 
     * @param event Event data
     */
    @Override
    public void onCreatureSpawn(CreatureSpawnEvent event) {
	if (!event.isCancelled() && BananaSpace.getWorldHandler().isSpaceWorld(event.getLocation().getWorld()) && event.getCreatureType().equals(CreatureType.ZOMBIE)) {
	    for(Player player : event.getLocation().getWorld().getPlayers()) {
		SpoutPlayer p = SpoutManager.getPlayer(player);
		if(p.isSpoutCraftEnabled()) {
		    app.setEntitySkin(p, (LivingEntity)event.getEntity(), "http://dl.dropbox.com/u/16261496/bananaspace_alien.png", EntitySkinType.DEFAULT);
		    SpaceMessageHandler.debugPrint(Level.INFO, "Made zombie '" + event.getEntity().getEntityId() + "' have an alien skin for player '" + player.getName() + "'.");
		}
	    }
	}

    }
}
