// Package Declaration
package me.iffa.bananaspace.runnables;

import org.getspout.spoutapi.block.SpoutBlock;

/**
 * WIP
 * 
 * @author iffamies
 */
public class SpoutBlackHoleRunnable implements Runnable {
    // Variables
    private SpoutBlock block = null;

    /**
     * Constructor of SpoutBlackHoleRunnable.
     * 
     * @param block Spout block (black hole block)
     */
    public SpoutBlackHoleRunnable(SpoutBlock block) {
        this.block = block;
    }
    
    /**
     * To be called every other tick.
     */
    @Override
    public void run() {
    }
}
