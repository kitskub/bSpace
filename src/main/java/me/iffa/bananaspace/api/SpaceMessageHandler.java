// Package Declaration
package me.iffa.bananaspace.api;

// Java Imports
import java.util.logging.Level;
import java.util.logging.Logger;

// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Useful methods to send messages to players and console. Feel free to use this for your own plugins.
 * 
 * @author iffa
 */
public class SpaceMessageHandler {
    // Variables
    private static final Logger log = Logger.getLogger("Minecraft");
    private static String printPrefix = "BananaSpace"; // Default, just in case
    private Plugin plugin;
    
    /**
     * Constructor of SpaceMessageHandler.
     * 
     * @param plugin Plugin using the class
     * @param printPrefix Prefix to use when printing to console (can be left null) 
     */
    public SpaceMessageHandler(Plugin plugin, String printPrefix) {
        this.plugin = plugin;
        SpaceMessageHandler.printPrefix = printPrefix;
    }
    
    /**
     * Prints a message to the console.
     * 
     * @param level Logging level (INFO, WARNING etc)
     * @param message Message to print
     */
    public void print(Level level, String message) {
        log.log(level, printPrefix + " " + message);
    }
   
    /**
     * Prints a debug message to the console if debugging is enabled.
     * 
     * @param level Logging level (INFO, WARNING etc)
     * @param message Message to print
     */
    public static void debugPrint(Level level, String message) {
        if (SpaceConfigHandler.getDebugging()) {
            log.log(level, printPrefix + " " + message);
        }
    }
    
    /**
     * Sends a "You don't have permission!"-message to a player.
     * 
     * @param player Player
     */
    public void sendNoPermissionMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You don't have permission!");
    }
    
    /**
     * sends a "You don't have enough money!"-message to a player.
     * 
     * @param player Player
     */
    public void sendNotEnoughMoneyMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You don't have enough money!");
    }
}
