// Package Declaration
package me.iffa.bspace.listeners;

// bSpace Imports
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import me.iffa.bspace.economy.Economy;
import me.iffa.bspace.handlers.MessageHandler;

// Bukkit Imports
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Listener for economy stuff.
 * 
 * @author Jack
 * @author iffa
 */
public class SpaceEconomyListener implements Listener {

    /**
     * Called when someone enters space.
     * 
     * @param event Event data
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpaceEnter(SpaceEnterEvent event) {
        if (event.getFrom() == event.getTo()) {
            return;
        }
        if (!Economy.enter(event.getPlayer())) {
            MessageHandler.sendNotEnoughMoneyMessage(event.getPlayer());
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Called when someone leaves space.
     * 
     * @param event Event data
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSpaceLeave(SpaceLeaveEvent event) {
        if (!Economy.exit(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
    }
}
