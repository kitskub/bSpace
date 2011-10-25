// Package Declaration
package me.iffa.bananaspace.api;

// Java Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.runnables.SpaceRunnable;
import me.iffa.bananaspace.wgen.SpaceChunkGenerator;
import me.iffa.bananaspace.wgen.planets.PlanetsChunkGenerator;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Class that handles space worlds.
 * 
 * @author iffa
 * @author Jack
 */
public class SpaceWorldHandler {
    // Variables
    public static List<World> spaceWorlds = new ArrayList<World>();
    private BananaSpace plugin;
    private Map<World, Integer> forcenightId = new HashMap<World, Integer>();
    private boolean usingMV;

    /**
     * Constructor of SpaceWorldHandler.
     * 
     * @param plugin BananaSpace
     */
    public SpaceWorldHandler(BananaSpace plugin) {
        this.plugin = plugin;
        if (plugin.getServer().getPluginManager().getPlugin("Multiverse-Core") != null) {
            usingMV = true;
        }
    }

    /**
     * Loads the space worlds into <code>spaceWorlds</code.
     */
    public void loadSpaceWorlds() {
        for (World world : plugin.getServer().getWorlds()) {
            if (world.getGenerator() instanceof PlanetsChunkGenerator || world.getGenerator() instanceof SpaceChunkGenerator) {
                spaceWorlds.add(world);
            }
        }
    }

    /**
     * Starts the force night task if required.
     * 
     * @param world World
     */
    public void startForceNightTask(World world) {
        SpaceRunnable task = new SpaceRunnable(world);
        forcenightId.put(world, BananaSpace.scheduler.scheduleSyncRepeatingTask(plugin, task, 60, 8399));
    }

    /**
     * Stops the force night task. No safety checks made, explosions may occur.
     * 
     * @param world World
     */
    public void stopForceNightTask(World world) {
        BananaSpace.scheduler.cancelTask(forcenightId.get(world));
    }

    /**
     * Gives all the space worlds of the server.
     * 
     * @return all space worlds as a List
     */
    public List<World> getSpaceWorlds() {
        return spaceWorlds;
    }

    /**
     * Checks if a world is a space world.
     * 
     * @param world World to check
     * 
     * @return true if the world is a space world
     */
    public boolean isSpaceWorld(World world) {
        if (spaceWorlds.contains(world)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a player is in a space world.
     * 
     * @param player Player to check
     * @param world Space world
     * 
     * @return true if the player is in the specified space world
     */
    public boolean isInSpace(Player player, World world) {
        return (spaceWorlds.contains(world) && player.getWorld() == world);
    }

    /**
     * Checks if a player is in any space world.
     * 
     * @param player Player
     * 
     * @return true if the player is in a space world
     */
    public boolean isInAnySpace(Player player) {
        for (World world : spaceWorlds) {
            if (player.getWorld() == world) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the space world a player is in.
     * 
     * @param player Player
     * 
     * @return Null if not in a space world
     */
    public World getSpaceWorld(Player player) {
        if (getSpaceWorlds().contains(player.getWorld())) {
            return getSpaceWorlds().get(getSpaceWorlds().indexOf(player.getWorld()));
        }
        return null;
    }

    /**
     * Checks the world to see if it is <code>spaceWorlds</code>, and adds it if not.
     * 
     * @param worldName World name to check
     */
    public void checkWorld(String worldName) {
        boolean in = false;
        for (World world : spaceWorlds) {
            if (world.getName().equals(worldName)) {
                in = true;
            }
        }
        if (!in) {
            spaceWorlds.add(plugin.getServer().getWorld(worldName));
        }
    }
}
