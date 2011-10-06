// Package Declaration
package me.iffa.bananaspace.api.event.misc;

// Bukkit Imports
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * Listener for misc events of BananaSpace.
 * 
 * @author iffa
 * 
 */
public class SpaceListener extends CustomEventListener implements Listener {
    /**
     * Called when a player teleports to space.
     * 
     * @param event Event data
     */
    public void onTeleportToSpace(TeleportToSpaceEvent event) {
    }

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
        if (event instanceof TeleportToSpaceEvent) {
            onTeleportToSpace((TeleportToSpaceEvent) event);
        } else if (event instanceof SpaceCommandEvent) {
            onSpaceCommand((SpaceCommandEvent) event);
        } else if (event instanceof SpaceSuffocationEvent) {
            onSpaceSuffocation((SpaceSuffocationEvent) event);
        }
    }
}
