// Package Declaration
package me.iffa.bspace.api.event.area;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Event data for when a player is teleported to space.
 * 
 * @author iffa
 */
public class SpaceLeaveEvent extends SpaceWorldAreaEvent {
    // Variables
    private static final HandlerList handlers = new HandlerList();
    private static final long serialVersionUID = 8744071538699676557L;
    private Location to = null;
    private Location from = null;

    /**
     * Constructor for
     * 
     * @param player Player
     * @param from Where the player teleports from
     * @param to Where the player teleports to
     */
    public SpaceLeaveEvent(Player player, Location from, Location to) {
        super("SpaceLeaveEvent", player);
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the destination of the teleport.
     * 
     * @return Destination of the teleport
     */
    public Location getTo() {
        return this.to;
    }

    /**
     * Gets where the player is trying to teleport from.
     * 
     * @return Where the player is trying to teleport from
     */
    public Location getFrom() {
        return this.from;
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
