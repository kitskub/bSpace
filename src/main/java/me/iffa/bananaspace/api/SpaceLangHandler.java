// Package Declaration
package me.iffa.bananaspace.api;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.config.SpaceConfig;
import me.iffa.bananaspace.config.SpaceConfig.ConfigFile;

/**
 * Useful methods to get localized strings from lang.yml. Try keeping params to a minimum!
 * 
 * @author iffamies
 */
public class SpaceLangHandler {
    /* General Strings */

    /**
     * Gets the enabled message.
     * 
     * @return Localized enabled message
     */
    public static String getEnabledMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("general.enabled").replace("{version}", BananaSpace.version);
    }

    /**
     * Gets the disabled message.
     * 
     * @return Localized disabled message
     */
    public static String getDisabledMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("general.disabled").replace("{version}", BananaSpace.version);
    }

    /**
     * Gets the 'now sending usage stats'-message.
     * 
     * @return Localized usage stats message
     */
    public static String getUsageStatsMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("general.usagestats");
    }

    /**
     * Gets the config loaded message.
     * 
     * @param configfile ConfigFile that was loaded
     * 
     * @return Localized config loaded message
     */
    public static String getConfigLoadedMessage(ConfigFile configfile) {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("general.configload").replace("{configfile}", configfile.getFile());
    }

    /**
     * Gets the cfg update start message.
     * 
     * @return Localized message
     */
    public static String getConfigUpdateStartMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("general.configupdatestart");
    }

    /**
     * Gets the cfg update finish message.
     * 
     * @return Localized message
     */
    public static String getConfigUpdateFinishMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("general.configupdatefinish");
    }

    /**
     * Gets the cfg update failure message.
     * 
     * @param ex Exception
     * 
     * @return Localized message
     */
    public static String getConfigUpdateFailureMessage(Exception ex) {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("general.configupdatefailure").replace("{exception}", ex.getMessage());
    }

    /* Command Strings */
    /**
     * Gets the not in space message.
     * 
     * @return Localized not in space message
     */
    public static String getNotInSpaceMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("commands.notinspace");
    }

    /**
     * Gets the already in that sworld message.
     * 
     * @return Localized already in that space world message
     */
    public static String getAlreadyInThatWorldMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("commands.alreadyinthere");
    }

    /**
     * Gets the exit not found message.
     * 
     * @param line One or two, decides which not found-line to get
     * 
     * @return Localized exit not found message
     */
    public static String getNoExitFoundMessage(int line) {
        String[] lines = SpaceConfig.getConfig(ConfigFile.LANG).getString("commands.exitnotfound").split("\\r?\\n");
        return line == 1 ? lines[0] : lines[1];
    }

    /**
     * Gets the world not found message.
     * 
     * @return Localized world not found message
     */
    public static String getWorldNotFoundMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("commands.worldnotfound");
    }

    /**
     * Gets the not space message.
     * 
     * @return Localized message
     */
    public static String getWorldNotSpaceMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("commands.worldnotspace");
    }
    
    /**
     * Gets the list of space message.
     * 
     * @return Localized message
     */
    public static String getListOfSpaceMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("commands.listofspace");
    }

    /* Other Strings */
    /**
     * Gets the not enough money message.
     * 
     * @return Localized not enough money message
     */
    public static String getNotEnoughMoneyMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("other.notenoughmoney");
    }

    /**
     * Gets the no permission message.
     * 
     * @return Localized no permission message
     */
    public static String getNoPermissionMessage() {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("other.nopermission");
    }

    /**
     * 
     * @param world name of world
     * @return
     */
    public static String getIdNotFoundMessage(String id) {
        return SpaceConfig.getConfig(ConfigFile.LANG).getString("other.idnotfound").replace("{idname}", id);
    }

    /**
     * Constructor of SpaceLangHandler.
     */
    private SpaceLangHandler() {
    }
}
