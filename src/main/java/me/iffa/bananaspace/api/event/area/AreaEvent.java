// Package Declaration
package me.iffa.bananaspace.api.event.area;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * Event data for various area related events.
 * 
 * @author iffa
 * 
 */
public abstract class AreaEvent extends Event {
    // Variables
    private static final long serialVersionUID = -316124458220245924L;
    protected Player player;
    protected boolean cancelled;

    // Constructor
    public AreaEvent(String event, Player player) {
        super(event);
        this.player = player;
    }

    /**
     * Gets the player associated with this event.
     * 
     * @return player associated with this event
     */
    public Player getPlayer() {
        return this.player;
    }
}
