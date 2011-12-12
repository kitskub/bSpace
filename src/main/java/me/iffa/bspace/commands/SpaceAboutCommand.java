// Package Declaration
package me.iffa.bspace.commands;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceWorldHandler;

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
        if (args.length < 2) {
            sender.sendMessage(ChatColor.GOLD + Space.getPrefix() + " About:");
            sender.sendMessage(ChatColor.GOLD + "-" + ChatColor.GRAY + " You're running version " + ChatColor.GOLD + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.GOLD + "-" + ChatColor.GRAY + " There are currently "+ ChatColor.GOLD + SpaceWorldHandler.getSpaceWorlds().size() + ChatColor.GRAY + " space worlds loaded");
        } else {
            sender.sendMessage(ChatColor.GOLD + "-" + ChatColor.GRAY + " Main developers:");
            sender.sendMessage(ChatColor.GOLD + "    iffa, kitskub");
            sender.sendMessage(ChatColor.GOLD + "-" + ChatColor.GRAY + " Other contributors (in no particular order):");
            sender.sendMessage(ChatColor.GOLD + "    Canis85, BR, SwearWord, HACKhalo2");
        }
    }
    
}
