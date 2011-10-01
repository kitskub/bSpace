// Package Declaration
package me.iffa.bananaspace.api;

// BananaSpace Imports
import me.iffa.bananaspace.config.SpaceConfig;

// Bukkit Imports
import org.bukkit.World;

/**
 * Static methods to use internally (and externally, why not?) to handle world-specific configuration.
 * 
 * @author iffa
 */
public class SpaceConfigHandler {
    /**
     * Checks if debugging mode is enabled.
     * 
     * @return true if debugging mode is enabled
     */
    public static boolean getDebugging() {
        return SpaceConfig.getConfig().getBoolean("debug", true);
    }
    /**
     * Gets the required helmet-state of a world.
     * 
     * @param world World
     * 
     * @return true if a helmet is required
     */
    public static boolean getRequireHelmet(World world) {
        return SpaceConfig.getConfig().getBoolean("worlds." + world.getName() + ".helmet.required", true);
    }

    /**
     * Gets the required suit-state of a world.
     * 
     * @param world World
     * 
     * @return true if a suit is required
     */
    public static boolean getRequireSuit(World world) {
        return SpaceConfig.getConfig().getBoolean("worlds." + world.getName() + ".suit.required", true);
    }

    /**
     * Gets the helmet given-state of a world.
     * 
     * @return true if a helmet is given when teleporting to this world
     */
    public static boolean isHelmetGiven() {
        return SpaceConfig.getConfig().getBoolean("global.givehelmet", true);
    }

    /**
     * Gets the suit given-state of a world.
     * 
     * @return true if a suit is given when teleporting to this world
     */
    public static boolean isSuitGiven() {
        return SpaceConfig.getConfig().getBoolean("global.givesuit", true);
    }

    /**
     * Checks if hostile mobs are allowed in a world.
     * 
     * @param world World
     * 
     * @return true if hostile mobs are allowed
     */
    public static boolean allowHostileMobs(World world) {
        return SpaceConfig.getConfig().getBoolean("worlds." + world.getName() + ".hostilemobs", false);
    }

    /**
     * Checks if neutral mobs are allowed in a world.
     * 
     * @param world World
     * 
     * @return true if neutral mobs are allowed
     */
    public static boolean allowNeutralMobs(World world) {
        return SpaceConfig.getConfig().getBoolean("worlds." + world.getName() + ".neutralmobs", true);
    }

    /**
     * Gets the force night-state of a world.
     * 
     * @param world World
     * 
     * @return true if night is forced
     */
    public static boolean forceNight(World world) {
        return SpaceConfig.getConfig().getBoolean("worlds." + world.getName() + ".alwaysnight", true);
    }

    /**
     * Gets the helmet blockid of a world.
     * 
     * @return block id integer
     */
    public static int getHelmetBlock() {
        return SpaceConfig.getConfig().getInt("global.blockid", 86);
    }

    /**
     * Gets the suit armortype of a world.
     * 
     * @return armortype string
     */
    public static String getArmorType() {
        return SpaceConfig.getConfig().getString("global.armortype", "iron");
    }

    /**
     * Gets the maximum room height of a world.
     * 
     * @param world World
     * 
     * @return room height int
     */
    public static int getRoomHeight(World world) {
        return SpaceConfig.getConfig().getInt("worlds." + world.getName() + ".breathingarea.maxroomheight", 5);
    }

    /**
     * Gets the weather allowed-state of a world.
     * 
     * @param world World
     * 
     * @return true if weather is allowed
     */
    public static boolean allowWeather(World world) {
        return SpaceConfig.getConfig().getBoolean("worlds." + world.getName() + ".weather", false);
    }

    /**
     * Gets the glowstone chance of a world.
     * 
     * @param world World
     * 
     * @return glowstone chance int
     */
    public static int getGlowstoneChance(World world) {
        return SpaceConfig.getConfig().getInt("worlds." + world.getName() + ".generation.glowstonechance", 1);
    }
    
    /**
     * Gets the glowstone chance of a world.
     * 
     * @param world World
     * 
     * @return asteroid chance int
     */
    public static int getStoneChance(World world) {
        return SpaceConfig.getConfig().getInt("worlds." + world.getName() + ".generation.stonechance", 3);
    }
    
    /**
     * Checks if asteroid generation is enabled for a world.
     * 
     * @param world World
     * 
     * @return true if asteroid generation is enabled
     */
    public static boolean getAsteroidsEnabled(World world) {
        return SpaceConfig.getConfig().getBoolean("worlds" + world.getName() + ".generation.generateasteroids", true);
    }
    /**
     * Checks if Spout will be used.
     * 
     * @return true if Spout is used
     */
    public static boolean isUsingSpout() {
        return SpaceConfig.getConfig().getBoolean("global.usespout", true);
    }
    
    /**
     * Checks if a world is in the configuration file.
     * 
     * @param name Name of the world
     * @return True if the world is in the config file
     */
    public static boolean isWorldInConfig(String name){
        if (SpaceConfig.getConfig().getKeys("worlds." + name)!=null) {
            return true;
        }
        return false;
    }
    
    public static boolean getSatellitesEnabled(World world) {
        return SpaceConfig.getConfig().getBoolean("worlds." + world.getName() + ".generation.generatesatellites", true);
    }
    
    public static int getSatelliteChance(World world) {
        return SpaceConfig.getConfig().getInt("worlds." + world.getName() + ".generation.satellitechance", 5);
    }

    /**
     * Constructor of SpaceConfigHandler.
     */
    private SpaceConfigHandler() {
    }
}
