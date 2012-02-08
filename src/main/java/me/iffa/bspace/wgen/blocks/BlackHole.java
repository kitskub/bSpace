// Package Declaration
package me.iffa.bspace.wgen.blocks;

// Java Imports
import java.util.ArrayList;
import java.util.List;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

// Spout Imports
import org.getspout.spoutapi.block.SpoutBlock;
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
    private static List<SpoutBlock> holesMap = new ArrayList<SpoutBlock>();

    /**
     * Constructor of BlackHole.
     */
    public BlackHole() {
        super(Bukkit.getPluginManager().getPlugin("bSpace"), "BlackHole", "http://i.imgur.com/zVBCZ.png", 16);
    }

    /**
     * Gets the list containing all black holes.
     *
     * @return List of black holes
     */
    public static List<SpoutBlock> getHolesList() {
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

    @Override
    public void onBlockDestroyed(World world, int i, int i1, int i2, LivingEntity le) {
    }
}
