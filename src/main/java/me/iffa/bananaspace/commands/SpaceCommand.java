// Package Declaration
package me.iffa.bananaspace.commands;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;

// Bukkit Imports
import org.bukkit.command.CommandSender;

/**
 * Represents "/space args".
 * 
 * @author iffa
 */
public abstract class SpaceCommand {
    // Variables
    protected BananaSpace plugin;
    protected CommandSender sender;
    protected String[] args;

    /**
     * Constructor of SpaceCommand.
     * 
     * @param plugin BananaSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceCommand(BananaSpace plugin, CommandSender sender, String[] args) {
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
        command();
    }

    /**
     * Does the command.
     */
    public abstract void command();
}
