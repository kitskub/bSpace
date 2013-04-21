// Package Declaration
package me.iffa.bspace.commands;

// bSpace Imports
import me.iffa.bspace.Space;

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
     * @param plugin bSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceHelpCommand(Space plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        getSender().sendMessage(ChatColor.GOLD + "[bSpace] Usage:");
        getSender().sendMessage(ChatColor.GRAY + " /space enter [world] - Go to space (default world or given one)");
        getSender().sendMessage(ChatColor.GRAY + " /space back - Leave space or go back where you were in space");
        getSender().sendMessage(ChatColor.GRAY + " /space list - Brings up a list of all space worlds");
        getSender().sendMessage(ChatColor.GRAY + " /space help - Brings up this help message");
        getSender().sendMessage(ChatColor.GRAY + " /space about [credits] - About bSpace");
        getSender().sendMessage(ChatColor.GRAY + "If you have questions, please visit " + ChatColor.GOLD + "bit.ly/banspace" + ChatColor.GRAY + "!");
        //getSender().sendMessage(ChatColor.GRAY + "...or if you prefer IRC, #iffa or #bananacode (Espernet)");
    }
}
