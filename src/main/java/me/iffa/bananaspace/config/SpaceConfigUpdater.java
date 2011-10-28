// Package Declaration
package me.iffa.bananaspace.config;

// Java Imports
import java.io.IOException;
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.config.SpaceConfig.ConfigFile;

// Bukkit Imports
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Converts old pre-v2 worlds into v2 IDs.
 * 
 * @author iffa
 */
public class SpaceConfigUpdater {
    // Variables
    private static boolean hadToBeUpdated = false;
    
    /**
     * Checks if the configuration files had to be "fixed".
     * 
     * @return True if had to be updated
     */
    public static boolean wasUpdated() {
        return hadToBeUpdated;
    }

    /**
     * Checks if a config file needs updating to v2.
     * 
     * @param configfile ConfigFile to check
     * 
     * @return True if needs updating
     */
    private static boolean needsUpdate(ConfigFile configfile) {
        if (SpaceConfig.getConfig(configfile).contains("worlds")) {
            for(String world : SpaceConfig.getConfig(configfile).getConfigurationSection("worlds").getKeys(false)){
                if(SpaceConfig.getConfig(configfile).getConfigurationSection("worlds." + world).contains("generation")){
                    hadToBeUpdated = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates pre-v2 config files to be compatible with v2. This way the only thing the
     * user has to do to fix configs for v2 is actually update and run it.
     */
    public static void updateConfigs() {
        if (!needsUpdate(ConfigFile.CONFIG)) {
            return;
        }
        // Variables
        SpaceMessageHandler.print(Level.INFO, "Starting to update configs for compatability with v2.");
        YamlConfiguration configFile = SpaceConfig.getConfig(ConfigFile.CONFIG);
        YamlConfiguration idsFile = SpaceConfig.getConfig(ConfigFile.IDS);
        
        for (String world : configFile.getConfigurationSection("worlds").getKeys(false)) {
            // Moving values onto ids.yml
            for (String key : configFile.getConfigurationSection("worlds." + world).getKeys(true)) {
                String value = configFile.getString("worlds." + world + "." + key);
                
                idsFile.set("ids." + world + "." + key, value);
                SpaceMessageHandler.debugPrint(Level.INFO, "Moved " + key + " of " + world + " to ids.yml with a value of " + value);
            }
            // Removing world from config.yml since it's moved to ids.yml already
            configFile.set("worlds." + world, null);
            SpaceMessageHandler.debugPrint(Level.INFO, "Removed " + world + " from config.yml.");
        }
        // Saving both files since converting is done.
        try {
            configFile.save(SpaceConfig.getConfigFile(ConfigFile.CONFIG));
            idsFile.save(SpaceConfig.getConfigFile(ConfigFile.IDS));
            SpaceMessageHandler.debugPrint(Level.INFO, "Saved changes to ids and config.yml.");
        } catch (IOException ex) {
            // In case of any error.
            SpaceMessageHandler.print(Level.SEVERE, "There was a problem converting configuration files for v2: " + ex.getMessage());
        }
        SpaceMessageHandler.print(Level.INFO, "Your pre-v2 config.yml was succesfully converted to the new v2 format. Your worlds can now be found");
    }

    /**
     * Constructor of SpaceConfigUpdater.
     */
    private SpaceConfigUpdater() {
    }
}
