/*
 */
package me.iffa.bspace.listeners;

import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.event.area.SpaceAreaListener;
import me.iffa.bspace.api.event.area.SpaceEnterEvent;
import me.iffa.bspace.api.event.area.SpaceLeaveEvent;
import me.iffa.bspace.economy.Economy;

/**
 *
 * @author Jack
 */
public class SpaceEconomyListener extends SpaceAreaListener{

    @Override
    public void onSpaceEnter(SpaceEnterEvent event){
        if(event.getFrom()==event.getTo()){
            return;
        }
        if (!Economy.enter(event.getPlayer())) {
            SpaceMessageHandler.sendNotEnoughMoneyMessage(event.getPlayer());
            event.setCancelled(true);
            return;
        }
    }
    
    @Override
    public void onSpaceLeave(SpaceLeaveEvent event){
        if (!Economy.exit(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
        
    }
    
}
