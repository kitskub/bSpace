// Package Declaration
package me.iffa.bspace.listeners.misc;

// bSpace Imports
import me.iffa.bspace.api.SpaceWorldHandler;
import me.iffa.bspace.wgen.blocks.BlackHole;

// Bukkit Imports
import org.bukkit.Chunk;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.WorldListener;

// Spout Imports
import org.getspout.spoutapi.block.SpoutBlock;

/**
 * Listens for chunk load events and checks if it has a black hole block that needs to be
 * added to the List.
 * 
 * @author iffamies
 */
public class BlackHoleScannerListener extends WorldListener {
    /**
     * Called when a chunk is loaded.
     * 
     * @param event Event data
     */
    @Override
    public void onChunkLoad(ChunkLoadEvent event) {
        if (!SpaceWorldHandler.isSpaceWorld(event.getWorld())) {
            return;
        }
        if(event.isNewChunk()){
            return;
        }
        Chunk chunk = event.getChunk();
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
    }
    
    @Override
    public void onChunkPopulate(ChunkPopulateEvent event){
        if (!SpaceWorldHandler.isSpaceWorld(event.getWorld())) {
            return;
        }
        
        Chunk chunk = event.getChunk();
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
    }
    
}
