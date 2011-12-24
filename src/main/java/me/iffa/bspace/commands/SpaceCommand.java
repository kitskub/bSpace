// Package Declaration
package me.iffa.bspace.commands;

// bSpace Imports
import me.iffa.bspace.Space;

// Bukkit Imports
import org.bukkit.command.CommandSender;

/**
 * Represents "/space args".
 * 
 * @author iffa
 */
public abstract class SpaceCommand {
    // Variables
    private Space plugin;
    private CommandSender sender;
    private String[] args;

    /**
     * Constructor of SpaceCommand.
     * 
     * @param plugin bSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceCommand(Space plugin, CommandSender sender, String[] args) {
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
        command(); // It's bad but fuck that
    }

    /**
     * Does the command.
     */
    public abstract void command();

    /**
     * Gets the plugin.
     * 
     * @return the plugin
     */
    public Space getPlugin() {
        return plugin;
    }

    /**
     * Gets the sender.
     * 
     * @return the sender
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * Gets the arguments.
     * 
     * @return the args
     */
    public String[] getArgs() {
        return args;
    }
}
