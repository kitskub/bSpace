// Package Declaration
package me.iffa.bspace.api.event.misc;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for when a player is teleported to space.
 * 
 * @author iffa
 */
public class TeleportToSpaceEvent extends Event implements Cancellable {
    // Variables
    private static final long serialVersionUID = 8744071438699676557L;
    private boolean canceled = false;
    private Player player = null;
    private Location to = null;
    private Location from = null;

    /**
     * Constructor for
     * 
     * @param event Event
     * @param player Player
     * @param from Where the player teleports from
     * @param to Where the player teleports to
     */
    public TeleportToSpaceEvent(String event, Player player, Location from, Location to) {
        super(event);
        this.player = player;
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the player that was teleported to space.
     * 
     * @return Player that was teleported to space
     */
    public Player getPlayer() {
        return this.player;
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

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }
}
