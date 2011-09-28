// Package Declaration
package me.iffa.bananaspace;

// Java imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

//Pail Imports
import me.escapeNT.pail.Pail;

//Economy Imports
import me.iffa.bananaspace.economy.Economy;

//BananaSpace Imports
import me.iffa.bananaspace.api.SpaceConfigHandler;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.api.SpacePlayerHandler;
import me.iffa.bananaspace.api.SpaceWorldHandler;
import me.iffa.bananaspace.commands.SpaceCommandHandler;
import me.iffa.bananaspace.config.SpaceConfig;
import me.iffa.bananaspace.config.SpacePlanetConfig;
import me.iffa.bananaspace.gui.PailInterface;
import me.iffa.bananaspace.listeners.SpaceEntityListener;
import me.iffa.bananaspace.listeners.SpacePlayerListener;
import me.iffa.bananaspace.listeners.misc.SpaceWeatherListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutAreaListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutCraftListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutEntityListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutKeyListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutPlayerListener;
import me.iffa.bananaspace.wgen.SpaceChunkGenerator;
import me.iffa.bananaspace.wgen.planets.PlanetsChunkGenerator;

//Bukkit Imports
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Main class of BananaSpace
 * 
 * @author iffa
 */
public class BananaSpace extends JavaPlugin {
    // Variables

    public static String prefix;
    public static String version;
    public static BukkitScheduler scheduler;
    public static SpaceWorldHandler worldHandler;
    public static SpacePlayerHandler playerHandler;
    public static SpaceMessageHandler messageHandler;
    public static PailInterface pailInt;
    public static PluginManager pm;
    private SpaceCommandHandler sce = null;
    private Economy economy;
    public static Map<Player, Location> locCache = null;
    public static boolean jumpPressed = false;
    private final SpaceWeatherListener weatherListener = new SpaceWeatherListener(this);
    private final SpaceEntityListener entityListener = new SpaceEntityListener(this);
    private final SpacePlayerListener playerListener = new SpacePlayerListener(this);
    private final SpaceSpoutPlayerListener spListener = new SpaceSpoutPlayerListener(this);
    private final SpaceSpoutCraftListener spcListener = new SpaceSpoutCraftListener(this);
    private final SpaceSpoutEntityListener speListener = new SpaceSpoutEntityListener(this);
    private final SpaceSpoutAreaListener spaListener = new SpaceSpoutAreaListener(this);
    private final SpaceSpoutKeyListener spkListener = new SpaceSpoutKeyListener(this);

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        messageHandler.print(Level.INFO, "Disabled version " + version);
    }

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        // Initializing variables
        initVariables();

        // Loading configuration files
        SpaceConfig.loadConfig();
        SpacePlanetConfig.loadConfig();
        SpaceMessageHandler.debugPrint(Level.INFO, "Initialized startup variables and loaded configuration files.");

        // Registering other events
        pm.registerEvent(Event.Type.WEATHER_CHANGE, weatherListener, Event.Priority.Normal, this);

        // Registering entity & player events
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
        SpaceMessageHandler.debugPrint(Level.INFO, "Registered events (General).");

        // Registering events for Spout
        if (pm.getPlugin("Spout") != null && SpaceConfigHandler.isUsingSpout()) {
            pm.registerEvent(Event.Type.PLAYER_TELEPORT, spListener, Event.Priority.Normal, this); //Player listener
            //pm.registerEvent(Event.Type.PLAYER_JOIN, spListener, Event.Priority.Normal, this); //moved this into a Custom Listener
            pm.registerEvent(Event.Type.ENTITY_DAMAGE, speListener, Event.Priority.Normal, this); //Entity Listener
            //pm.registerEvent(Event.Type.CREATURE_SPAWN, speListener, Event.Priority.Normal, this); //Disabled until Limitations in Spout is fixed
            pm.registerEvent(Event.Type.CUSTOM_EVENT, spcListener, Event.Priority.Normal, this); //SpoutCraft Listener
            pm.registerEvent(Event.Type.CUSTOM_EVENT, spaListener, Event.Priority.Normal, this); //Area Listener
            pm.registerEvent(Event.Type.CUSTOM_EVENT, spkListener, Event.Priority.Normal, this); //Key Listener
            SpaceMessageHandler.debugPrint(Level.INFO, "Registered events (Spout).");
        }

        // Loading space worlds (startup).
        worldHandler.loadSpaceWorlds();

        // Initializing the CommandExecutor
        sce = new SpaceCommandHandler(this);
        getCommand("space").setExecutor(sce);
        SpaceMessageHandler.debugPrint(Level.INFO, "Initialized CommandExecutors.");

        // Checking if it should always be night in space
        for (World world : worldHandler.getSpaceWorlds()) {
            if (SpaceConfigHandler.forceNight(world)) {
                worldHandler.startForceNightTask(world);
                SpaceMessageHandler.debugPrint(Level.INFO, "Started night forcing task for world '" + world.getName() + "'.");
            }
        }
        
        //Economy
        if (economy == null) {
            if (Economy.checkEconomy(this)) {
                economy = new Economy(this);
            } else {
                economy = new Economy();
            }
        }
        
        // Pail interface
        if (pm.getPlugin("Pail") != null) {
            SpaceMessageHandler.debugPrint(Level.INFO, "Starting up the Pail tab.");
            pailInt = new PailInterface(this);
            ((Pail) pm.getPlugin("Pail")).loadInterfaceComponent("BananaSpace", pailInt);
        }

        messageHandler.print(Level.INFO, "Enabled version " + version);
    }

    /**
     * Initializes variables (used on startup).
     */
    private void initVariables() {
        pm = getServer().getPluginManager();
        version = getDescription().getVersion();
        prefix = "[" + getDescription().getName() + "]";
        scheduler = getServer().getScheduler();
        worldHandler = new SpaceWorldHandler(this);
        playerHandler = new SpacePlayerHandler();
        messageHandler = new SpaceMessageHandler(this, prefix);
        if (pm.getPlugin("Spout") != null && SpaceConfigHandler.isUsingSpout()) {
            locCache = new HashMap<Player, Location>();
        }
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
        SpaceMessageHandler.debugPrint(Level.INFO, "Getting generator for" + worldName + " " + id);
        if (!SpaceConfigHandler.isWorldInConfig(worldName)) {
            worldHandler.createSpaceWorld(this, worldName, true);
        }
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

    /**
     * Gets the SpaceMessageHandler.
     * 
     * @return SpaceMessageHandler
     */
    public static SpaceMessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * Gets the Economy-class.
     * 
     * @return Economy
     */
    public Economy getEconomy() {
        return economy;
    }
}