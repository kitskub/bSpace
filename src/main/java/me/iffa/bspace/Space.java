// Package Declaration
package me.iffa.bspace;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// Pail Imports
import me.escapeNT.pail.Pail;

// PluginStats Imports
import com.randomappdev.pluginstats.Ping;

// bSpace Imports
import me.iffa.bspace.api.SpaceAddon;
import me.iffa.bspace.api.schematic.SpaceSchematicHandler;
import me.iffa.bspace.commands.SpaceCommandHandler;
import me.iffa.bspace.config.SpaceConfig;
import me.iffa.bspace.config.SpaceConfigUpdater;
import me.iffa.bspace.economy.Economy;
import me.iffa.bspace.gui.PailInterface;
import me.iffa.bspace.handlers.AddonHandler;
import me.iffa.bspace.handlers.ConfigHandler;
import me.iffa.bspace.handlers.LangHandler;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.WorldHandler;
import me.iffa.bspace.listeners.SpaceEconomyListener;
import me.iffa.bspace.listeners.SpaceEntityListener;
import me.iffa.bspace.listeners.SpacePlayerListener;
import me.iffa.bspace.listeners.SpaceSuffocationListener;
import me.iffa.bspace.listeners.misc.BlackHolePlayerListener;
import me.iffa.bspace.listeners.misc.SpaceWorldListener;
import me.iffa.bspace.listeners.spout.SpaceSpoutAreaListener;
import me.iffa.bspace.listeners.spout.SpaceSpoutCraftListener;
import me.iffa.bspace.listeners.spout.SpaceSpoutEntityListener;
import me.iffa.bspace.listeners.spout.SpaceSpoutKeyListener;
import me.iffa.bspace.listeners.spout.SpaceSpoutPlayerListener;
import me.iffa.bspace.wgen.planets.PlanetsChunkGenerator;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of bSpace.
 * 
 * @author iffa
 * @author kitskub
 * @author HACKhalo2
 */
public class Space extends JavaPlugin {
    // Variables
    private static String prefix;
    private static String version;
    private static PailInterface pailInterface;
    private static Map<Player, Location> locCache = null;
    private static Map<Player, Boolean> jumpPressed = new HashMap<Player, Boolean>();
    private PluginManager pm;
    private SpaceCommandHandler sce = null;
    private Economy economy;
    private final SpaceEntityListener entityListener = new SpaceEntityListener();
    private final SpaceWorldListener worldListener = new SpaceWorldListener();
    private final SpacePlayerListener playerListener = new SpacePlayerListener();
    private final SpaceSuffocationListener suffocationListener = new SpaceSuffocationListener(this);
    private final SpaceEconomyListener economyListener = new SpaceEconomyListener();

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        for(SpaceAddon addon : AddonHandler.addons){
            addon.onSpaceDisable();
        }
        // Finishing up disablation.
        MessageHandler.print(Level.INFO, LangHandler.getDisabledMessage());
    }

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        // Initializing variables.
        initVariables();
        MessageHandler.debugPrint(Level.INFO, "Initialized startup variables.");

        // Loading configuration files.
        SpaceConfig.loadConfigs();
        MessageHandler.debugPrint(Level.INFO, "Loaded configuration files, now checking if they need to be updated...");
        // Updating configuration files (if needed).
        SpaceConfigUpdater.updateConfigs();

        // Registering events.
        registerEvents();

        // Loading schematic files.
        SpaceSchematicHandler.loadSchematics();

        // Loading space worlds (startup).
        WorldHandler.loadSpaceWorlds();

        // Initializing the CommandExecutor for /space.
        sce = new SpaceCommandHandler(this);
        getCommand("space").setExecutor(sce);
        MessageHandler.debugPrint(Level.INFO, "Initialized CommandExecutors.");

        // Checking if it should always be night in space worlds.
        for (World world : WorldHandler.getSpaceWorlds()) {
            String id = ConfigHandler.getID(world);
            if (ConfigHandler.forceNight(id)) {
                WorldHandler.startForceNightTask(world);
                MessageHandler.debugPrint(Level.INFO, "Started night forcing task for world '" + world.getName() + "'.");
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
            MessageHandler.debugPrint(Level.INFO, "Starting up the Pail tab.");
            pailInterface = new PailInterface(this);
            ((Pail) pm.getPlugin("Pail")).loadInterfaceComponent("bSpace", pailInterface);
        }

        // Finishing up enablation.
        MessageHandler.print(Level.INFO, LangHandler.getUsageStatsMessage());
        Ping ping = new Ping();
        ping.init(this);
        MessageHandler.print(Level.INFO, LangHandler.getEnabledMessage());
    }

    /**
     * Initializes variables (used on startup).
     */
    private void initVariables() {
        pm = getServer().getPluginManager();
        version = getDescription().getVersion();
        prefix = "[" + getDescription().getName() + "]";
        if (pm.getPlugin("Spout") != null && ConfigHandler.isUsingSpout()) {
            locCache = new HashMap<Player, Location>();
        }
    }

    /**
     * Registers events for bSpace.
     */
    private void registerEvents() {
        // Registering other events.
        //pm.registerEvent(Event.Type.WEATHER_CHANGE, weatherListener, Event.Priority.Highest, this); - removed, see @deprecated tag
        //pm.registerEvent(Event.Type.WORLD_LOAD, worldListener, Event.Priority.Monitor, this);
        pm.registerEvents(worldListener, this);
        MessageHandler.debugPrint(Level.INFO, "Registered events (other).");

        // Registering entity & player events.
        //pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Monitor, this);
        //pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
        //pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.High, this);
        pm.registerEvents(entityListener, this);
        //pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Event.Priority.High, this);
        //pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Monitor, this);
        //pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Monitor, this);
        //pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Monitor, this);
        //pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Monitor, this);
        pm.registerEvents(playerListener, this);
        //pm.registerEvent(Event.Type.CUSTOM_EVENT, suffocationListener, Event.Priority.Monitor, this); //Suffocation Listener
        pm.registerEvents(suffocationListener, this);
        //pm.registerEvent(Event.Type.CUSTOM_EVENT, new SpaceEconomyListener(), Event.Priority.Highest, this); //Economy Listener
        pm.registerEvents(economyListener, this);
        MessageHandler.debugPrint(Level.INFO, "Registered events (entity & player).");

        // Registering events for Spout.
        if (pm.getPlugin("Spout") != null && ConfigHandler.isUsingSpout()) {
            //pm.registerEvent(Event.Type.PLAYER_TELEPORT, new SpaceSpoutPlayerListener(this), Event.Priority.Monitor, this); //Player listener
            //pm.registerEvent(Event.Type.PLAYER_RESPAWN, new SpaceSpoutPlayerListener(this), Event.Priority.Monitor, this); // Player listener
            pm.registerEvents(new SpaceSpoutPlayerListener(this), this);
            //pm.registerEvent(Event.Type.ENTITY_DAMAGE, new SpaceSpoutEntityListener(), Event.Priority.Normal, this); //Entity Listener
            //pm.registerEvent(Event.Type.CREATURE_SPAWN, new SpaceSpoutEntityListener(), Event.Priority.High, this); //Disabled until Limitations in Spout is fixed
            pm.registerEvents(new SpaceSpoutEntityListener(), this);
            //pm.registerEvent(Event.Type.CUSTOM_EVENT, new SpaceSpoutCraftListener(), Event.Priority.High, this); //SpoutCraft Listener
            pm.registerEvents(new SpaceSpoutCraftListener(), this);
            //pm.registerEvent(Event.Type.CUSTOM_EVENT, new SpaceSpoutAreaListener(), Event.Priority.Monitor, this); //Area Listener
            pm.registerEvents(new SpaceSpoutAreaListener(), this);
            //pm.registerEvent(Event.Type.CUSTOM_EVENT, new SpaceSpoutKeyListener(), Event.Priority.Monitor, this); //Key Listener
            pm.registerEvents(new SpaceSpoutKeyListener(), this);
            //pm.registerEvent(Event.Type.PLAYER_MOVE, new BlackHolePlayerListener(), Event.Priority.Monitor, this);
            pm.registerEvents(new BlackHolePlayerListener(), this);
            MessageHandler.debugPrint(Level.INFO, "Registered events (Spout).");
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
        boolean realID=true;
        if(id == null || id.isEmpty() || id.length() == 0){
            realID=false;
        }
        if(realID){
            MessageHandler.debugPrint(Level.INFO, "Getting generator for '" + worldName + "' using id: '" + id + "'");
        }else{
            MessageHandler.debugPrint(Level.INFO, "Getting generator for '" + worldName + "' using default id,planets.");
        }
        WorldHandler.checkWorld(worldName);
        if (!realID) {
            return new PlanetsChunkGenerator("planets");
        }
        //TODO check if id is in ids.yml
        return new PlanetsChunkGenerator(id);
    }

    /* Some API methods */

    /**
     * Gets the jump pressed value. (ie = wtf is this)
     * 
     * @param player Player
     * 
     * @return Jump pressed
     */
    public static boolean getJumpPressed(Player player) {
        return jumpPressed.get(player);
    }
    
    /**
     * Sets the jump pressed value. (ie = wtf is this ??)
     * 
     * @param player Player
     * @param newJumpPressed New jump pressed value
     */
    public static void setJumpPressed(Player player, boolean newJumpPressed) {
        jumpPressed.put(player, newJumpPressed);
    }
    
    /**
     * Gets the location cache.
     * 
     * @return Location cach
     */
    public static Map<Player, Location> getLocCache() {
        return locCache;
    }
    
    /**
     * Gets the plugin's prefix.
     * 
     * @return Prefix
     */
    public static String getPrefix() {
        return prefix;
    }
    
    /**
     * Gets the plugin's version.
     * 
     * @return Version
     */
    public static String getVersion() {
        return version;
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