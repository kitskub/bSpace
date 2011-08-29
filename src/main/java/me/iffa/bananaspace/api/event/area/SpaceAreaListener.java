// Package Declaration
package me.iffa.bananaspace.api.event.area;

// Bukkit Imports
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Listener;

/**
 * Listener for area events of BananaSpace.
 * 
 * @author iffa
 */
public class SpaceAreaListener extends CustomEventListener implements Listener {
    /**
     * Called when a player enters a breathable area.
     * 
     * @param event Event data
     */
    public void onAreaEnter(AreaEnterEvent event) {
    }

    /**
     * Called when a player leaves a breathable area.
     * 
     * @param event Event data
     */
    public void onAreaLeave(AreaLeaveEvent event) {
    }
}
