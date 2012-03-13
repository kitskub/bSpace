// Package Declaration
package me.iffa.bspace.api;

// bSpace Imports
import static me.iffa.bspace.config.SpaceConfig.Defaults.*;
import me.iffa.bspace.config.SpaceConfig;
import me.iffa.bspace.config.SpaceConfig.ConfigFile;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.WorldHandler;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Material;

// Java Imports
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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
     * @param world World
     *
     * @return ID of world
     *
     * @see(WorldHandler.java)
     */
    public static String getID(World world) {
        return WorldHandler.getID(world);
    }
    //Global

    /**
     * Checks if debugging mode is enabled.
     *
     * @return true if debugging mode is enabled
     */
    public static boolean getDebugging() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("debug", (Boolean) DEBUGGING.getDefault());
    }

    /**
     * Gets the helmet given-state of a world.
     *
     * @return true if a helmet is given when teleporting to this world
     */
    public static boolean isHelmetGiven() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.givehelmet", (Boolean) HELMET_GIVEN.getDefault());
    }

    /**
     * Gets the suit given-state of a world.
     *
     * @return true if a suit is given when teleporting to this world
     */
    public static boolean isSuitGiven() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.givesuit", (Boolean) SUIT_GIVEN.getDefault());
    }

    /**
     * Gets the helmet blockid of a world.
     *
     * @return block id integer
     */
    public static int getHelmetBlock() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getInt("global.blockid", (Integer) HELMET_BLOCK.getDefault());
    }

    /**
     * Gets the suit armortype of a world.
     *
     * @return armortype string
     */
    public static String getArmorType() {
        String armorType = SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.armortype", (String) ARMOR_TYPE.getDefault());
        if (Material.matchMaterial(armorType + "_HELMET") == null) {
            MessageHandler.print(Level.SEVERE, "Invalid armortype '" + SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.armortype") + "' in config!");
            return (String) ARMOR_TYPE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.armortype", (String) ARMOR_TYPE.getDefault());
    }

    /**
     * Checks if Spout will be used.
     *
     * @return true if Spout is used
     */
    public static boolean isUsingSpout() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.spout.use", (Boolean) USE_SPOUT.getDefault());
    }

    /**
     * Gets the spout texture pack url.
     *
     * @return Url of the texture used in the config, or the default
     */
    public static String getSpoutTexturePack() {
        String texture = SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.spout.texturepack", (String) TEXTURE_PACK.getDefault());
        try {
            URL url = new URL(texture);
            URI toURI = url.toURI();
            url.openConnection();
        } catch (Exception ex) {
            MessageHandler.debugPrint(Level.WARNING, "Failed to get texture pack from URL: " + ex.toString());
        }
        return texture.isEmpty() ? (String) TEXTURE_PACK.getDefault() : texture;
    }

    public static String getBlackHoleTexture() {
        String texture = SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.spout.blackhole-texture", (String) BLACKHOLE_TEXTURE.getDefault());
        try {
            URL url = new URL(texture);
            URI toURI = url.toURI();
            url.openConnection();
        } catch (Exception ex) {
            MessageHandler.debugPrint(Level.WARNING, "Failed to get black hole texture from URL: " + ex.toString());
        }
        return texture.isEmpty() ? (String) BLACKHOLE_TEXTURE.getDefault() : texture;
    }

    /**
     * Gets the use texture pack value.
     *
     * @return True if using the texture pack
     */
    public static boolean getTexturePackEnabled() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.spout.usetp", (Boolean) USE_TEXTURE_PACK.getDefault());
    }

    /**
     * Gets the clouds enabled value.
     *
     * @return True if clouds enabled
     */
    public static boolean getCloudsEnabled() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.spout.clouds", (Boolean) CLOUDS.getDefault());
    }

    /**
     * Gets the gravity value.
     *
     * @return True if gravity enabled
     */
    public static boolean getGravityEnabled() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.spout.gravity", (Boolean) GRAVITY.getDefault());
    }

    /**
     * Gets the gravity value.
     *
     * @return True if gravity enabled
     */
    public static boolean getStopDrowning() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.drowning.stopdrowning", (Boolean) STOPDROWNING.getDefault());
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
        for (String string : strings) {
            worlds.add(Bukkit.getWorld(string));
        }
        return worlds;
    }

    //ID-specific
    /**
     * Gets the name of the planets file. Not checked if it exists.
     *
     * @param id Id
     *
     * @return name of planets file
     */
    public static String getPlanetsFile(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return "planets.yml";//Never going to change; unless of course someone demands it
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getString("ids." + id + ".generation.planets-file", "planets.yml");
    }

    /**
     * Gets the required helmet-state of a world.
     *
     * @param id Id
     *
     * @return true if a helmet is required
     */
    public static boolean getRequireHelmet(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) REQUIRE_HELMET.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".helmet.required", (Boolean) REQUIRE_HELMET.getDefault());
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
            return (Boolean) REQUIRE_SUIT.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".suit.required", (Boolean) REQUIRE_SUIT.getDefault());
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
            return (Boolean) FORCE_NIGHT.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".alwaysnight", (Boolean) FORCE_NIGHT.getDefault());
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
            return (Integer) ROOM_HEIGHT.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".maxroomheight", (Integer) ROOM_HEIGHT.getDefault());
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
            return (Integer) GLOWSTONE_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.glowstonechance", (Integer) GLOWSTONE_CHANCE.getDefault());
    }

    /**
     * Gets the stone chance of a world.
     *
     * @param id ID
     *
     * @return asteroid chance int
     */
    public static int getStoneChance(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Integer) STONE_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.stonechance", (Integer) STONE_CHANCE.getDefault());
    }

    /**
     * Checks if asteroid generation is enabled for a world.
     *
     * @param id
     *
     * @return true if asteroid generation is enabled
     */
    public static boolean getAsteroidsEnabled(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Boolean) ASTEROIDS_ENABLED.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generateasteroids", (Boolean) ASTEROIDS_ENABLED.getDefault());
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
            return (Boolean) SATELLITES_ENABLED.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generatesatellites", (Boolean) SATELLITES_ENABLED.getDefault());
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
            return (Integer) SATELLITE_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.satellitechance", (Integer) SATELLITE_CHANCE.getDefault());
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
            return (Boolean) GENERATE_PLANETS.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generateplanets", (Boolean) GENERATE_PLANETS.getDefault());
    }

    /**
     * Gets generate schematics value.
     *
     * @param id ID
     *
     * @return True if generateschematics=true
     */
    public static boolean getGenerateSchematics(String id) {
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generateschematics", (Boolean) GENERATE_SCHEMATICS.getDefault());
    }

    /**
     * Gets the schematic-chance.
     *
     * @param id ID
     *
     * @return Schematic chance
     */
    public static int getSchematicChance(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Integer) SCHEMATIC_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.schematicchance", (Integer) SCHEMATIC_CHANCE.getDefault());
    }

    /**
     * Gets the generate black holes with spout value.
     *
     * @param id ID
     *
     * @return True if generating black holes with spout
     */
    public static boolean getGenerateBlackHolesSpout(String id) {
        if (id.equalsIgnoreCase("planets")  ) {
            return (Boolean) SPOUT_BLACKHOLES.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.spoutblackholes", (Boolean) SPOUT_BLACKHOLES.getDefault());
    }

     /**
     * Gets the generate black holes without spout value.
     *
     * @param id ID
     *
     * @return True if generating black holes without spout
     */
    public static boolean getGenerateBlackHolesNonSpout(String id) {
        if (id.equalsIgnoreCase("planets")  ) {
            return (Boolean) NONSPOUT_BLACKHOLES.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.nonspoutblackholes", (Boolean) SPOUT_BLACKHOLES.getDefault());
    }

    /**
     * Gets the black hole chance.
     *
     * @param id ID
     *
     * @return Black hole chance
     */
    public static int getBlackHoleChance(String id) {
        if (id.equalsIgnoreCase("planets")) {
            return (Integer) BLACKHOLE_CHANCE.getDefault();
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.blackholechance", (Integer) BLACKHOLE_CHANCE.getDefault());
    }

    /**
     * Constructor of SpaceConfigHandler.
     */
    protected SpaceConfigHandler() {
    }
}
