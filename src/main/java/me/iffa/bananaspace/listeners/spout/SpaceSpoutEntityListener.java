package me.iffa.bananaspace.listeners.spout;

import me.iffa.bananaspace.BananaSpace;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SpaceSpoutEntityListener extends EntityListener {
    private final BananaSpace plugin;
    
    public SpaceSpoutEntityListener(BananaSpace plugin) {
	this.plugin = plugin;
    }
    
    /**
     * Called when an entity takes damage
     * 
     * @param event Event data
     */
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
	Entity entity = event.getEntity();
	if(entity instanceof Player) {
	    SpoutPlayer player = SpoutManager.getPlayer((Player)entity);
	    if(BananaSpace.getWorldHandler().isInAnySpace(player) && event.getCause().equals(DamageCause.FALL) && player.isSpoutCraftEnabled()) {
		event.setDamage(0); //Fuck math
	    }
	}
    }
}
