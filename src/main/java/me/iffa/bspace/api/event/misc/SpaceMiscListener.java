// Package Declaration
package me.iffa.bspace.api.event.misc;

// Bukkit Imports
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

/**
 * Listener for misc. events of bSpace.
 * 
 * @author iffa
 */
public class SpaceMiscListener extends CustomEventListener {
    /**
     * Called when a player starts suffocating (for having no helmet, suit or
     * both).
     * 
     * @param event Event data
     */
    public void onSpaceSuffocation(SpaceSuffocationEvent event) {
    }

    /**
     * Called when a player uses the space command.
     * 
     * @param event Event data
     */
    public void onSpaceCommand(SpaceCommandEvent event) {
    }

    /**
     * Handles the events that are called.
     * 
     * @param event Event
     */
    @Override
    public void onCustomEvent(Event event) {
        if (event instanceof SpaceCommandEvent) {
            onSpaceCommand((SpaceCommandEvent) event);
        } else if (event instanceof SpaceSuffocationEvent) {
            onSpaceSuffocation((SpaceSuffocationEvent) event);
        }
    }
}
