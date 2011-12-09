// Package Declaration
package me.iffa.bspace.runnables;

// bSpace Imports
import java.util.logging.Level;
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpacePlayerHandler;
import me.iffa.bspace.api.event.misc.SpaceSuffocationEvent;
import me.iffa.bspace.listeners.SpaceSuffocationListener;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A runnable class for suffocating.
 * 
 * @author iffa
 */
public class SuffacationRunnable implements Runnable {
    // Variables
    private final Player player;
    private boolean suffocating = false;

    /**
     * Constructor for SuffacationRunnable.
     * 
     * @param player Player
     */
    public SuffacationRunnable(Player player) {
        this.player = player;
    }

    /**
     * Suffocates the player when repeated every second.
     */
    @Override
    public void run() {
        if (!player.isDead()) {
            if(SpacePlayerHandler.checkNeedsSuffocation(player)){
                if(!suffocating){
                    /* Notify listeners start */
                    SpaceSuffocationEvent e = new SpaceSuffocationEvent(player);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (e.isCancelled()) {
                        return;
                    }
                    /* Notify listeners end */
                    suffocating=true;
                    SpaceMessageHandler.debugPrint(Level.INFO, "Player '" + player.getName() + "' is now suffocate in space.");
                }
            } else {
                if(suffocating) suffocating = false;
            }
            
            if(suffocating){
                if (player.getHealth() < 2 && player.getHealth() > 0) {
                    player.setHealth(0);
                    Bukkit.getScheduler().cancelTask(SpaceSuffocationListener.taskid.get(player));
                    return;
                } else if (player.getHealth() <= 0) {
                    Bukkit.getScheduler().cancelTask(SpaceSuffocationListener.taskid.get(player));
                    return;
                }
                player.setHealth(player.getHealth() - 2);
            }
        }
        Bukkit.getScheduler().cancelTask(SpaceSuffocationListener.taskid.get(player));
    }
}
