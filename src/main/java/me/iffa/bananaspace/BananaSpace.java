// Package Declaration
package me.iffa.bananaspace;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// Pail Imports
import me.escapeNT.pail.Pail;

// PluginStats Imports
import com.randomappdev.pluginstats.Ping;

// BananaSpace Imports
import me.iffa.bananaspace.api.SpaceConfigHandler;
import me.iffa.bananaspace.api.SpaceLangHandler;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.api.SpaceSchematicHandler;
import me.iffa.bananaspace.api.SpaceWorldHandler;
import me.iffa.bananaspace.commands.SpaceCommandHandler;
import me.iffa.bananaspace.config.SpaceConfig;
import me.iffa.bananaspace.config.SpaceConfigUpdater;
import me.iffa.bananaspace.economy.Economy;
import me.iffa.bananaspace.gui.PailInterface;
import me.iffa.bananaspace.listeners.SpaceEntityListener;
import me.iffa.bananaspace.listeners.SpacePlayerListener;
import me.iffa.bananaspace.listeners.misc.SpaceWeatherListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutAreaListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutCraftListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutEntityListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutKeyListener;
import me.iffa.bananaspace.listeners.spout.SpaceSpoutPlayerListener;
import me.iffa.bananaspace.wgen.planets.PlanetsChunkGenerator;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Main class of BananaSpace.
 * 
 * @author iffa
 * @author kitskub
 * @author HACKhalo2
 */
public class BananaSpace extends JavaPlugin {
    // Variables
    public static String prefix;
    public static String version;
    public static BukkitScheduler scheduler;
    public static SpaceWorldHandler worldHandler;
    public static PailInterface pailInt;
    public static PluginManager pm;
    public static Map<Player, Location> locCache = null;
    public static boolean jumpPressed = false;
    private SpaceCommandHandler sce = null;
    private Economy economy;
    private final SpaceWeatherListener weatherListener = new SpaceWeatherListener();
    private final SpaceEntityListener entityListener = new SpaceEntityListener();
    private final SpacePlayerListener playerListener = new SpacePlayerListener(this);

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        // Finishing up disablation.
        SpaceMessageHandler.print(Level.INFO, SpaceLangHandler.getDisabledMessage());
    }

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        // Initializing variables.
        initVariables();
        SpaceMessageHandler.debugPrint(Level.INFO, "Initialized startup variables.");

        // Loading configuration files.
        SpaceConfig.loadConfigs();
        SpaceMessageHandler.debugPrint(Level.INFO, "Loaded configuration files, now checking if they need to be updated...");
        // Updating configuration files (if needed).
        SpaceConfigUpdater.updateConfigs();

        // Registering events.
        registerEvents();

        // Loading schematic files.
        SpaceSchematicHandler.loadSchematics();

        // Loading space worlds (startup).
        worldHandler.loadSpaceWorlds();

        // Initializing the CommandExecutor for /space.
        sce = new SpaceCommandHandler(this);
        getCommand("space").setExecutor(sce);
        SpaceMessageHandler.debugPrint(Level.INFO, "Initialized CommandExecutors.");

        // Checking if it should always be night in space worlds.
        for (World world : worldHandler.getSpaceWorlds()) {
            if (SpaceConfigHandler.forceNight(world)) {
                worldHandler.startForceNightTask(world);
                SpaceMessageHandler.debugPrint(Level.INFO, "Started night forcing task for world '" + world.getName() + "'.");
            }
        }

        // Checking if Economy is ok.
        if (economy == null && getServer().getPluginManager().getPlugin("Register") != null) {
            if (Economy.checkEconomy()) {
                economy = new Economy();
            }
        }

        // Initializing the Pail tab.
        if (pm.getPlugin("Pail") != null) {
            SpaceMessageHandler.debugPrint(Level.INFO, "Starting up the Pail tab.");
            pailInt = new PailInterface();
            ((Pail) pm.getPlugin("Pail")).loadInterfaceComponent("BananaSpace", pailInt);
        }

        // Finishing up enablation.
        SpaceMessageHandler.print(Level.INFO, SpaceLangHandler.getUsageStatsMessage());
        Ping.init(this);
        SpaceMessageHandler.print(Level.INFO, SpaceLangHandler.getEnabledMessage());
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
        if (pm.getPlugin("Spout") != null && SpaceConfigHandler.isUsingSpout()) {
            locCache = new HashMap<Player, Location>();
        }
    }

    /**
     * Registers events for BananaSpace.
     */
    private void registerEvents() {
        // Registering other events.
        pm.registerEvent(Event.Type.WEATHER_CHANGE, weatherListener, Event.Priority.Normal, this);
        SpaceMessageHandler.debugPrint(Level.INFO, "Registered events (other).");

        // Registering entity & player events.
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
        SpaceMessageHandler.debugPrint(Level.INFO, "Registered events (entity & player).");

        // Registering events for Spout.
        if (pm.getPlugin("Spout") != null && SpaceConfigHandler.isUsingSpout()) {
            pm.registerEvent(Event.Type.PLAYER_TELEPORT, new SpaceSpoutPlayerListener(this), Event.Priority.Normal, this); //Player listener
            pm.registerEvent(Event.Type.PLAYER_RESPAWN, new SpaceSpoutPlayerListener(this), Event.Priority.Normal, this); // Player listener
            //pm.registerEvent(Event.Type.PLAYER_JOIN, spListener, Event.Priority.Normal, this); //moved this into a Custom Listener
            pm.registerEvent(Event.Type.ENTITY_DAMAGE, new SpaceSpoutEntityListener(), Event.Priority.Normal, this); //Entity Listener
            //pm.registerEvent(Event.Type.CREATURE_SPAWN, speListener, Event.Priority.Normal, this); //Disabled until Limitations in Spout is fixed
            pm.registerEvent(Event.Type.CUSTOM_EVENT, new SpaceSpoutCraftListener(), Event.Priority.Normal, this); //SpoutCraft Listener
            pm.registerEvent(Event.Type.CUSTOM_EVENT, new SpaceSpoutAreaListener(), Event.Priority.Normal, this); //Area Listener
            pm.registerEvent(Event.Type.CUSTOM_EVENT, new SpaceSpoutKeyListener(), Event.Priority.Normal, this); //Key Listener
            SpaceMessageHandler.debugPrint(Level.INFO, "Registered events (Spout).");
        }
    }

    /**
     * Gets the default world generator of the plugin.
     * 
     * @param worldName World name
     * @param id ID (cow, fish etc)
     * 
     * @return ChunkGenerator to use
     */
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        SpaceMessageHandler.debugPrint(Level.INFO, "Getting generator for '" + worldName + "' using id: '" + id + "'");
        SpaceWorldHandler.checkWorld(worldName);
        if (id.isEmpty() || id.length() == 0 || id == null) {
            return new PlanetsChunkGenerator("planets", false);
        }
        return new PlanetsChunkGenerator(id);
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
     * Gets the Economy-class.
     * 
     * @return Economy
     */
    public Economy getEconomy() {
        return economy;
    }
}