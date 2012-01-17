package me.iffa.bspace.handlers;

import java.util.logging.Level;
import me.iffa.bspace.api.SpaceMessageHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Useful methods to send messages to players and console.
 * 
 * Internal use only
 * 
 * @author Jack
 */
public class MessageHandler extends SpaceMessageHandler{
    private static String printPrefix = "[bSpace]";
    
    /**
     * Prints a message to the console.
     * 
     * @param level Logging level (INFO, WARNING etc)
     * @param message Message to print
     */
    public static void print(Level level, String message) {
        log.log(level, printPrefix + " " + message);
    }

    /**
     * Prints a debug message to the console if debugging is enabled.
     * 
     * @param level Logging level (INFO, WARNING etc)
     * @param message Message to print
     */
    public static void debugPrint(Level level, String message) {
        
        if (ConfigHandler.getDebugging()) {
            log.log(level, printPrefix + " " + message);
        }
    }
    
    /**
     * Sends a "You don't have permission!"-message to a player.
     * 
     * @param player Player
     */
    public static void sendNoPermissionMessage(Player player) {
        player.sendMessage(ChatColor.RED + LangHandler.getNoPermissionMessage());
    }

    /**
     * sends a "You don't have enough money!"-message to a player.
     * 
     * @param player Player
     */
    public static void sendNotEnoughMoneyMessage(Player player) {
        player.sendMessage(ChatColor.RED + LangHandler.getNotEnoughMoneyMessage());
    }
     
    private MessageHandler() {
    }
    
    
}
