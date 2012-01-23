// Package Declaration
package me.iffa.bspace.listeners.misc;

// Java Imports
import java.util.HashMap;
import java.util.Map;

// bSpace Imports
import me.iffa.bspace.handlers.SpoutHandler;
import me.iffa.bspace.handlers.WorldHandler;
import me.iffa.bspace.runnables.SpoutBlackHoleChaosRunnable;
import me.iffa.bspace.wgen.blocks.BlackHole;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

// Spout Imports
import org.getspout.spoutapi.block.SpoutBlock;

/**
 * Player listener to trigger black hole sucking.
 * 
 * @author iffamies
 */
public class BlackHolePlayerListener implements Listener {
    // Variables
    private static Map<Player, Integer> runnables = new HashMap<Player, Integer>();
    private static Map<Chunk, Boolean> scanned = new HashMap<Chunk,Boolean>();
    private static final int SIZE = 20;
    private static long lastTime = System.currentTimeMillis();

    /**
     * Called when a player attempts to move.
     * 
     * @param event Event data
     */
    @EventHandler(event = PlayerMoveEvent.class, priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()||!WorldHandler.isInAnySpace(event.getPlayer())||event.getPlayer().getHealth()==0) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        if(!(lastTime+200<=currentTime)){
            return;
        }
        lastTime=System.currentTimeMillis();
        checkBlocks(event.getTo());
        for (SpoutBlock block : BlackHole.getHolesList()) {
            if (SpoutHandler.isInsideRadius(event.getPlayer(), block.getLocation(), SIZE)&&!runnables.containsKey(event.getPlayer())) {
                int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                        Bukkit.getPluginManager().getPlugin("bSpace"),
                        new SpoutBlackHoleChaosRunnable(event.getPlayer(), block),
                        0,//Delay
                        (long) 1); //Period
                runnables.put(event.getPlayer(), taskId);
                event.getPlayer().sendMessage("Black hole. -bam-, you're dead.");
                return;
            }
        }
    }

    /**
     * Gets all running suck tasks.
     * 
     * @return All tasks
     */
    public static Map<Player, Integer> getRunningTasks() {
        return runnables;
    }
    
    public static void stopRunnable(Player player){
        Bukkit.getScheduler().cancelTask(runnables.get(player));
        runnables.remove(player);
    }
    
    private static void checkBlocks(Location loc) {
        Chunk center = loc.getChunk();
        int chunks = (int) Math.ceil((SIZE-1)/16);
        chunks = chunks>Bukkit.getViewDistance()?Bukkit.getViewDistance():chunks;       
        for (int chunkX=-chunks; chunkX<=chunks; chunkX++){
            for (int chunkZ=-chunks; chunkZ<=chunks; chunkZ++){
                Chunk chunk = loc.getWorld().getChunkAt(center.getX()+chunkX, center.getZ()+chunkZ);
                if(scanned.containsKey(chunk)&&scanned.get(chunk)==true){
                   continue;
                }
                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < 128; y++) {
                        for (int z = 0; z < 16; z++) {
                            SpoutBlock block = (SpoutBlock) chunk.getBlock(x, y, z);
                            if (block.getBlockType() instanceof BlackHole && !BlackHole.getHolesList().contains(block)) {
                                BlackHole.getHolesList().add(block);
                            }
                        }
                    }
                }
                scanned.put(chunk, true);
            }
        }
    }
}
