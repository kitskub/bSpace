// Package Declaration
package me.iffa.bananaspace;

// Java imports
import java.util.logging.Logger;

// BananaSpace imports
import me.iffa.bananaspace.listeners.SpaceEntityListener;
import me.iffa.bananaspace.listeners.SpacePlayerListener;
import me.iffa.bananaspace.listeners.misc.SpaceWeatherListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutPlayerListener;
import me.iffa.bananaspace.commands.SpaceCommand;
import me.iffa.bananaspace.config.SpaceConfig;
import me.iffa.bananaspace.api.SpaceWorldHandler;
import me.iffa.bananaspace.api.SpaceConfigHandler;
import me.iffa.bananaspace.api.SpacePlayerHandler;
import me.iffa.bananaspace.config.SpacePlanetConfig;
import me.iffa.bananaspace.gui.PailInterface;
import me.iffa.bananaspace.wgen.SpaceChunkGenerator;
import me.iffa.bananaspace.wgen.planets.PlanetsChunkGenerator;

// Bukkit imports
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.generator.ChunkGenerator;

// Pail Imports
import me.escapeNT.pail.Pail;

/**
 * Main class of BananaSpace
 * 
 * @author iffa
 * 
 */
public class BananaSpace extends JavaPlugin {
    // Variables
    public static String prefix;
    public static String version;
    public static final Logger log = Logger.getLogger("Minecraft");
    public static BukkitScheduler scheduler;
    private SpaceCommand sce = null;
    public static SpaceWorldHandler worldHandler;
    public static SpacePlayerHandler playerHandler;
    public static PailInterface pailInt;
    private final SpaceWeatherListener weatherListener = new SpaceWeatherListener(
            this);
    private final SpaceEntityListener entityListener = new SpaceEntityListener(
            this);
    private final SpacePlayerListener playerListener = new SpacePlayerListener(
            this);
    private final SpaceSpoutPlayerListener spListener = new SpaceSpoutPlayerListener(
            this);

    /**
     * Called when the plugin is loaded
     */
    @Override
    public void onDisable() {
        log.info(prefix + " Disabled version " + version);
    }

    /**
     * Called when the plugin is loaded
     */
    @Override
    public void onEnable() {
        // Initializing some variables
        version = getDescription().getVersion();
        prefix = "[" + getDescription().getName() + "]";
        scheduler = getServer().getScheduler();
        PluginManager pm = getServer().getPluginManager();
        worldHandler = new SpaceWorldHandler(this);
        playerHandler = new SpacePlayerHandler();

        // Loading configuration files
        SpaceConfig.loadConfig();
        SpacePlanetConfig.loadConfig();
        debugLog("Initialized startup variables and loaded configuration files.");

        // Registering other events
        pm.registerEvent(Event.Type.WEATHER_CHANGE, weatherListener,
                Event.Priority.Normal, this);

        // Registering entity & player events
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener,
                Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener,
                Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener,
                Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener,
                Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener,
                Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener,
                Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener,
                Event.Priority.Normal, this);
        debugLog("Registered events (General).");
        
        // Registering events for Spout
        if (pm.getPlugin("Spout") != null && SpaceConfigHandler.isUsingSpout()) {
            pm.registerEvent(Event.Type.PLAYER_TELEPORT, spListener, Event.Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_JOIN, spListener, Event.Priority.Normal, this);
            debugLog("Registered events (Spout).");
        }

        // Creating all space worlds if MultiVerse-Core is not found.
        if (pm.getPlugin("Multiverse-Core") == null) {
            debugLog("Starting to create spaceworlds (startup).");
            worldHandler.createSpaceWorlds();
        }

        // Initializing the CommandExecutor
        sce = new SpaceCommand(this);
        getCommand("space").setExecutor(sce);
        debugLog("Initialized CommandExecutors.");

        // Checking if it should always be night in space
        for (World world : worldHandler.getSpaceWorlds()) {
            if (SpaceConfigHandler.forceNight(world)) {
                worldHandler.startForceNightTask(world);
                debugLog("Started night forcing task for world '" + world.getName() + "'.");
            }
        }

        // Pail
        if (pm.getPlugin("Pail") != null) {
            debugLog("Starting up the Pail tab.");
            pailInt = new PailInterface(this);
            ((Pail) pm.getPlugin("Pail")).loadInterfaceComponent("BananaSpace", pailInt);
        }

        log.info(prefix + " Enabled version " + version);
    }

    /**
     * Prints a debug message to the server log.
     * 
     * @param msg Message
     */
    public static void debugLog(String msg) {
        if (!SpaceConfigHandler.getDebugging()) {
            return;
        }
        log.info(prefix + " " + msg);
    }

    /**
     * Gets the default world generator of the plugin.
     * 
     * @param worldName World name
     * @param id ID (cow, fish etc)
     * 
     * @return ChunkGenerator
     */
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        if (id == null || id.isEmpty()) {
            return new SpaceChunkGenerator();
        }
        if (id.equalsIgnoreCase("planets")) {
            return new PlanetsChunkGenerator(SpacePlanetConfig.getConfig(), this);
        }
        return new SpaceChunkGenerator();
    }

    /* Some API methods */
    /**
     * Gets the SpaceWorldHandler.
     * 
     * @return SpaceWorldHandler
     */
    public static SpaceWorldHandler getWorldHandler() {
        return worldHandler;
    }

    /**
     * Gets the SpacePlayerHandler.
     * 
     * @return SpacePlayerHandler
     */
    public static SpacePlayerHandler getPlayerHandler() {
        return playerHandler;
    }
}