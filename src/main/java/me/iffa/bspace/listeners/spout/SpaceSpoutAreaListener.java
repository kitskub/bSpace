// Package Declaration
package me.iffa.bspace.listeners.spout;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.event.area.AreaEnterEvent;
import me.iffa.bspace.api.event.area.AreaLeaveEvent;
import me.iffa.bspace.api.event.area.SpaceAreaListener;
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import me.iffa.bspace.handlers.PlayerHandler;
import me.iffa.bspace.handlers.SpoutHandler;
import me.iffa.bspace.handlers.WorldHandler;

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
        SpoutHandler.resetGravity(event.getPlayer());
    }

    /**
     * Called when a player leaves an area.
     * 
     * @param event Event data
     */
    @Override
    public void onAreaLeave(AreaLeaveEvent event) {
        SpoutHandler.setGravity(event.getPlayer());
    }
    
    @Override
    public void onSpaceEnter(SpaceEnterEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        if (event.isCancelled() || !player.isSpoutCraftEnabled() || event.getFrom().getWorld().equals(event.getTo().getWorld())
                || (WorldHandler.isSpaceWorld(event.getFrom().getWorld()) && WorldHandler.isSpaceWorld(event.getTo().getWorld()))) {
            //Return if the event is canceled, if player doesn't have spoutcraft, if teleporting interworld, or it teleporting between space worlds
            return;
        }
        SpoutHandler.setOrReset(plugin, player, event.getTo());
        if(!PlayerHandler.insideArea(event.getTo())){
            SpoutHandler.setGravity(player);
        }
        
        
    }
    
    @Override
    public void onSpaceLeave(SpaceLeaveEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        if (event.isCancelled() || !player.isSpoutCraftEnabled() || event.getFrom().getWorld().equals(event.getTo().getWorld())
                || (WorldHandler.isSpaceWorld(event.getFrom().getWorld()) && WorldHandler.isSpaceWorld(event.getTo().getWorld()))) {
            //Return if the event is canceled, if player doesn't have spoutcraft, if teleporting interworld, or it teleporting between space worlds
            return;
        }
        SpoutHandler.setOrReset(plugin, player, event.getFrom());
        SpoutHandler.resetGravity(player);
    }
}
