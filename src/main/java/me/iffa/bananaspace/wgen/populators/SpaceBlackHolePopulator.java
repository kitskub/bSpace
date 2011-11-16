/*
 */
package me.iffa.bananaspace.wgen.populators;

import java.util.Random;
import me.iffa.bananaspace.wgen.blocks.BlackHole;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutBlock;

/**
 *
 * @author Jack
 */
public class SpaceBlackHolePopulator extends BlockPopulator{
    private Plugin plugin;

    public SpaceBlackHolePopulator(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void populate(World world, Random random, Chunk source) {
        if(random.nextInt(100)<=50){//TODO replace with chance
        int x = random.nextInt(16);
        int z = random.nextInt(16);
        int y = random.nextInt(127);
        Block currentBlock = source.getBlock(x, y, z);
        SpoutManager.getMaterialManager().overrideBlock(currentBlock, new BlackHole(plugin));
        }
    }
    
}
