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
    protected Space plugin;
    protected CommandSender sender;
    protected String[] args;

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
}
