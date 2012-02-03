// Package Declaration
package me.iffa.bspace.api;

// bSpace Imports
import me.iffa.bspace.config.SpaceConfig.Defaults;
import me.iffa.bspace.config.SpaceConfig;
import me.iffa.bspace.config.SpaceConfig.ConfigFile;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.WorldHandler;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.Bukkit;

// Java Imports
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Material;

/**
 * Static methods handle configuration.
 * External use only
 * 
 * @author iffa
 * @author Jack
 */
public class SpaceConfigHandler {
    
    /**
     * @see WorldHandler#getID(org.bukkit.World)
     * 
     * @param world
     * @return @see(WorldHandler.java)
     */
    public static String getID(World world){
        return WorldHandler.getID(world);
    }
    //Global
    
    /**
     * Checks if debugging mode is enabled.
     * 
     * @return true if debugging mode is enabled
     */
    public static boolean getDebugging() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("debug", (Boolean) Defaults.DEBUGGING.getDefault());
    }
    
    /**
     * Gets the helmet given-state of a world.
     * 
     * @return true if a helmet is given when teleporting to this world
     */
    public static boolean isHelmetGiven() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.givehelmet", (Boolean) Defaults.HELMET_GIVEN.getDefault());
    }

    /**
     * Gets the suit given-state of a world.
     * 
     * @return true if a suit is given when teleporting to this world
     */
    public static boolean isSuitGiven() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.givesuit", (Boolean) Defaults.SUIT_GIVEN.getDefault());
    }
    
    /**
     * Gets the helmet blockid of a world.
     * 
     * @return block id integer
     */
    public static int getHelmetBlock() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getInt("global.blockid", (Integer) Defaults.HELMET_BLOCK.getDefault());
    }

    /**
     * Gets the suit armortype of a world.
     * 
     * @return armortype string
     */
    public static String getArmorType() {
        String armorType = SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.armortype", (String) Defaults.ARMOR_TYPE.getDefault());
        if(Material.matchMaterial(armorType+ "_HELMET")==null){
            MessageHandler.print(Level.SEVERE, "Invalid armortype '" + SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.armortype") + "' in config!");
            return (String) Defaults.ARMOR_TYPE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.armortype", (String) Defaults.ARMOR_TYPE.getDefault());
    }
     
    /**
     * Checks if Spout will be used.
     * 
     * @return true if Spout is used
     */
    public static boolean isUsingSpout() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.spout.use", (Boolean) Defaults.USE_SPOUT.getDefault());
    }
   
    /**
     * Gets the spout texture pack url.
     * 
     * @return Url of the texture used in the config, or the default
     */
    public static String getSpoutTexturePack() {
        String texture = SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.spout.texturepack", (String) Defaults.TEXTURE_PACK.getDefault());
        try {
            URL url = new URL(texture);
            URI toURI = url.toURI();
            url.openConnection();
        } catch (MalformedURLException ex) {
        } catch (URISyntaxException ex) {
        } catch (IOException ex){
        }
        return texture.isEmpty() ? (String) Defaults.TEXTURE_PACK.getDefault() : texture;
    }

    /**
     * Gets the use texture pack value.
     * 
     * @return True if using the texture pack
     */
    public static boolean getUseTexturePack() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.spout.usetp", (Boolean) Defaults.USE_TEXTURE_PACK.getDefault());
    }

    /**
     * Gets the clouds enabled value.
     * 
     * @return True if clouds enabled
     */
    public static boolean getCloudsEnabled() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.spout.clouds", (Boolean) Defaults.CLOUDS.getDefault());
    }
    
    /**
     * Gets the gravity value.
     * 
     * @return True if gravity enabled
     */
    public static boolean getGravity() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.spout.gravity", (Boolean) Defaults.GRAVITY.getDefault());
    }
    
    /**
     * Gets the gravity value.
     * 
     * @return True if gravity enabled
     */
    public static boolean getStopDrowning() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.drowning.stopdrowning", (Boolean) Defaults.STOPDROWNING.getDefault());
    }
    
    /**
     * Gets the gravity value.
     * 
     * @return True if gravity enabled
     */
    public static List<World> getStopDrowningWorlds() {
        @SuppressWarnings("unchecked")
        List<String> strings = SpaceConfig.getConfig(ConfigFile.CONFIG).getStringList("global.drowning.worlds");
        List<World> worlds = new ArrayList<World>();
        for(String string : strings){
            worlds.add(Bukkit.getWorld(string));
        }
        return worlds;
    }

    //ID-specific
    
    /**
     * Gets the required helmet-state of a world.
     * 
     * @param id Id
     * 
     * @return true if a helmet is required
     */
    public static boolean getRequireHelmet(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.REQUIRE_HELMET.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".helmet.required", (Boolean) Defaults.REQUIRE_HELMET.getDefault());
    }

    /**
     * Gets the required suit-state of a world.
     * 
     * @param id ID
     * 
     * @return true if a suit is required
     */
    public static boolean getRequireSuit(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.REQUIRE_SUIT.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".suit.required", (Boolean) Defaults.REQUIRE_SUIT.getDefault());
    }

    /**
     * Checks if hostile mobs are allowed in a world.
     * 
     * @param id ID
     * 
     * @return true if hostile mobs are allowed
     */
    public static boolean allowHostileMobs(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.HOSTILE_MOBS_ALLOWED.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".hostilemobs", (Boolean) Defaults.HOSTILE_MOBS_ALLOWED.getDefault());
    }

    /**
     * Checks if neutral mobs are allowed in a world.
     * 
     * @param id ID
     * 
     * @return true if neutral mobs are allowed
     */
    public static boolean allowNeutralMobs(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.NEUTRAL_MOBS_ALLOWED.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".neutralmobs", (Boolean) Defaults.NEUTRAL_MOBS_ALLOWED.getDefault());
    }

    /**
     * Gets the force night-state of a world.
     * 
     * @param id ID
     * 
     * @return true if night is forced
     */
    public static boolean forceNight(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.FORCE_NIGHT.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".alwaysnight", (Boolean) Defaults.FORCE_NIGHT.getDefault());
    }

    /**
     * Gets the maximum room height of a world.
     * 
     * @param id Id
     * 
     * @return room height int
     */
    public static int getRoomHeight(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Integer) Defaults.ROOM_HEIGHT.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".maxroomheight", (Integer) Defaults.ROOM_HEIGHT.getDefault());
    }

    /**
     * Gets the weather allowed-state of a world.
     * 
     * @param id Id
     * 
     * @return true if weather is allowed
     * @deprecated No longer needed
     */
    @Deprecated
    public static boolean allowWeather(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.ALLOW_WEATHER.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".weather", (Boolean) Defaults.ALLOW_WEATHER.getDefault());
    }

    /**
     * Gets the glowstone chance of a world.
     * 
     * @param id Id
     * 
     * @return glowstone chance int
     */
    public static int getGlowstoneChance(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Integer) Defaults.GLOWSTONE_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.glowstonechance", (Integer) Defaults.GLOWSTONE_CHANCE.getDefault());
    }

    /**
     * Gets the stone chance of a world.
     * 
     * @param id ID
     * @return asteroid chance int
     */
    public static int getStoneChance(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Integer) Defaults.STONE_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.stonechance", (Integer) Defaults.STONE_CHANCE.getDefault());
    }

    /**
     * Checks if asteroid generation is enabled for a world.
     * 
     * @param id 
     * @return true if asteroid generation is enabled
     */
    public static boolean getAsteroidsEnabled(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.ASTEROIDS_ENABLED.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generateasteroids", (Boolean) Defaults.ASTEROIDS_ENABLED.getDefault());
    }

    /**
     * Checks if satellites are enabled.
     * 
     * @param id ID
     * 
     * @return True if satellites are enabled
     */
    public static boolean getSatellitesEnabled(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.SATELLITES_ENABLED.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generatesatellites", (Boolean) Defaults.SATELLITES_ENABLED.getDefault());
    }

    /**
     * Gets the satellite spawn chance.
     * 
     * @param id ID
     * 
     * @return Spawn chance
     */
    public static int getSatelliteChance(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Integer) Defaults.SATELLITE_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.satellitechance", (Integer) Defaults.SATELLITE_CHANCE.getDefault());
    }

    /**
     * Gets generate planets value.
     * 
     * @param id ID
     * 
     * @return True if generateplantes=true
     */
    public static boolean getGeneratePlanets(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.GENERATE_PLANETS.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generateplanets", (Boolean) Defaults.GENERATE_PLANETS.getDefault());
    }

    /**
     * Gets generate schematics value.
     * 
     * @param id ID
     * 
     * @return True if generateschematics=true
     */
    public static boolean getGenerateSchematics(String id) {
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generateschematics", (Boolean) Defaults.GENERATE_SCHEMATICS.getDefault());
    }

    /**
     * Gets the schematic-chance.
     * 
     * @param id ID
     * @return Schematic chance
     */
    public static int getSchematicChance(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Integer) Defaults.SCHEMATIC_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.schematicchance", (Integer) Defaults.SCHEMATIC_CHANCE.getDefault());
    }

    /**
     * Gets the genrate black holes value.
     * 
     * @param id ID
     * @return True if generating black holes
     */
    public static boolean getGenerateBlackHoles(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) Defaults.BLACKHOLES.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.spout-only.blackholes", (Boolean) Defaults.BLACKHOLES.getDefault());
    }
    
    /**
     * Gets the black hole chance.
     * 
     * @param id ID
     * @return Black hole chance
     */
    public static int getBlackHoleChance(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Integer) Defaults.BLACKHOLE_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.blackholechance", (Integer) Defaults.BLACKHOLE_CHANCE.getDefault());
    }
    
    /**
     * Constructor of SpaceConfigHandler.
     */
    protected SpaceConfigHandler() {
    }
}
