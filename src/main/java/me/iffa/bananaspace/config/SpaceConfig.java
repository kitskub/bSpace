// Package Declaration
package me.iffa.bananaspace.config;

// Java Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;

//BananaSpace Imports
import me.iffa.bananaspace.api.SpaceMessageHandler;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * A class that handles the configuration file.
 * 
 * @author iffa
 * @author Pandarr
 * @author Sammy
 * @author kitskub
 */
public class SpaceConfig {
    // Variables
    private static Map<ConfigFile, YamlConfiguration> config = new EnumMap<ConfigFile, YamlConfiguration>(ConfigFile.class);
    private static Map<ConfigFile, File> configFile = new EnumMap<ConfigFile, File>(ConfigFile.class);
    private static Map<ConfigFile, Boolean> loaded = new EnumMap<ConfigFile, Boolean>(ConfigFile.class);
    
    /**
     * Gets the configuration file.
     * 
     * @param configfile ConfigFile to get
     * 
     * @return YamlConfiguration object
     */
    public static YamlConfiguration getConfig(ConfigFile configfile) {
        if (loaded.get(configfile)==null || !loaded.get(configfile)) {
            loadConfig(configfile);
        }
        return config.get(configfile);
    }
    
    /**
     * Gets the configuration file.
     * 
     * @param configfile ConfigFile to get
     * 
     * @return Configuration file
     */
    public static File getConfigFile(ConfigFile configfile) {
        return configFile.get(configfile);
    }
    
    /**
     * Checks if the configuration file is loaded.
     * 
     * @param configfile ConfigFile to get
     * 
     * @return True if configuraton file is loaded
     */
    public static boolean getLoaded(ConfigFile configfile) {
        return loaded.get(configfile);
    }
    
    /**
     * Loads all configuration files. (can be used to save a total of 2 lines!)
     */
    public static void loadConfigs() {
        for (ConfigFile configfile : ConfigFile.values()) {
            loadConfig(configfile);
        }
    }

    /**
     * Loads the configuration file from the .jar.
     * 
     * @param configfile ConfigFile to load
     */
    public static void loadConfig(ConfigFile configfile) {
        configFile.put(configfile, new File(Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder(), configfile.getFile()));
        if (configFile.get(configfile).exists()) {
            config.put(configfile, new YamlConfiguration());
            try {
                config.get(configfile).load(configFile.get(configfile));
            } catch (FileNotFoundException ex) {
                SpaceMessageHandler.print(Level.WARNING, ex.getMessage());
                loaded.put(configfile, false);
                return;
            } catch (IOException ex) {
                SpaceMessageHandler.print(Level.WARNING, ex.getMessage());
                loaded.put(configfile, false);
                return;
            } catch (InvalidConfigurationException ex) {
                SpaceMessageHandler.print(Level.WARNING, ex.getMessage());
                loaded.put(configfile, false);
                return;
            }
            loaded.put(configfile, true);
        } else {
            try {
                Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder().mkdir();
                InputStream jarURL = SpaceConfig.class.getResourceAsStream("/" + configfile.getFile());
                copyFile(jarURL, configFile.get(configfile));
                config.put(configfile, new YamlConfiguration());
                config.get(configfile).load(configFile.get(configfile));
                loaded.put(configfile, true);
                SpaceMessageHandler.print(Level.INFO, "Generated " + configfile.getFile() + " file");
            } catch (Exception e) {
                SpaceMessageHandler.print(Level.SEVERE, e.toString());
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
    static private void copyFile(InputStream in, File out) throws Exception {
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
     * Constructor of SpaceConfig.
     */
    private SpaceConfig() {
    }
    
    /**
     * All config files.
     */
    public enum ConfigFile {
        // Enums
        PLANETS("planets.yml"),
        CONFIG("config.yml"),
        IDS("ids.yml");
        
        // Variables
        private String file;
        
        /**
         * Constructor of ConfigFile.
         * @param file 
         */
        ConfigFile(String file) {
            this.file = file;
        }
        
        /**
         * Gets the file associated with the enum.
         * 
         * @return File associated wiht the enum
         */
        public String getFile() {
            return this.file;
        }
    }
}