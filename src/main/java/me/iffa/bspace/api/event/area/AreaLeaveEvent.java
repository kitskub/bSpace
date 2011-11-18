// Package Declaration
package me.iffa.bspace.api.event.area;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * Event data for when a player leaves a breathable area.
 * 
 * @author iffa
 */
public class AreaLeaveEvent extends AreaEvent {
    // Variables
    private static final long serialVersionUID = 7604929590186681633L;

    /**
     * Constructor for AreaLeaveEvent.
     * 
     * @param player Player
     */
    public AreaLeaveEvent(Player player) {
        super("AreaLeaveEvent", player);
    }
}
