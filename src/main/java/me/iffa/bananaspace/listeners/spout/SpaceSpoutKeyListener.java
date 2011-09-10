package me.iffa.bananaspace.listeners.spout;

import me.iffa.bananaspace.BananaSpace;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
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
	    if(event.getKey().equals(player.getJumpKey()) && !BananaSpace.locCache.containsKey(temp)) {
		Location jumpLocation = event.getPlayer().getLocation(); //Get the starting jump location
		BananaSpace.locCache.put(temp, jumpLocation);
		BananaSpace.debugLog("Added player "+temp.getName()+" to the Location Cache");
	    } else if(event.getKey().equals(player.getJumpKey()) && BananaSpace.locCache.containsKey(temp)) {
		BananaSpace.debugLog("Player "+temp.getName()+" is in the Location Cache already! Skipping...");
	    }
	}
    }
}
