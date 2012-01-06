// Package Declaration
package me.iffa.bspace.listeners.spout;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceSpoutHandler;
import me.iffa.bspace.api.SpaceWorldHandler;
import me.iffa.bspace.api.SpacePlayerHandler;
import me.iffa.bspace.api.event.area.AreaEnterEvent;
import me.iffa.bspace.api.event.area.AreaLeaveEvent;
import me.iffa.bspace.api.event.area.SpaceAreaListener;
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;

// Bukkit Imports
import org.bukkit.Bukkit;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Area Listener to change gravity settings between space areas and airlock areas.
 * 
 * @author HACKhalo2
 * @author iffa
 * @author kitskub
 */
public class SpaceSpoutAreaListener extends SpaceAreaListener {
    private final Space plugin = (Space) Bukkit.getPluginManager().getPlugin("bSpace");
    /**
     * Called when a player enters an area.
     * 
     * @param event Event data
     */
    @Override
    public void onAreaEnter(AreaEnterEvent event) {
        SpaceSpoutHandler.resetGravity(event.getPlayer());
    }

    /**
     * Called when a player leaves an area.
     * 
     * @param event Event data
     */
    @Override
    public void onAreaLeave(AreaLeaveEvent event) {
        SpaceSpoutHandler.setGravity(event.getPlayer());
    }
    
    @Override
    public void onSpaceEnter(SpaceEnterEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        if (event.isCancelled() || !player.isSpoutCraftEnabled() || event.getFrom().getWorld().equals(event.getTo().getWorld())
                || (SpaceWorldHandler.isSpaceWorld(event.getFrom().getWorld()) && SpaceWorldHandler.isSpaceWorld(event.getTo().getWorld()))) {
            //Return if the event is canceled, if player doesn't have spoutcraft, if teleporting interworld, or it teleporting between space worlds
            return;
        }
        SpaceSpoutHandler.setOrReset(plugin, player, event.getTo());
        if(!SpacePlayerHandler.insideArea(event.getTo())){
            SpaceSpoutHandler.setGravity(player);
        }
        
        
    }
    
    @Override
    public void onSpaceLeave(SpaceLeaveEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        if (event.isCancelled() || !player.isSpoutCraftEnabled() || event.getFrom().getWorld().equals(event.getTo().getWorld())
                || (SpaceWorldHandler.isSpaceWorld(event.getFrom().getWorld()) && SpaceWorldHandler.isSpaceWorld(event.getTo().getWorld()))) {
            //Return if the event is canceled, if player doesn't have spoutcraft, if teleporting interworld, or it teleporting between space worlds
            return;
        }
        SpaceSpoutHandler.setOrReset(plugin, player, event.getFrom());
        SpaceSpoutHandler.resetGravity(player);
    }
}
