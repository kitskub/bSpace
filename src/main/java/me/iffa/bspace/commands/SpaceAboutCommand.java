// Package Declaration
package me.iffa.bspace.commands;

// bSpace Imports
import me.iffa.bspace.Space;

// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Represents "/space about".
 * 
 * @author iffamies
 */
public class SpaceAboutCommand extends SpaceCommand {
    /**
     * Constructor of SpaceAboutCommand.
     * 
     * @param plugin bSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceAboutCommand(Space plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        sender.sendMessage(ChatColor.DARK_GREEN + Space.getPrefix() + " About bSpace:");
        sender.sendMessage(ChatColor.GREEN + "- You're running version " + ChatColor.DARK_GREEN + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.GREEN + "- All " + ChatColor.DARK_GREEN + plugin.getDescription().getAuthors().size() + " developers/contributors " + ChatColor.GREEN + "thank you:");
        sender.sendMessage("  " + ChatColor.GREEN + plugin.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
    }
    
}
