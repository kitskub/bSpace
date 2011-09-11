package me.iffa.bananaspace.listeners.spout;

import me.iffa.bananaspace.BananaSpace;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.event.input.KeyReleasedEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Catches Key press events for SpoutCraft enabled Clients
 * @author HACKhalo2
 */
public class SpaceSpoutKeyListener extends InputListener {
    private final BananaSpace plugin;

    public SpaceSpoutKeyListener(BananaSpace plugin) {
	this.plugin = plugin;
    }

    /**
     * Called when a key is pressed on the SpoutCraft Client
     * 
     * @param event Event data
     */
    @Override
    public void onKeyPressedEvent(KeyPressedEvent event) {
	SpoutPlayer player = event.getPlayer();
	Player temp = player;

	if(event.getScreenType().equals(ScreenType.GAME_SCREEN) && BananaSpace.getWorldHandler().isInAnySpace(player)) {
	    //Log the jump location for future use
	    if(event.getKey().equals(player.getJumpKey())) {
		BananaSpace.jumpPressed = true; 
		if(!BananaSpace.locCache.containsKey(temp)) {
		    Location jumpLocation = event.getPlayer().getLocation(); //Get the starting jump location
		    BananaSpace.locCache.put(temp, jumpLocation);
		    //BananaSpace.debugLog("Added player "+temp.getName()+" to the Location Cache");
		} else {
		    Location temp1 = BananaSpace.locCache.get(temp);
		    Location temp2 = event.getPlayer().getLocation();
		    if((temp1.getBlock().getX() == temp2.getBlock().getX()) && (temp1.getBlock().getY() == temp2.getBlock().getY()) &&
			    (temp1.getBlock().getZ() == temp2.getBlock().getZ()) && (temp1.getWorld().equals(temp2.getWorld()))) {
			BananaSpace.debugLog("Player "+temp.getName()+" is in the Location Cache already! Skipping...");
		    } else {
			Block under = Bukkit.getServer().getWorld(player.getWorld().getName()).getBlockAt(
				temp2.getBlockX(), temp2.getBlockY()-1, temp2.getBlockZ());
			if(under.getType() != Material.AIR) {
			    //Update the cached reference
			    BananaSpace.locCache.remove(player);
			    BananaSpace.locCache.put(temp, temp2);
			    BananaSpace.debugLog("Updated Player "+temp.getName()+" in the Location Cache.");
			}
		    }
		}
	    }
	}
    }

    /**
     * Called when a key is released on the SpoutCraft Client
     * 
     * @param event Event data
     */
    @Override
    public void onKeyReleasedEvent(KeyReleasedEvent event) {
	if(event.getKey().equals(event.getPlayer().getJumpKey())) {
	    BananaSpace.jumpPressed = false;
	}
    }
}
