// Package Declaration
package me.iffa.bspace.api;

// Java Imports
import java.util.logging.Level;
import java.util.logging.Logger;

// bSpace Imports
import me.iffa.bspace.handlers.ConfigHandler;

/**
 * Useful methods to send messages to players and console.
 * 
 * External use only
 * 
 * @author iffa
 */
public class SpaceMessageHandler {
    // Variables
    protected static final Logger log = Logger.getLogger("Minecraft");
    private static String printPrefix = "[Unknown]";
    
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
     * Constructor of SpaceMessageHandler.
     */
    protected SpaceMessageHandler() {
    }
}
