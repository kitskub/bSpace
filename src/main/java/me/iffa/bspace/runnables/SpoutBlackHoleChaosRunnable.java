// Package Declaration
package me.iffa.bspace.runnables;

// bSpace Imports
import me.iffa.bspace.api.SpaceSpoutHandler;
import me.iffa.bspace.listeners.misc.BlackHolePlayerListener;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Location;

// Spout Imports
import org.getspout.spoutapi.block.SpoutBlock;

// Bukkit Imports
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Runnable that handles causing chaos with black holes.
 * 
 * @author iffamies
 * @author SwearWord (thanks for your awesome maths!)
 */
public class SpoutBlackHoleChaosRunnable implements Runnable {
    // Variables
    private SpoutBlock block;
    private Player player;
    private float angle;
    private double xDistance;
    private double zDistance;
    private double absDistance;
    private double index = .1;

    /**
     * Constructor of SpoutBlackHoleChaosRunnable.
     * 
     * @param player Entity to suck
     * @param block Black hole block
     */
    public SpoutBlackHoleChaosRunnable(Player player, SpoutBlock block) {
        this.block = block;
        this.player = player;
        xDistance = block.getLocation().getX() - player.getLocation().getX();
        zDistance = block.getLocation().getZ() - player.getLocation().getZ();
        absDistance = Math.sqrt((xDistance*xDistance) + (zDistance*zDistance));
        angle = (float) Math.atan(zDistance / xDistance);
    }

    /**
     * Magic happens here.
     */
    @Override
    public void run() {
        if (player.isDead()) {
            Integer i = BlackHolePlayerListener.getRunningTasks().get(player);
            if(i==null||!Bukkit.getScheduler().isQueued(i)) return;
            BlackHolePlayerListener.stopRunnable(player);
            return;
        }
        absDistance -= index;
        xDistance = Math.cos(angle) * absDistance;
        zDistance = Math.sin(angle) * absDistance;
        double blockX = block.getLocation().getBlockX();
        double blockZ = block.getLocation().getBlockZ();
        double entityX = player.getLocation().getBlockZ();
        double entityZ = player.getLocation().getBlockZ();
	if(blockX<entityX){
            entityX = blockX - xDistance;
        }
        else{
            entityX = blockX + xDistance;
        }
        if(blockZ<entityZ){
            entityZ = blockZ - zDistance;
        }
        else{
            entityZ = blockZ + zDistance;
        }
	index += .05;
        if (SpaceSpoutHandler.isInsideRadius(player, block.getLocation(), 1)) {
            player.setHealth(0);
            BlackHolePlayerListener.stopRunnable(player);
            return;
        }
        player.teleport(new Location(player.getWorld(), entityX, block.getY(), entityZ));
    }
}
