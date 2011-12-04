// Package Declaration
package me.iffa.bspace.api.event.area;

// Bukkit Imports
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

/**
 * Listener for area events of bSpace.
 * 
 * @author iffa
 */
public class SpaceAreaListener extends CustomEventListener {
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
    
    /**
     * 
     * @param spaceEnterEvent Event data
     */
    public void onSpaceEnter(SpaceEnterEvent spaceEnterEvent) {
    }

    /**
     * 
     * @param spaceLeaveEvent Event data
     */
    public void onSpaceLeave(SpaceLeaveEvent spaceLeaveEvent) {
    }

    /**
     * Handles the events that are called.
     * 
     * @param event Event
     */
    @Override
    public void onCustomEvent(Event event) {
        if (event instanceof AreaEnterEvent) {
            onAreaEnter((AreaEnterEvent) event);
        } else if (event instanceof AreaLeaveEvent) {
            onAreaLeave((AreaLeaveEvent) event);
        } else if (event instanceof SpaceEnterEvent) {
            onSpaceEnter((SpaceEnterEvent) event);
        } else if (event instanceof SpaceLeaveEvent) {
            onSpaceLeave((SpaceLeaveEvent) event);
        }
    }

}
