// Package Declaration
package me.iffa.bananaspace.listeners.spout;

// BananaSpace Imports
import me.iffa.bananaspace.api.event.area.AreaEnterEvent;
import me.iffa.bananaspace.api.event.area.AreaLeaveEvent;
import me.iffa.bananaspace.api.event.area.SpaceAreaListener;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Area Listener to change gravity settings between space areas and airlock areas.
 * 
 * @author HACKhalo2
 * @author iffa
 */
public class SpaceSpoutAreaListener extends SpaceAreaListener {
    /**
     * Called when a player enters an area.
     * 
     * @param event Event data
     */
    @Override
    public void onAreaEnter(AreaEnterEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        if (player.isSpoutCraftEnabled()) {
            Location temp = player.getLocation();
            Block under = Bukkit.getServer().getWorld(player.getWorld().getName()).getBlockAt(temp.getBlockX(), temp.getBlockY() - 1, temp.getBlockZ());
            if (under.getType() != Material.AIR) {
                //reset the movement multipliers
                player.setAirSpeedMultiplier(1);
                player.setGravityMultiplier(1);
                player.setWalkingMultiplier(1);
            }
        }
    }

    /**
     * Called when a player leaves an area.
     * 
     * @param event Event data
     */
    @Override
    public void onAreaLeave(AreaLeaveEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        if (player.isSpoutCraftEnabled()) {
            Location temp = player.getLocation();
            Block under = Bukkit.getServer().getWorld(player.getWorld().getName()).getBlockAt(temp.getBlockX(), temp.getBlockY() - 1, temp.getBlockZ());
            if (under.getType() != Material.AIR) {
                //set the movement multipliers for space
                player.setAirSpeedMultiplier(1.2);
                player.setGravityMultiplier(0.3);
                player.setWalkingMultiplier(0.7);
            }
        }
    }
}
