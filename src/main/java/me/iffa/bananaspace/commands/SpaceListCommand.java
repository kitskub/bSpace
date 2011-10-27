// Package Declaration
package me.iffa.bananaspace.commands;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.api.SpacePlayerHandler;

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
     * @param plugin BananaSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceListCommand(BananaSpace plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        if (!SpacePlayerHandler.hasPermission("bananaspace.teleport.list", (Player) sender)) {
            SpaceMessageHandler.sendNoPermissionMessage((Player) sender);
            return;
        }
        sender.sendMessage(ChatColor.GREEN + BananaSpace.prefix + " List of spaceworlds:");
        for (World world : BananaSpace.getWorldHandler().getSpaceWorlds()) {
            sender.sendMessage(ChatColor.DARK_GREEN + "- " + world.getName());
        }
        return;
    }
}
