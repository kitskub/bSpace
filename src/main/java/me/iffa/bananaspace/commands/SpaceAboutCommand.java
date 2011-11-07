// Package Declaration
package me.iffa.bananaspace.commands;

import me.iffa.bananaspace.BananaSpace;
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
     * @param plugin BananaSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceAboutCommand(BananaSpace plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        sender.sendMessage(ChatColor.DARK_GREEN + BananaSpace.prefix + " About BananaSpace:");
        sender.sendMessage(ChatColor.DARK_GREEN + "- You're running version " + ChatColor.GREEN + plugin.getDescription().getVersion() + ChatColor.DARK_GREEN +  "");
        sender.sendMessage(ChatColor.DARK_GREEN + "- All " + ChatColor.GREEN + plugin.getDescription().getAuthors().size() + " developers/contributors " + ChatColor.DARK_GREEN + "thank you:");
        sender.sendMessage("  " + ChatColor.GREEN + plugin.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
    }
    
}
