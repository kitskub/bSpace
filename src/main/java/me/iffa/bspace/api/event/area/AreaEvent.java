// Package Declaration
package me.iffa.bspace.api.event.area;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event data for various area related events.
 * 
 * @author iffa
 */
public abstract class AreaEvent extends Event {
    // Variables
    private static final HandlerList handlers = new HandlerList();
    private static final long serialVersionUID = -316124458220245924L;
    private Player player;

    /**
     * Constructor for AreaEvent.
     * 
     * @param event Event
     * @param player Player
     */
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

    /**
     * {@inheritDoc}
     * @return Handler list
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
