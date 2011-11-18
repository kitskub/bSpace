// Package Declaration
package me.iffa.bspace.runnables;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.listeners.SpacePlayerListener;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * A runnable class for suffocating.
 * 
 * @author iffa
 */
public class SuffacationRunnable implements Runnable {
    // Variables
    private final Player player;

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
            if (player.getHealth() < 2 && player.getHealth() > 0) {
                player.setHealth(0);
                Space.scheduler.cancelTask(SpacePlayerListener.taskid.get(player));
                return;
            } else if (player.getHealth() <= 0) {
                Space.scheduler.cancelTask(SpacePlayerListener.taskid.get(player));
                return;
            }
            player.setHealth(player.getHealth() - 2);
        }
    }
}
