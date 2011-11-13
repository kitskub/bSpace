// Package Declaration
package me.iffa.bananaspace.api;

// BananaSpace Imports
import me.iffa.bananaspace.config.SpaceConfig.Defaults;
import me.iffa.bananaspace.config.SpaceConfig;
import me.iffa.bananaspace.config.SpaceConfig.ConfigFile;
import me.iffa.bananaspace.wgen.planets.PlanetsChunkGenerator;

// Bukkit Imports
import org.bukkit.World;

// Java Imports
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("debug", (Boolean) Defaults.DEBUGGING.getDefault());
    }

    /**
     * Gets the required helmet-state of a world.
     * 
     * @param world World
     * 
     * @return true if a helmet is required
     */
    public static boolean getRequireHelmet(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
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
        if (id.equalsIgnoreCase("planets")) {
            return false;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".helmet.required", (Boolean) Defaults.REQUIRE_HELMET.getDefault());
    }

    /**
     * Gets the required suit-state of a world.
     * 
     * @param world World
     * 
     * @return true if a suit is required
     */
    public static boolean getRequireSuit(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getRequireSuit(theGen.ID);
        }
        return false;
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
            return false;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".suit.required", (Boolean) Defaults.REQUIRE_SUIT.getDefault());
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
     * Checks if hostile mobs are allowed in a world.
     * 
     * @param world World
     * 
     * @return true if hostile mobs are allowed
     */
    public static boolean allowHostileMobs(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return allowHostileMobs(theGen.ID);
        }
        return false;
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
            return false;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".hostilemobs", (Boolean) Defaults.HOSTILE_MOBS_ALLOWED.getDefault());
    }

    /**
     * Checks if neutral mobs are allowed in a world.
     * 
     * @param world World
     * 
     * @return true if neutral mobs are allowed
     */
    public static boolean allowNeutralMobs(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return allowNeutralMobs(theGen.ID);
        }
        return true;
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
            return true;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".neutralmobs", (Boolean) Defaults.NEUTRAL_MOBS_ALLOWED.getDefault());
    }

    /**
     * Gets the force night-state of a world.
     * 
     * @param world World
     * 
     * @return true if night is forced
     */
    public static boolean forceNight(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return forceNight(theGen.ID);
        }
        return true;
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
            return true;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".alwaysnight", (Boolean) Defaults.FORCE_NIGHT.getDefault());
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
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.armortype", (String) Defaults.ARMOR_TYPE.getDefault());
    }

    /**
     * Gets the maximum room height of a world.
     * 
     * @param world World
     * 
     * @return room height int
     */
    public static int getRoomHeight(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
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
        if (id.equalsIgnoreCase("planets")) {
            return 5;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".maxroomheight", (Integer) Defaults.ROOM_HEIGHT.getDefault());
    }

    /**
     * Gets the weather allowed-state of a world.
     * 
     * @param world World
     * 
     * @return true if weather is allowed
     */
    public static boolean allowWeather(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
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
        if (id.equalsIgnoreCase("planets")) {
            return false;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".weather", (Boolean) Defaults.ALLOW_WEATHER.getDefault());
    }

    /**
     * Gets the glowstone chance of a world.
     * 
     * @param world World
     * 
     * @return glowstone chance int
     */
    public static int getGlowstoneChance(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
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
        if (id.equalsIgnoreCase("planets")) {
            return 1;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.glowstonechance", (Integer) Defaults.GLOWSTONE_CHANCE.getDefault());
    }

    /**
     * Gets the stone chance of a world.
     * 
     * @param world World
     * 
     * @return asteroid chance int
     */
    public static int getStoneChance(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return getStoneChance(theGen.ID);
        }
        return (Integer) Defaults.STONE_CHANCE.getDefault();
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
     * @param world World
     * 
     * @return true if asteroid generation is enabled
     */
    public static boolean getAsteroidsEnabled(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
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
        if (id.equalsIgnoreCase("planets")) {
            return true;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids" + id + ".generation.generateasteroids", (Boolean) Defaults.ASTEROIDS_ENABLED.getDefault());
    }

    /**
     * Checks if Spout will be used.
     * 
     * @return true if Spout is used
     */
    public static boolean isUsingSpout() {
        return SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("global.usespout", (Boolean) Defaults.USE_SPOUT.getDefault());
    }

    /**
     * Checks if a world is in the configuration file.
     * 
     * @param name Name of the world
     * 
     * @return True if the world is in the config file
     * @deprecated Don't see why this is needed, will probably go away VERY soon.
     */
    public static boolean isWorldInConfig(String name) {
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
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
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
        if (id.equalsIgnoreCase("planets")) {
            return true;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getBoolean("ids." + id + ".generation.generatesatellites", (Boolean) Defaults.SATELLITES_ENABLED.getDefault());
    }

    /**
     * Gets the satellite spawn chance.
     * 
     * @param world World
     * 
     * @return Spawn chance
     */
    public static int getSatelliteChance(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
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
        if (id.equalsIgnoreCase("planets")) {
            return 1;
        }
        return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + id + ".generation.satellitechance", (Integer) Defaults.SATELLITE_CHANCE.getDefault());
    }

    /**
     * Gets the spout texture pack url.
     * 
     * @return Url of the texture used in the config, or the default
     */
    public static String getSpoutTexturePack() {
        String texture = SpaceConfig.getConfig(ConfigFile.CONFIG).getString("global.texturepack", (String) Defaults.TEXTURE_PACK.getDefault());
        try {
            URL url = new URL(texture);
            URI toURI = url.toURI();
        } catch (MalformedURLException ex) {
            return (String) Defaults.TEXTURE_PACK.getDefault();
        } catch (URISyntaxException ex) {
            return (String) Defaults.TEXTURE_PACK.getDefault();
        }
        return texture.isEmpty() ? (String) Defaults.TEXTURE_PACK.getDefault() : texture;
    }

    /**
     * Gets the generateplantes-value.
     * 
     * @param world World
     * 
     * @return True if generatng pnalets
     */
    public static boolean getGeneratePlanets(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return theGen.GENERATE;
        }
        return true;
    }

    /**
     * Gets generate planets value.
     * 
     * @param id ID
     * 
     * @return True if generateplantes=true
     */
    public static boolean getGeneratePlanets(String id) {
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
     * @param world World
     * 
     * @return Schematic chance
     */
    public static int getSchematicChance(World world) {
        if (world.getGenerator() instanceof PlanetsChunkGenerator) {
            PlanetsChunkGenerator theGen = (PlanetsChunkGenerator) world.getGenerator();
            return SpaceConfig.getConfig(ConfigFile.IDS).getInt("ids." + theGen.ID + ".generation.schematicchance", (Integer) Defaults.SCHEMATIC_CHANCE.getDefault());
        }
        return (Integer) Defaults.SCHEMATIC_CHANCE.getDefault();
    }

    /**
     * Constructor of SpaceConfigHandler.
     */
    private SpaceConfigHandler() {
    }
}
