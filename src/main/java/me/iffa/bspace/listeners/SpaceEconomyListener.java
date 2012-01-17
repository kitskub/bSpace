// Package Declaration
package me.iffa.bspace.listeners;

// bSpace Imports
import me.iffa.bspace.api.event.area.SpaceAreaListener;
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import me.iffa.bspace.economy.Economy;
import me.iffa.bspace.handlers.MessageHandler;

/**
 * Listener for economy stuff.
 * 
 * @author Jack
 * @author iffa
 */
public class SpaceEconomyListener extends SpaceAreaListener{
    /**
     * Called when someone enters space.
     * 
     * @param event Event data
     */
    @Override
    public void onSpaceEnter(SpaceEnterEvent event){
        if(event.getFrom()==event.getTo()){
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
    @Override
    public void onSpaceLeave(SpaceLeaveEvent event){
        if (!Economy.exit(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
    }
}
