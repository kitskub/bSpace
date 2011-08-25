// Package Declaration
package me.iffa.bananaspace.api;

// Bukkit Imports
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

// BananaSpace Imports
import me.iffa.bananaspace.api.event.area.AreaEnterEvent;
import me.iffa.bananaspace.api.event.area.AreaLeaveEvent;
import me.iffa.bananaspace.api.event.misc.AntiMobSpawnEvent;
import me.iffa.bananaspace.api.event.misc.SpaceCommandEvent;
import me.iffa.bananaspace.api.event.misc.SpaceSuffocationEvent;
import me.iffa.bananaspace.api.event.misc.TeleportToSpaceEvent;

/**
 * Listener for events of BananaSpace.
 * 
 * @author iffa
 * 
 */
public class SpaceListener extends CustomEventListener implements Listener {
    /**
     * Called when a player enters a breathable area.
     * 
     * @param event
     *            Event data
     */
    public void onAreaEnter(AreaEnterEvent event) {
    }

    /**
     * Called when a player leaves a breathable area.
     * 
     * @param event
     *            Event data
     */
    public void onAreaLeave(AreaLeaveEvent event) {
    }

    /**
     * Called when a player teleports to space.
     * 
     * @param event
     *            Event data
     */
    public void onTeleportToSpace(TeleportToSpaceEvent event) {
    }

    /**
     * Called when a player starts suffocating (for having no helmet, suit or
     * both).
     * 
     * @param event
     *            Event data
     */
    public void onSpaceSuffocation(SpaceSuffocationEvent event) {
    }

    /**
     * Called when a player uses the space command.
     * 
     * @param event
     *            Event data
     */
    public void onSpaceCommand(SpaceCommandEvent event) {
    }

    /**
     * Called when an entity is not allowed to spawn in space.
     * 
     * @param event
     *            Event data
     */
    public void onAntiMobSpawn(AntiMobSpawnEvent event) {
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
        } else if (event instanceof TeleportToSpaceEvent) {
            onTeleportToSpace((TeleportToSpaceEvent) event);
        } else if (event instanceof SpaceCommandEvent) {
            onSpaceCommand((SpaceCommandEvent) event);
        } else if (event instanceof AntiMobSpawnEvent) {
            onAntiMobSpawn((AntiMobSpawnEvent) event);
        } else if (event instanceof SpaceSuffocationEvent) {
            onSpaceSuffocation((SpaceSuffocationEvent) event);
        }
    }
}
