// Package Declaration
package me.iffa.bspace.commands;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceLangHandler;
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpacePlayerHandler;

// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Represents "/space list".
 * 
 * @author iffa
 */
public class SpaceListCommand extends SpaceCommand {
    /**
     * Constructor of SpaceListCommand.
     * 
     * @param plugin bSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceListCommand(Space plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        if (!SpacePlayerHandler.hasPermission("bSpace.teleport.list", (Player) sender)) {
            SpaceMessageHandler.sendNoPermissionMessage((Player) sender);
            return;
        }
        sender.sendMessage(ChatColor.GREEN + Space.prefix + " " + SpaceLangHandler.getListOfSpaceMessage());
        for (World world : Space.getWorldHandler().getSpaceWorlds()) {
            sender.sendMessage(ChatColor.DARK_GREEN + "- " + world.getName());
        }
        return;
    }
}
