// Package Declaration
package me.iffa.bspace.api.event.area;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Event data for when a player enters a breathable area.
 * 
 * @author iffa
 */
public class AreaEnterEvent extends AreaEvent {
    // Variables
    private static final HandlerList handlers = new HandlerList();
    private static final long serialVersionUID = 8533622463870713905L;

    /**
     * Constructor for AreaEnterEvent.
     * 
     * @param player Player
     */
    public AreaEnterEvent(Player player) {
        super("AreaEnterEvent", player);
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
