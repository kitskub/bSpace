// Package Declaration
package me.iffa.bananaspace.config;

// Java Imports
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
 */
public class SpaceConfig {
    // Variables
    public static Configuration myConfig;

    /**
     * Loads the configuration file from the .jar.
     */
    public void loadConfig() {
        File configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder(), "config.yml");
        if (configFile.exists()) {
            myConfig = new Configuration(configFile);
            myConfig.load();
        } else {
            try {
                Bukkit.getServer().getPluginManager().getPlugin("BananaSpace").getDataFolder().mkdir();
                InputStream jarURL = getClass().getResourceAsStream("/config.yml");
                SpaceConfig.copyFile(jarURL, configFile);
                myConfig = new Configuration(configFile);
                myConfig.load();
                BananaSpace.log.info(BananaSpace.prefix + " Generated configuration file for version " + BananaSpace.version);
            } catch (Exception e) {
                System.out.println(e.toString());
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
    static public void copyFile(InputStream in, File out) throws Exception {
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
}
