/*
 */
package me.iffa.bspace.api.event.area;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 *
 * @author Jack
 */
public class SpaceWorldAreaEvent extends AreaEvent implements Cancellable{
    private static final long serialVersionUID = -316124428220245924L;
    private boolean cancelled;
    
    /**
     * Constructor for AreaEvent.
     * 
     * @param event Event
     * @param player Player
     */
    public SpaceWorldAreaEvent(String event, Player player) {
        super(event,player);
    }
    
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
    
}
