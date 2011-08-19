// Package Declaration
package me.iffa.bananaspace.api.event.misc;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for when a player suffocates.
 * 
 * @author iffa
 */
public class SpaceSuffocationEvent extends Event implements
        Cancellable {
    // Variables
    private static final long serialVersionUID = 8772846319048911013L;
    protected boolean canceled = false;
    protected Player player = null;

    // Constructor
    public SpaceSuffocationEvent(String event, Player player) {
        super(event);
        this.player = player;
    }

    /**
     * Gets the player that is suffocating.
     * 
     * @return Player that is suffocating
     */
    public Player getPlayer() {
        return this.player;
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
