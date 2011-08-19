// Package Declaration
package me.iffa.bananaspace.schedulers;

// Bukkit Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.listeners.SpacePlayerListener;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * A runnable class for suffocating.
 * 
 * @author iffa
 * 
 */
public class SpaceRunnable2 implements Runnable {
    // Variables
    private Player player;

    // Constructor
    public SpaceRunnable2(Player player) {
        this.player = player;
    }

    /**
     * Suffocates the player when repeated every second.
     */
    @Override
    public void run() {
        if (player.getHealth() < 2 && player.getHealth() > 0) {
            BananaSpace.scheduler.cancelTask(SpacePlayerListener.taskid.get(player));
        }
        player.setHealth(player.getHealth() - 2);
    }
}
