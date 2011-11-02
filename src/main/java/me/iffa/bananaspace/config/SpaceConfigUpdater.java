// Package Declaration
package me.iffa.bananaspace.config;

// Java Imports
import java.io.IOException;
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.api.SpaceLangHandler;
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
            try {
            for(String world : SpaceConfig.getConfig(configfile).getConfigurationSection("worlds").getKeys(false)){
                if(SpaceConfig.getConfig(configfile).getConfigurationSection("worlds." + world).contains("generation")){
                    hadToBeUpdated = true;
                    return true;
                }
            }
            } catch (NullPointerException ex) {
                return false;
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
        SpaceMessageHandler.print(Level.INFO, SpaceLangHandler.getConfigUpdateStartMessage());
        YamlConfiguration configFile = SpaceConfig.getConfig(ConfigFile.CONFIG);
        YamlConfiguration idsFile = SpaceConfig.getConfig(ConfigFile.IDS);
        
        for (String world : configFile.getConfigurationSection("worlds").getKeys(false)) {
            // Generation values
            for (String key : configFile.getConfigurationSection("worlds." + world + "." + "generation").getKeys(false)) {
                Object value = configFile.get("worlds." + world + ".generation." + key);
                idsFile.set("ids." + world + ".generation." + key, value);
                SpaceMessageHandler.debugPrint(Level.INFO, "Moved " + key + " of " + world + " to ids.yml with a value of " + value);
            }
            // Suit values
            for (String key : configFile.getConfigurationSection("worlds." + world + "." + "suit").getKeys(false)) {
                Object value = configFile.get("worlds." + world + ".suit." + key);
                idsFile.set("ids." + world + ".suit." + key, value);
                SpaceMessageHandler.debugPrint(Level.INFO, "Moved " + key + " of " + world + " to ids.yml with a value of " + value);
            }
            // Helmet values
            for (String key : configFile.getConfigurationSection("worlds." + world + "." + "helmet").getKeys(false)) {
                Object value = configFile.get("worlds." + world + ".helmet." + key);
                idsFile.set("ids." + world + ".helmet." + key, value);
                SpaceMessageHandler.debugPrint(Level.INFO, "Moved " + key + " of " + world + " to ids.yml with a value of " + value);
            }
            // Misc. values
            for (String key : configFile.getConfigurationSection("worlds." + world).getKeys(false)) {
                // So we don't make bad things happen. Skrillex (Y)
                if (key.equalsIgnoreCase("generation") || key.equalsIgnoreCase("suit") || key.equalsIgnoreCase("helmet")) {
                    continue;
                }
                Object value = configFile.get("worlds." + world + "." + key);
                idsFile.set("ids." + world + "." + key, value);
                SpaceMessageHandler.debugPrint(Level.INFO, "Moved " + key + " of " + world + " to ids.yml with a value of " + value);
            }
            
            // Removing the world from config.yml.
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
            SpaceMessageHandler.print(Level.SEVERE, SpaceLangHandler.getConfigUpdateFailureMessage(ex));
        }
        // It was all done.
        SpaceMessageHandler.print(Level.INFO, SpaceLangHandler.getConfigUpdateFinishMessage());
    }

    /**
     * Constructor of SpaceConfigUpdater.
     */
    private SpaceConfigUpdater() {
    }
}
