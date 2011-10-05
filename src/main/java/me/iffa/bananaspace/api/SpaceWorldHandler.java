// Package Declaration
package me.iffa.bananaspace.api;

// Java Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.wgen.planets.PlanetsChunkGenerator;
import me.iffa.bananaspace.runnables.SpaceRunnable;
import me.iffa.bananaspace.wgen.SpaceChunkGenerator;
import me.iffa.bananaspace.config.SpaceConfig;
import me.iffa.bananaspace.config.SpacePlanetConfig;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Class that handles space worlds.
 * 
 * @author iffa
 */
public class SpaceWorldHandler {
    // Variables
    public static List<World> spaceWorlds = new ArrayList<World>();
    private BananaSpace plugin;
    private Map<World, Integer> forcenightId = new HashMap<World, Integer>();
    private boolean startupLoaded;
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
     * Loads the space worlds into <code>spaceWorlds</code> and creates them if Multiverse is not there.
     */
    public void loadSpaceWorlds() {
        List<String> worlds = SpaceConfig.getConfig().getKeys("worlds");
        if (worlds == null) {
            SpaceMessageHandler.print(Level.SEVERE, "Your configuration file has no worlds! Cancelling world generation process.");
            startupLoaded = false;
            return;
        }
        for (String world : worlds) {
            if (plugin.getServer().getWorld(world) == null) {
                if (!usingMV) {
                    World.Environment env;
                    if (SpaceConfig.getConfig().getBoolean("worlds." + world + ".nethermode", false)) {
                        env = World.Environment.NETHER;
                    } else {
                        env = World.Environment.NORMAL;
                    }
                    // Choosing which chunk generator to use
                    if (!SpaceConfig.getConfig().getBoolean("worlds." + world + ".generation.generateplanets", true)) {
                        SpaceMessageHandler.debugPrint(Level.INFO, "Creating startup world '" + world + "' with normal generator.");
                        WorldCreator.name(world).environment(env).generator(new SpaceChunkGenerator());
                    } else {
                        SpaceMessageHandler.debugPrint(Level.INFO, "Creating startup world '" + world + "' with planet generator.");
                        WorldCreator.name(world).environment(env).generator(new PlanetsChunkGenerator(SpacePlanetConfig.getConfig(), plugin));
                    }
                }
            }
            if (plugin.getServer().getWorld(world) != null) {
                spaceWorlds.add(Bukkit.getServer().getWorld(world));
            }
            startupLoaded = true;
        }
    }

    /**
     * Checks if any worlds were created/loaded on plugin startup.
     * 
     * @return true if any spaceworld was loaded from the config
     */
    public boolean getStartupLoaded() {
        if (startupLoaded) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if MultiVerse is being used for world generation.
     * 
     * @return true if MultiVerse is used
     */
    public boolean getUsingMV() {
        if (usingMV) {
            return true;
        }
        return false;
    }

    /**
     * Creates a spaceworld with the default settings and the given name. I suggest you to do the if worldname exists-check yourself aswell, <b>if you do not want BananaSpace to nag about it.</b>. I suggest you also turn logging on so users know an error was your fault, not mine.
     * 
     * @param plugin Your plugin
     * @param worldname Name for new spaceworld
     * @param log True if the console should be informed that an external plugin created a spaceworld.
     */
    public void createSpaceWorld(Plugin plugin, String worldname, boolean log) {
        if (plugin.getServer().getWorld(worldname) != null) {
            SpaceMessageHandler.print(Level.WARNING, "Plugin '" + plugin.getDescription().getName() + "' tried to create a new spaceworld with a name that is already a world! Nag to author(s) '" + plugin.getDescription().getAuthors() + "'!");
            return;
        }
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".generation.generateplanets", true);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".generation.glowstonechance", 1);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".generation.asteroidchance", 3);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".suit.required", false);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".helmet.required", false);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".breathingarea.maxroomheight", 5);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".weather", false);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".nethermode", false);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".alwaysnight", true);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".neutralmobs", true);
        SpaceConfig.getConfig().setProperty("worlds." + worldname + ".hostilemobs", false);
        SpaceConfig.getConfig().save();
        if (log) {
            SpaceMessageHandler.print(Level.INFO, "Plugin '" + plugin.getDescription().getName() + "' starting to create spaceworld '" + worldname + "'");
        }
        
        WorldCreator.name(worldname).environment(World.Environment.NORMAL).generator(new PlanetsChunkGenerator(SpacePlanetConfig.getConfig(), plugin));
        World world = plugin.getServer().getWorld(worldname);
        spaceWorlds.add(world);
        BananaSpace.pailInt.addSpaceList(worldname);
        if (log) {
            SpaceMessageHandler.print(Level.INFO, "Plugin '" + plugin.getDescription().getName() + "' created spaceworld '" + worldname + "'");
        }
    }

    /**
     * Removes a spaceworld with the default settings and the given name. I suggest you to do the if worldname exists-check yourself aswell, <b>if you do not want BananaSpace to nag about it.</b>. I suggest you also turn logging on so users know an error was your fault, not mine.
     * 
     * @param plugin Your plugin
     * @param worldname Name of spaceworld
     * @param log True if the console should be informed that an external plugin removed a spaceworld.
     */
    public void removeSpaceWorld(Plugin plugin, String worldname, boolean log) {
        if (plugin.getServer().getWorld(worldname) == null) {
            SpaceMessageHandler.print(Level.WARNING, "Plugin '" + plugin.getDescription().getName() + "' tried to remove a spaceworld with a name that doesn't exist! Nag to author(s) '" + plugin.getDescription().getAuthors() + "'!");
            return;
        }
        if (!this.isSpaceWorld(plugin.getServer().getWorld(worldname))) {
            SpaceMessageHandler.print(Level.WARNING, "Plugin '" + plugin.getDescription().getName() + "' tried to remove a spaceworld that is not a spaceworld! Nag to author(s) '" + plugin.getDescription().getAuthors() + "'!");
            return;
        }
        spaceWorlds.remove(plugin.getServer().getWorld(worldname));
        SpaceConfig.getConfig().removeProperty("worlds." + worldname);
        SpaceConfig.getConfig().save();
        /*
         * Removing a few properties that in most cases WILL be left over because of the Configuration-class.
         */
        SpaceConfig.getConfig().removeProperty("worlds." + worldname + "generation");
        SpaceConfig.getConfig().removeProperty("worlds" + worldname);
        SpaceConfig.getConfig().save();
        plugin.getServer().unloadWorld(worldname, true);
        if (log) {
            SpaceMessageHandler.print(Level.INFO, "Plugin '" + plugin.getDescription().getName() + "' removed spaceworld '" + worldname + "'");
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
}
