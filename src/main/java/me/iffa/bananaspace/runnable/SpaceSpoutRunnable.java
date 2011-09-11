package me.iffa.bananaspace.runnable;

import me.iffa.bananaspace.BananaSpace;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * A runnable class as a workaround for Spout player modifiers
 * 
 * @author HACKhalo2
 */
public class SpaceSpoutRunnable implements Runnable {
    private final Player player;

    public SpaceSpoutRunnable(Player player) {
	this.player = player;
    }

    public void run() {
	if(BananaSpace.worldHandler.isInAnySpace(player)) { //idiot proofing
	    SpoutPlayer p = SpoutManager.getPlayer(player); //refresh the SpoutPlayer reference (workaround to a bug in the Spout Client)
	    //Set the player properties
	    p.setAirSpeedMultiplier(1.2);
	    p.setGravityMultiplier(0.3);
	    p.setWalkingMultiplier(0.7);
	    //Debug stuffs
	    BananaSpace.debugLog("Changed player '" + p.getName() + "'s gravity settings ("+p.getAirSpeedMultiplier()+
		    ", "+p.getGravityMultiplier()+", "+p.getJumpingMultiplier()+").");
	}

    }

}
