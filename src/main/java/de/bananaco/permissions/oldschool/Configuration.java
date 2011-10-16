// Package Declaration
package de.bananaco.permissions.oldschool;

// Java Imports
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

// Bukkit Imports
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Substitute for the old Bukkit configuration object. Replicates the old functionality in the new object
 * 
 * @author codename_B
 * @author desht
 * @author iffa (JavaDocs, my standrads are also rats)
 */
public class Configuration extends YamlConfiguration {
    // Variables
    private final File file;
    private static final Logger log = Logger.getLogger("Minecraft");

    /**
     * A substitute for getConfiguration().
     * 
     * @param plugin Plugin instance
     */
    public Configuration(JavaPlugin plugin) {
        this(new File(plugin.getDataFolder(), "config.yml"));
        load(); // Y u
    }

    /**
     * Constructor of Configuration.
     * 
     * @param fileName Configuration file name
     */
    public Configuration(String fileName) {
        this(new File(fileName));
    }

    /**
     * Constructor of Configuration.
     * 
     * @param file Configuration file
     */
    public Configuration(File file) {
        super();
        if (file == null) {
            log.warning("File should not be null!");
        }
        this.file = file;
    }

    /**
     * Loads the configuration file.
     */
    public void load() {
        try {
            // First do checks to create the initial file.
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                if (file.createNewFile()) {
                    super.save(file);
                } else {
                    throw new Exception("Cannot load: File can not be created!");
                }
            }
            // Then do checks to save the file
            super.load(file);
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    /**
     * Saves the configuration file.
     */
    public void save() {
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                if (file.createNewFile()) {
                    super.save(file);
                } else {
                    throw new Exception("Cannot save: File can not be created!");
                }
            } else {
                super.save(file);
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    /**
     * Gets all configuration nodes.
     * 
     * @return List of configuration nodes
     */
    public List<String> getKeys() {
        Set<String> keys = super.getKeys(false);
        List<String> lkeys = new ArrayList<String>();
        if (keys != null) {
            for (String key : keys) {
                lkeys.add(key);
            }
        }
        return lkeys;
    }

    /**
     * Gets all configuration node under a node.
     * 
     * @param path Path
     * 
     * @return List of configuration nodes
     */
    public List<String> getKeys(String path) {
        List<String> lkeys = new ArrayList<String>();
        ConfigurationSection cs = super.getConfigurationSection(path);

        if (cs == null) {
            return lkeys;
        }

        Set<String> keys = cs.getKeys(false);

        if (keys != null) {
            for (String key : keys) {
                lkeys.add(key);
            }
        }
        return lkeys;
    }

    /**
     * Sets a configuration node's property.
     * 
     * @param path Node
     * @param object Object
     */
    public void setProperty(String path, Object object) {
        super.set(path, object);
    }

    /**
     * Should work according to the javadocs, but doesn't.
     * Fixed in the latest bukkit builds
     * @param path
     */
    public void removeProperty(String path) {
        try {
            super.set(path, null);
        } catch (Exception e) {
        }
    }

    public List<String> getStringList(String path, List<String> def) {
        if (def == null) {
            def = new ArrayList<String>();
        }

        List<?> list = super.getList(path, def);
        if (list == null) {
            return def;
        }
        try {
            @SuppressWarnings("unchecked")
            List<String> sList = (List<String>) list;
            return sList;
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
        return def;
    }
}