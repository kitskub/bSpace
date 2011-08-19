// Package Declaration
package me.iffa.bananaspace.api;

// Java Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// BananaSpace Imports
import java.util.Map;
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.wgen.planets.PlanetsChunkGenerator;
import me.iffa.bananaspace.schedulers.SpaceRunnable;
import me.iffa.bananaspace.wgen.SpaceChunkGenerator;
import me.iffa.bananaspace.config.SpaceConfig;
import me.iffa.bananaspace.config.SpacePlanetConfig;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Class that handles space worlds. (internally)
 * 
 * @author iffa
 */
public class SpaceWorldHandler {
    // Variables
    private BananaSpace plugin;
    private Map<World, Integer> forcenightId = new HashMap<World, Integer>();
    public static List<World> spaceWorlds = new ArrayList<World>();

    // Constructor
    public SpaceWorldHandler(BananaSpace plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates all space worlds from the configuration file. WARNING: Do not use this with your plugin, as it may cause explosions etc etc!
     */
    public void createSpaceWorlds() {
        List<String> worlds = SpaceConfig.myConfig.getKeys("worlds");
        if (worlds == null) {
            BananaSpace.log.severe(BananaSpace.prefix + " Your configuration file has no worlds! Cancelling world generation process.");
            return;
        }
        for (String world : worlds) {
            if (plugin.getServer().getWorld(world) != null) {
            World.Environment env;
            if (SpaceConfig.myConfig.getBoolean("worlds." + world + ".nethermode", false)) {
                env = World.Environment.NETHER;
            } else {
                env = World.Environment.NORMAL;
            }
            // Choosing which chunk generator to use
            if (!SpaceConfig.myConfig.getBoolean(
                    "worlds." + world + ".generation.generateplanets", true)) {
                plugin.getServer().createWorld(world,
                        env, new SpaceChunkGenerator());
                spaceWorlds.add(Bukkit.getServer().getWorld(world));
            } else {
                plugin.getServer().createWorld(world,
                        env,
                        new PlanetsChunkGenerator(SpacePlanetConfig.myConfig, plugin));
                spaceWorlds.add(Bukkit.getServer().getWorld(world));
            }
            }
        }
    }

    /**
     * Creates a spaceworld with the default settings and the given name. I suggest you to do the if worldname exists-check yourself aswell, <b>if you do not want BananaSpace to nag about it.</b>.
     * 
     * @param plugin Your plugin
     * @param worldname Name for new spaceworld
     * @param log True if the console should be informed that an external plugin created a spaceworld.
     */
    public void createSpaceWorld(Plugin plugin, String worldname, boolean log) {
        if (plugin.getServer().getWorld(worldname) != null) {
            BananaSpace.log.warning(BananaSpace.prefix + " Plugin '" + plugin.getDescription().getName() + "' tried to create a new spaceworld with a name that is already a world! Nag to author(s) '" + plugin.getDescription().getAuthors() + "'!");
            return;
        }
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".generation.generateplanets", true);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".generation.glowstonechance", 1);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".generation.asteroidchance", 3);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".suit.required", false);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".helmet.required", false);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".breathingarea.maxroomheight", 5);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".weather", false);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".nethermode", false);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".alwaysnight", true);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".neutralmobs", true);
        SpaceConfig.myConfig.setProperty("worlds." + worldname + ".hostilemobs", false);
        SpaceConfig.myConfig.save();
        plugin.getServer().createWorld(worldname, World.Environment.NORMAL, new PlanetsChunkGenerator(SpacePlanetConfig.myConfig, plugin));
        World world = plugin.getServer().getWorld(worldname);
        spaceWorlds.add(world);
        if (log) {
            BananaSpace.log.info(BananaSpace.prefix + " Plugin '" + plugin.getDescription().getName() + "' created spaceworld '" + worldname + "'");
        }
    }

    /**
     * Removes a spaceworld with the default settings and the given name. I suggest you to do the if worldname exists-check yourself aswell, <b>if you do not want BananaSpace to nag about it.</b>.
     * 
     * @param plugin Your plugin
     * @param worldname Name of spaceworld
     * @param log True if the console should be informed that an external plugin removed a spaceworld.
     */
    public void removeSpaceWorld(Plugin plugin, String worldname, boolean log) {
        if (plugin.getServer().getWorld(worldname) == null) {
            BananaSpace.log.warning(BananaSpace.prefix + " Plugin '" + plugin.getDescription().getName() + "' tried to remove a spaceworld with a name that doesn't exist! Nag to author(s) '" + plugin.getDescription().getAuthors() + "'!");
            return;
        }
        if (!this.isSpaceWorld(plugin.getServer().getWorld(worldname))) {
            BananaSpace.log.warning(BananaSpace.prefix + " Plugin '" + plugin.getDescription().getName() + "' tried to remove a spaceworld that is not a spaceworld! Nag to author(s) '" + plugin.getDescription().getAuthors() + "'!");
            return;
        }
        spaceWorlds.remove(plugin.getServer().getWorld(worldname));
        SpaceConfig.myConfig.removeProperty("worlds." + worldname);
        SpaceConfig.myConfig.save();
        plugin.getServer().unloadWorld(worldname, true);
        if (log) {
            BananaSpace.log.info(BananaSpace.prefix + " Plugin '" + plugin.getDescription().getName() + "' removed spaceworld '" + worldname + "'");
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
     * Stops the force night task.
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
     * @return null if not in a space world
     */
    public World getSpaceWorld(Player player) {
        if (getSpaceWorlds().contains(player.getWorld())) {
            return getSpaceWorlds().get(getSpaceWorlds().indexOf(player.getWorld()));
        }
        return null;
    }
}
