// Package Declaration
package me.iffa.bspace.api;

// Java Imports
import java.util.List;
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.runnables.SpoutFixRunnable;

// Bukkit Imports
import me.iffa.bspace.wgen.blocks.BlackHole;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

// Spout Imports
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.CustomBlock;
import org.getspout.spoutapi.player.SkyManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Useful methods for Spout&Spoutcraft-only features.
 * 
 * @author iffamies
 * @author Adamki11s
 */
public class SpaceSpoutHandler {
    public static CustomBlock blackHole = new BlackHole();
    /**
     * Sets or resets Spoutcraft-only features.
     * 
     * @param plugin bSpace instance
     * @param player SpoutPlayer
     * @param location Location
     */
    public static void setOrReset(Space plugin, SpoutPlayer player, Location location) {
        SkyManager sky = SpoutManager.getSkyManager();
        if (SpaceWorldHandler.isSpaceWorld(location.getWorld())) {
            if (!SpaceConfigHandler.getCloudsEnabled()) {
                sky.setCloudsVisible(player, false);
                sky.setMoonVisible(player, false);
                sky.setCloudsVisible(player, false);
                sky.setStarFrequency(player, 4200);
            }
            if (SpaceConfigHandler.getUseTexturePack()) {
                player.setTexturePack(SpaceConfigHandler.getSpoutTexturePack());
                SpaceMessageHandler.debugPrint(Level.INFO, "Set " + player.getName() + "'s texture pack");
            }
            if (SpaceConfigHandler.getGravity()) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new SpoutFixRunnable(player), 10L);
                SpaceMessageHandler.debugPrint(Level.INFO, "Made clouds and the moon invisible for player '" + player.getName() + "'. Starting runnable thread to setup Player movements...");
            }
        } else {
            if (!SpaceConfigHandler.getCloudsEnabled()) {
                sky.setCloudsVisible(player, true);
                sky.setMoonVisible(player, true);
                sky.setStarFrequency(player, 500);
                SpaceMessageHandler.debugPrint(Level.INFO, "Made clouds visible again for player '" + player.getName() + "'.");
            }
            if (SpaceConfigHandler.getUseTexturePack()) {
                player.resetTexturePack();
                SpaceMessageHandler.debugPrint(Level.INFO, "Reset " + player.getName() + "'s texture pack");
            }
            if (SpaceConfigHandler.getGravity()) {
                player.setCanFly(false);
                player.resetMovement();
                SpaceMessageHandler.debugPrint(Level.INFO, "Reset player '" + player.getName() + "'s gravity and visual settings.");
            }
        }
    }

    /**
     * Checks if an entity is inside a radius.(Actually within a cuboid with sides radius times 2 blocks long)
     * 
     * @param entity Entity
     * @param centre Center of radius
     * @param radius Radius
     * 
     * @return True if inside radius
     */
    public static boolean isInsideRadius(Entity entity, Location centre, int radius) {
        Location negMost = new Location(centre.getWorld(), centre.getX() - radius, centre.getY() - radius, centre.getZ() - radius);
        Location posMost     = new Location(centre.getWorld(), centre.getX() + radius, centre.getY() + radius, centre.getZ() + radius);
        double x1 = negMost.getX(), x2 = posMost.getX(),
                y1 = negMost.getY(), y2 = posMost.getY(),
                z1 = negMost.getZ(), z2 = posMost.getZ(),
                px = entity.getLocation().getX(),
                py = entity.getLocation().getY(),
                pz = entity.getLocation().getZ();
        if ((((py <= y1)
                && (py >= y2))
                || ((py >= y1)
                && (py <= y2)))
                && (((pz <= z1)
                && (pz >= z2))
                || ((pz >= z1)
                && (pz <= z2)))
                && (((px <= x1)
                && (px >= x2))
                || ((px >= x1)
                && (px <= x2)))
                && (((px <= x1)
                && (px >= x2))
                || ((px >= x1)
                && (px <= x2)))) {
            return true;
        }
        return false;
    }
    
    public static void resetGravity(Player player){
        SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player);
        if (spoutPlayer.isSpoutCraftEnabled()) {
            Location temp = spoutPlayer.getLocation();
            Block under = Bukkit.getServer().getWorld(spoutPlayer.getWorld().getName()).getBlockAt(temp.getBlockX(), temp.getBlockY() - 1, temp.getBlockZ());
            if (under.getType() != Material.AIR) {
                //set the movement multipliers for space
                spoutPlayer.setAirSpeedMultiplier(1);
                spoutPlayer.setGravityMultiplier(1);
                spoutPlayer.setWalkingMultiplier(1);
            }
        }
    }
    
    public static void setGravity(Player player){
        SpoutPlayer spoutPlayer = SpoutManager.getPlayer(player);
        if (spoutPlayer.isSpoutCraftEnabled()) {
            Location temp = spoutPlayer.getLocation();
            Block under = Bukkit.getServer().getWorld(spoutPlayer.getWorld().getName()).getBlockAt(temp.getBlockX(), temp.getBlockY() - 1, temp.getBlockZ());
            if (under.getType() != Material.AIR) {
                //set the movement multipliers for space
                spoutPlayer.setAirSpeedMultiplier(1.2);
                spoutPlayer.setGravityMultiplier(0.3);
                spoutPlayer.setWalkingMultiplier(0.7);
            }
        }
    }

    /**
     * Constructor of SpaceSpoutHandler.
     */
    private SpaceSpoutHandler() {
    }
}
