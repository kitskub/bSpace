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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.generator.ChunkGenerator;

//Permissions (Nijiko)
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

// Pail Imports
import java.io.File;
import me.escapeNT.pail.Pail;
import org.bukkit.Bukkit;
import org.bukkit.util.config.Configuration;

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
    public static PermissionHandler permissionHandler;
    private SpaceCommand sce = null;
    public static SpaceWorldHandler worldHandler;
    public static SpacePlayerHandler playerHandler;
    public static PailInterface pailInt;
    SpaceConfig cMgr = new SpaceConfig();
    SpacePlanetConfig cplaMgr = new SpacePlanetConfig();
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
        PluginManager pm = getServer().getPluginManager();
        worldHandler = new SpaceWorldHandler(this);
        playerHandler = new SpacePlayerHandler();

        // Loading configuration files
        cMgr.loadConfig();
        cplaMgr.loadConfig();

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

        // Registering events for Spout
        if (pm.getPlugin("Spout") != null && SpaceConfigHandler.isUsingSpout()) {
            pm.registerEvent(Event.Type.PLAYER_TELEPORT, spListener, Event.Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_JOIN, spListener, Event.Priority.Normal, this);
        }

        // Creating all space worlds.
        worldHandler.createSpaceWorlds();

        // Initializing the CommandExecutor
        sce = new SpaceCommand(this);
        getCommand("space").setExecutor(sce);

        // Checking if it should always be night in space
        scheduler = getServer().getScheduler();
        for (World world : worldHandler.getSpaceWorlds()) {
            if (SpaceConfigHandler.forceNight(world)) {
                worldHandler.startForceNightTask(world);
            }
        }

        // Setting up Permissions (Nijiko)
        setupPermissions();

        // Pail
        if (pm.getPlugin("Pail") != null) {
            pailInt = new PailInterface(this);
            ((Pail) pm.getPlugin("Pail")).loadInterfaceComponent("BananaSpace", pailInt);
        }

        log.info(prefix + " Enabled version " + version);
    }

    /**
     * Sets up Permissions (Nijiko)
     */
    private void setupPermissions() {
        if (permissionHandler != null) {
            return;
        }
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (permissionsPlugin == null) {
            return;
        }
        permissionHandler = ((Permissions) permissionsPlugin).getHandler();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        Configuration myConfig = null;
        File configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder(), "config.yml");
        if (configFile.exists()) {
            myConfig = new Configuration(configFile) {};
            myConfig.load();
        }
        else {
            try {
                Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder().mkdir();
                SpaceConfig.copyFile(getClass().getResourceAsStream("/config.yml"), configFile);
                myConfig = new Configuration(configFile);
                myConfig.load();
                configFile.delete();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        if (myConfig.getBoolean("worlds." + worldName + ".generation.generateplanets", true)) {
            return new PlanetsChunkGenerator(myConfig, this);
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
