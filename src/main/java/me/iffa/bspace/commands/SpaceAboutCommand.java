// Package Declaration
package me.iffa.bspace.commands;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.handlers.WorldHandler;

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
        if (getArgs().length < 2) {
            getSender().sendMessage(ChatColor.GOLD + Space.getPrefix() + " About:");
            getSender().sendMessage(ChatColor.GOLD + "-" + ChatColor.GRAY + " You're running version " + ChatColor.GOLD + getPlugin().getDescription().getVersion());
            getSender().sendMessage(ChatColor.GOLD + "-" + ChatColor.GRAY + " There are currently "+ ChatColor.GOLD + WorldHandler.getSpaceWorlds().size() + ChatColor.GRAY + " space worlds loaded");
        } else {
            getSender().sendMessage(ChatColor.GOLD + "-" + ChatColor.GRAY + " Main developers:");
            getSender().sendMessage(ChatColor.GOLD + "    iffa, kitskub");
            getSender().sendMessage(ChatColor.GOLD + "-" + ChatColor.GRAY + " Other contributors (in no particular order):");
            getSender().sendMessage(ChatColor.GOLD + "    Canis85, BR, SwearWord, HACKhalo2");
        }
    }
    
}
