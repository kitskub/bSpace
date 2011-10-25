// Package Declaration
package me.iffa.bananaspace.api;

// BananaSpace Imports
import me.iffa.bananaspace.config.SpaceConfig;
import me.iffa.bananaspace.config.SpaceConfig.ConfigFile;
import me.iffa.bananaspace.wgen.planets.PlanetsChunkGenerator;

// Bukkit Imports
import org.bukkit.World;

/**
 * Static methods to use internally (and externally, why not?) to handle world-specific configuration.
 * 
 * @author iffa
 * @author Jack
 */
public class SpaceConfigHandler {
    /**
     * Checks if debugging mode is enabled.
     * 
     * @return true if debugging mode is enabled
     */
    public static boolean getDebugging() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("debug", true);
    }
    /**
     * Gets the required helmet-state of a world.
     * 
     * @param world World
     * 
     * @return true if a helmet is required
     */
    public static boolean getRequireHelmet(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getRequireHelmet(theGen.ID);
        }
        return false;
    }
    /**
     * Gets the required helmet-state of a world.
     * 
     * @param id Id
     * 
     * @return true if a helmet is required
     */
    public static boolean getRequireHelmet(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return false;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".helmet.required", false);
    }

    /**
     * Gets the required suit-state of a world.
     * 
     * @param world World
     * 
     * @return true if a suit is required
     */
    public static boolean getRequireSuit(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getRequireSuit(theGen.ID);
        }
        return false;
}
    /**
     * Gets the required suit-state of a world.
     * 
     * @param id 
     * @return true if a suit is required
     */
    public static boolean getRequireSuit(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return false;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".suit.required", true);
    }
    /**
     * Gets the helmet given-state of a world.
     * 
     * @return true if a helmet is given when teleporting to this world
     */
    public static boolean isHelmetGiven() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.givehelmet", true);
    }

    /**
     * Gets the suit given-state of a world.
     * 
     * @return true if a suit is given when teleporting to this world
     */
    public static boolean isSuitGiven() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.givesuit", true);
    }

    /**
     * Checks if hostile mobs are allowed in a world.
     * 
     * @param world World
     * 
     * @return true if hostile mobs are allowed
     */
    public static boolean allowHostileMobs(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return allowHostileMobs(theGen.ID);
        }
        return false;
    }
    
    /**
     * Checks if hostile mobs are allowed in a world.
     * 
     * @param id 
     * @return true if hostile mobs are allowed
     */
    public static boolean allowHostileMobs(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return false;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".hostilemobs", false);
    }
    
    /**
     * Checks if neutral mobs are allowed in a world.
     * 
     * @param world World
     * 
     * @return true if neutral mobs are allowed
     */
    public static boolean allowNeutralMobs(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return allowNeutralMobs(theGen.ID);
        }
        return true;
    }
    
    /**
     * Checks if neutral mobs are allowed in a world.
     * 
     * @param id 
     * @return true if neutral mobs are allowed
     */
    public static boolean allowNeutralMobs(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return true;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".neutralmobs", true);
    }

    /**
     * Gets the force night-state of a world.
     * 
     * @param world World
     * 
     * @return true if night is forced
     */
    public static boolean forceNight(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return forceNight(theGen.ID);
        }
        return true;
    }
    
    /**
     * Gets the force night-state of a world.
     * 
     * @param id 
     * @return true if night is forced
     */
    public static boolean forceNight(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return true;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".alwaysnight", true);
    }

    /**
     * Gets the helmet blockid of a world.
     * 
     * @return block id integer
     */
    public static int getHelmetBlock() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getInt("global.blockid", 86);
    }

    /**
     * Gets the suit armortype of a world.
     * 
     * @return armortype string
     */
    public static String getArmorType() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.armortype", "iron");
    }

    /**
     * Gets the maximum room height of a world.
     * 
     * @param world World
     * 
     * @return room height int
     */
    public static int getRoomHeight(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getRoomHeight(theGen.ID);
        }
        return 5;
    }

    /**
     * Gets the maximum room height of a world.
     * 
     * @param id Id
     * 
     * @return room height int
     */
    public static int getRoomHeight(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return 5;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("idss." + id + ".breathingarea.maxroomheight", 5);
    }
    
    /**
     * Gets the weather allowed-state of a world.
     * 
     * @param world World
     * 
     * @return true if weather is allowed
     */
    public static boolean allowWeather(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return allowWeather(theGen.ID);
        }
        return false;
    }
    
        /**
     * Gets the weather allowed-state of a world.
     * 
     * @param id Id
     * 
     * @return true if weather is allowed
     */
    public static boolean allowWeather(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return false;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".weather", false);
    }

    /**
     * Gets the glowstone chance of a world.
     * 
     * @param world World
     * 
     * @return glowstone chance int
     */
    public static int getGlowstoneChance(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getGlowstoneChance(theGen.ID);
        }
        return 1;
    }
    
    /**
     * Gets the glowstone chance of a world.
     * 
     * @param id Id
     * 
     * @return glowstone chance int
     */
    public static int getGlowstoneChance(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return 1;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.glowstonechance", 1);
    }
    
    /**
     * Gets the stone chance of a world.
     * 
     * @param world World
     * 
     * @return asteroid chance int
     */
    public static int getStoneChance(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getStoneChance(theGen.ID);
        }
        return 3;
    }
    
    /**
     * Gets the stone chance of a world.
     * 
     * @param id 
     * @return asteroid chance int
     */
    public static int getStoneChance(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return 3;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.stonechance", 3);
    }
    
    
    /**
     * Checks if asteroid generation is enabled for a world.
     * 
     * @param world World
     * 
     * @return true if asteroid generation is enabled
     */
    public static boolean getAsteroidsEnabled(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getAsteroidsEnabled(theGen.ID);
        }
        return true;
    }
    
    /**
     * Checks if asteroid generation is enabled for a world.
     * 
     * @param id 
     * @return true if asteroid generation is enabled
     */
    public static boolean getAsteroidsEnabled(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return true;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids" + id + ".generation.generateasteroids", true);
    }
    
    /**
     * Checks if Spout will be used.
     * 
     * @return true if Spout is used
     */
    public static boolean isUsingSpout() {
       return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.usespout", true);
    }
    
    /**
     * Checks if a world is in the configuration file.
     * 
     * @param name Name of the world
     * 
     * @return True if the world is in the config file
     * @deprecated Don't see why this is needed, will probably go away VERY soon.
     */
    public static boolean isWorldInConfig(String name){
        if (SpaceConfig.getConfig(ConfigFile.CONFIG).get("worlds." + name) != null) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if satellites are enabled.
     * 
     * @param world World
     * 
     * @return True if satellites are enabled
     */
    public static boolean getSatellitesEnabled(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getSatellitesEnabled(theGen.ID);
        }
        return true;
    }

    /**
     * Checks if satellites are enabled.
     * 
     * @param id 
     * @return True if satellites are enabled
     */
    public static boolean getSatellitesEnabled(String id) {
        if(id.equalsIgnoreCase("planets")) {
            return true;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generatesatellites", true);
    }

    /**
     * Gets the satellite spawn chance.
     * 
     * @param world World
     * 
     * @return Spawn chance
     */
    public static int getSatelliteChance(World world) {
        if(world.getGenerator() instanceof PlanetsChunkGenerator){
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getSatelliteChance(theGen.ID);
        }
        return 1;
    }

    /**
     * Gets the satellite spawn chance.
     * 
     * @param id 
     * @return Spawn chance
     */
    public static int getSatelliteChance(String id) {
        if(id.equalsIgnoreCase("planets")) return 1;
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.satellitechance", 1);
    }

    /**
     * Constructor of SpaceConfigHandler.
     */
    private SpaceConfigHandler() {
    }
}
