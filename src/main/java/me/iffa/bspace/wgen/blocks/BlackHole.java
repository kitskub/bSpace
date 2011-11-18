// Package Declaration
package me.iffa.bspace.wgen.blocks;

// Java Imports
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

// Spout Imports
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Represents a black hole block.
 * 
 * @author Jack
 * @author iffa
 */
public class BlackHole extends GenericCubeCustomBlock {
    // Variables
    private static Map<World, List<BlackHole>> holesMap = new HashMap<World, List<BlackHole>>();
    
    /**
     * Constructor of BlackHole.
     * 
     * @param plugin Plugin instance
     */
    public BlackHole(Plugin plugin) {
        super(plugin, "BlackHole", "http://cloud.github.com/downloads/iffa/bSpace/terrain_138.png", 16);
    }
    
    /**
     * Gets the map containing all black holes.
     * 
     * @return Map of all black holes
     */
    public static Map<World, List<BlackHole>> getHolesMap() {
        return holesMap;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int changedId) {
    }

    @Override
    public void onBlockPlace(World world, int x, int y, int z) {
    }

    @Override
    public void onBlockPlace(World world, int x, int y, int z, LivingEntity entity) {
    }

    @Override
    public void onBlockDestroyed(World world, int x, int y, int z) {
    }

    @Override
    public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
        return true;
    }

    @Override
    public void onEntityMoveAt(World world, int x, int y, int z, Entity entity) {
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, SpoutPlayer player) {
    }

    @Override
    public boolean isProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
        return false;
    }

    @Override
    public boolean isIndirectlyProvidingPowerTo(World world, int x, int y, int z, BlockFace face) {
        return false;
    }
}
