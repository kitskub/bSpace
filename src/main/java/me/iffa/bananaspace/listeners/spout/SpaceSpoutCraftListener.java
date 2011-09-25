// Package Declaration
package me.iffa.bananaspace.listeners.spout;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;

// Bukkit Imports
import org.bukkit.World;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;
import org.getspout.spoutapi.player.AppearanceManager;
import org.getspout.spoutapi.player.SkyManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * The SpoutCraft listener for things that need to run as soon as the server enables Spout for the client.
 * 
 * @author HACKhalo2
 * @author iffa
 */
public class SpaceSpoutCraftListener extends SpoutListener {
    // Variables
    private final BananaSpace plugin;
    private final SkyManager sky = SpoutManager.getSkyManager();
    private final AppearanceManager app = SpoutManager.getAppearanceManager();

    /**
     * Constructor of SpaceSpoutCraftListener.
     * 
     * @param plugin BananaSpace instance
     */
    public SpaceSpoutCraftListener(BananaSpace plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when a player using SpoutCraft joins.
     * 
     * @param event Event data
     */
    @Override
    public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {
        SpoutPlayer player = event.getPlayer();
        if (BananaSpace.worldHandler.isSpaceWorld(player.getWorld())) {
            World space = player.getWorld();
            player.setCanFly(true);
            //Disabled due to limitations in Spout.
	    /*for (LivingEntity entity : space.getLivingEntities()) {
            if (entity instanceof Zombie) {
            app.setEntitySkin(player, entity, "http://dl.dropbox.com/u/16261496/bananaspace_alien.png", EntitySkinType.DEFAULT);
            BananaSpace.debugLog("Made zombie '" + entity.getEntityId() + "' have an alien skin for player '" + player.getName() + "'.");
            }
            }*/
            //Set the sky properties
            sky.setMoonVisible(player, false);
            sky.setCloudsVisible(player, false);
            sky.setStarFrequency(player, 5000);
            //Set the player properties
            player.setAirSpeedMultiplier(1.2);
            player.setGravityMultiplier(0.3);
            player.setWalkingMultiplier(0.7);
            //Debug stuffs
            BananaSpace.debugLog("Made clouds invisible for player '" + player.getName() + "'.");
            BananaSpace.debugLog("Made zombies have an alien skin for player '" + player.getName() + "'.");
            BananaSpace.debugLog("Changed player '" + player.getName() + "'s gravity settings (" + player.getAirSpeedMultiplier() + ", " + player.getGravityMultiplier() + ", " + player.getJumpingMultiplier() + ").");
        }
    }
}
