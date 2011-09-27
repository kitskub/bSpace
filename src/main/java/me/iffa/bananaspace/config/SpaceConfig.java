// Package Declaration
package me.iffa.bananaspace.config;

// Java Imports
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Level;

//BananaSpace Import
import me.iffa.bananaspace.BananaSpace;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.util.config.Configuration;

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
    private static Configuration myConfig;
    private static boolean loaded = false;

    /**
     * Gets the configuration file.
     * 
     * @return the myConfig
     */
    public static Configuration getConfig() {
        if (!loaded) {
            loadConfig();
        }
        return myConfig;
    }

    /**
     * Loads the configuration file from the .jar.
     */
    public static void loadConfig() {
        File configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder(), "config.yml");
        if (configFile.exists()) {
            myConfig = new Configuration(configFile);
            myConfig.load();
            loaded = true;
        } else {
            try {
                Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder().mkdir();
                InputStream jarURL = SpaceConfig.class.getResourceAsStream("/config.yml");
                copyFile(jarURL, configFile);
                myConfig = new Configuration(configFile);
                myConfig.load();
                loaded = true;
                BananaSpace.getMessageHandler().print(Level.INFO, "Generated configuration file for version " + BananaSpace.version);
            } catch (Exception e) {
                BananaSpace.getMessageHandler().print(Level.SEVERE, e.toString());
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
}
