// Package Declaration
package me.iffa.bananaspace.config;

// Java Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

//BananaSpace Import
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceMessageHandler;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * A class that handles the planet generation configuration file.
 * 
 * @author iffa
 * @author Pandarr
 * @author Sammy
 * @author kitskub
 */
public class SpacePlanetConfig {
    // Variables
    private static YamlConfiguration myConfig;
    private static File configFile;
    private static boolean loaded = false;

    /**
     * Gets the configuration file.
     * 
     * @return the myConfig
     */
    public static YamlConfiguration getConfig() {
        if (!loaded) {
            loadConfig();
        }
        return myConfig;
    }
    
    /**
     * Gets the configuration file.
     * 
     * @return Config file
     */
    public static File getConfigFile() {
        return configFile;
    }

    /**
     * Loads the configuration file from the .jar.
     */
    public static void loadConfig() {
        if (!loaded) {
            File configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder(), "planets.yml");
            if (configFile.exists()) {
                myConfig = new YamlConfiguration();
                try {
                    myConfig.load(configFile);
                } catch (FileNotFoundException ex) {
                    SpaceMessageHandler.print(Level.WARNING, ex.getMessage());
                } catch (IOException ex) {
                    SpaceMessageHandler.print(Level.WARNING, ex.getMessage());
                } catch (InvalidConfigurationException ex) {
                    SpaceMessageHandler.print(Level.WARNING, ex.getMessage());
                }
                loaded = true;
            } else {
                try {
                    Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder().mkdir();
                    InputStream jarURL = SpacePlanetConfig.class.getResourceAsStream("/planets.yml");
                    copyFile(jarURL, configFile);
                    myConfig = new YamlConfiguration();
                    myConfig.load(configFile);
                    loaded = true;
                    if ((long) myConfig.getDouble("seed", -1.0) == -1) {
                        myConfig.set("seed", Bukkit.getServer().getWorlds().get(0).getSeed());
                    }
                    SpaceMessageHandler.print(Level.INFO, "Generated planet configuration for version " + BananaSpace.version);
                } catch (Exception e) {
                    SpaceMessageHandler.print(Level.SEVERE, e.toString());
                }
            }
        }
    }

    /**
     * Copies a file to a new location.
     * 
     * @param in InputStream
     * @param out File
     * 
     * @throws Exception
     */
    private static void copyFile(InputStream in, File out) throws Exception {
        InputStream fis = in;
        FileOutputStream fos = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * Constructor of SpacePlanetConfig.
     */
    private SpacePlanetConfig() {
    }
}
