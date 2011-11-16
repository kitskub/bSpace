/*
 */
package me.iffa.bananaspace.wgen.blocks;

import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 *
 * @author Jack
 */
public class BlackHole extends GenericCubeCustomBlock{
    public BlackHole(Plugin plugin){
        super(plugin,"BlackHole","http://cloud.github.com/downloads/iffa/BananaSpace/terrain_138.png", 16);
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, int changedId) {
        return;
    }

    public void onBlockPlace(World world, int x, int y, int z) {
        return;
    }

    public void onBlockPlace(World world, int x, int y, int z, LivingEntity entity) {
        return;
    }

    public void onBlockDestroyed(World world, int x, int y, int z) {
        return;
    }

    public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
        return true;
    }

    public void onEntityMoveAt(World world, int x, int y, int z, Entity entity) {
        return;
    }

    public void onBlockClicked(World world, int x, int y, int z, SpoutPlayer player) {
        return;
    }

    public boolean isProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
        return false;
    }

    public boolean isIndirectlyProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
        return false;
    }
    
}
