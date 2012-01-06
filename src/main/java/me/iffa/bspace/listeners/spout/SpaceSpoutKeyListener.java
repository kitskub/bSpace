// Package Declaration
package me.iffa.bspace.listeners.spout;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpaceWorldHandler;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

// Spout Imports
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.event.input.KeyReleasedEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Catches key press events for SpoutCraft enabled clients.
 * 
 * @author HACKhalo2
 * @author iffamies
 */
public class SpaceSpoutKeyListener extends InputListener {
    /**
     * Called when a key is pressed on a SpoutCraft client.
     * 
     * @param event Event data
     */
    @Override
    public void onKeyPressedEvent(KeyPressedEvent event) {
        SpoutPlayer player = event.getPlayer();
        Player temp = player;

        if (event.getScreenType().equals(ScreenType.GAME_SCREEN) && SpaceWorldHandler.isInAnySpace(player)) {
            //Log the jump location for future use
            if (event.getKey().equals(player.getJumpKey())) {
                Space.setJumpPressed(true);
                if (!Space.getLocCache().containsKey(temp)) {
                    Location jumpLocation = event.getPlayer().getLocation(); //Get the starting jump location
                    Space.getLocCache().put(temp, jumpLocation);
                    //bSpace.debugLog("Added player "+temp.getName()+" to the Location Cache");
                } else {
                    Location temp1 = Space.getLocCache().get(temp);
                    Location temp2 = event.getPlayer().getLocation();
                    if ((temp1.getBlock().getX() == temp2.getBlock().getX()) && (temp1.getBlock().getY() == temp2.getBlock().getY())
                            && (temp1.getBlock().getZ() == temp2.getBlock().getZ()) && (temp1.getWorld().equals(temp2.getWorld()))) {
                        //SpaceMessageHandler.debugPrint(Level.WARNING, "Player " + temp.getName() + " is in the Location Cache already! Skipping...");
                    } else {
                        Block under = Bukkit.getServer().getWorld(player.getWorld().getName()).getBlockAt(
                                temp2.getBlockX(), temp2.getBlockY() - 1, temp2.getBlockZ());
                        if (under.getType() != Material.AIR) {
                            //Update the cached reference
                            Space.getLocCache().remove(player);
                            Space.getLocCache().put(temp, temp2);
                        }
                    }
                }
            }
        }
    }

    /**
     * Called when a key is released on a SpoutCraft client.
     * 
     * @param event Event data
     */
    @Override
    public void onKeyReleasedEvent(KeyReleasedEvent event) {
        if (event.getKey().equals(event.getPlayer().getJumpKey())) {
            Space.setJumpPressed(true);
        }
    }
}
