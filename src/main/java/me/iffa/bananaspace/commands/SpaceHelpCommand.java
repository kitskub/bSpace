// Package Declaration
package me.iffa.bananaspace.commands;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;

// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Represents "/space help".
 * 
 * @author iffa
 */
public class SpaceHelpCommand extends SpaceCommand {
    /**
     * Constructor of SpaceHelpCommand.
     * 
     * @param plugin BananaSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceHelpCommand(BananaSpace plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        sender.sendMessage(ChatColor.DARK_GREEN + "[BananaSpace] Usage:");
        sender.sendMessage(ChatColor.GREEN + "/space enter [world] - Go to space (default world or given one)");
        sender.sendMessage(ChatColor.GREEN + "/space back - Leave space or go back where you were in space");
        sender.sendMessage(ChatColor.GREEN + "/space list - Brings up a list of all space worlds");
        sender.sendMessage(ChatColor.GREEN + "/space help - Brings up this help message");
        sender.sendMessage(ChatColor.GREEN + "/space about - About BananaSpace");
        sender.sendMessage(ChatColor.DARK_GREEN + "If you have questions, please visit " + ChatColor.GRAY + "bit.ly/bananaspace" + ChatColor.DARK_GREEN + "!");
    }
}
