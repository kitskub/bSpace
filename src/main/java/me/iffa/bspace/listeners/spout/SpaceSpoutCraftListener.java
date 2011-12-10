// Package Declaration
package me.iffa.bspace.listeners.spout;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpaceWorldHandler;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;
import org.getspout.spoutapi.player.EntitySkinType;
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
    private final SkyManager sky = SpoutManager.getSkyManager();

    /**
     * Called when a player using SpoutCraft joins.
     * 
     * @param event Event data
     */
    @Override
    public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {
        SpoutPlayer player = event.getPlayer();
        if (SpaceWorldHandler.isSpaceWorld(player.getWorld())) {
            World space = player.getWorld();
            player.setCanFly(true);
            //[18:44] <Afforess> iffa: setEntitySkin was fixed in the 703/510 RB series
            //[18:45] <iffa> Afforess: so it can be uncommented or what?
            //[18:45] <Afforess> yes
            for (LivingEntity entity : space.getLivingEntities()) {
                if (entity instanceof Zombie) {
                    player.setEntitySkin(entity, "http://cloud.github.com/downloads/iffa/bSpace/bananaspace_alien.png", EntitySkinType.DEFAULT);
                }
            }
            SpaceMessageHandler.debugPrint(Level.INFO, "Made zombies have an alien skin for player '" + player.getName() + "'.");
            //Set the sky properties
            sky.setMoonVisible(player, false);
            sky.setCloudsVisible(player, false);
            SpaceMessageHandler.debugPrint(Level.INFO, "Made clouds invisible for player '" + player.getName() + "'.");
            //Set the player properties
            player.setAirSpeedMultiplier(1.2);
            player.setGravityMultiplier(0.3);
            player.setWalkingMultiplier(0.7);
            SpaceMessageHandler.debugPrint(Level.INFO, "Changed player '" + player.getName() + "'s gravity settings (" + player.getAirSpeedMultiplier() + ", " + player.getGravityMultiplier() + ", " + player.getJumpingMultiplier() + ").");
        }
    }
}
