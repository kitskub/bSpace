// Package Declaration
package me.iffa.bananaspace.commands;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.api.SpacePlayerHandler;

// Bukkit Imports
import me.iffa.bananaspace.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Represents "/space back".
 * 
 * @author iffa
 */
public class SpaceExitCommand extends SpaceCommand {
    // Variables
    public static Map<Player, Location> enterDest = new HashMap<Player, Location>();;

    /**
     * Constructor of SpaceExitCommand.
     * 
     * @param plugin BananaSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceExitCommand(BananaSpace plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        Player player = (Player) sender;
        if (BananaSpace.worldHandler.isInAnySpace(player)) {
            if (SpacePlayerHandler.hasPermission("bananaspace.teleport.exit", player)) {
                if(!Economy.exitCommand(player)){
                            SpaceMessageHandler.sendNotEnoughMoneyMessage(player);
                            return;
                }
                enterDest.put(player, player.getLocation());
                Location location;
                if (SpaceEnterCommand.exitDest.containsKey(player)) {
                    location = SpaceEnterCommand.exitDest.get(player);
                    SpaceMessageHandler.debugPrint(Level.INFO, "Teleported player '" + player.getName() + "' out of space.");
                    player.teleport(location);
                    return;
                } else {
                    SpaceEnterCommand.exitDest.put(player, Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
                    sender.sendMessage(ChatColor.RED + "Exit destination not found, setting to default world spawn location.");
                    sender.sendMessage(ChatColor.RED + "Type '/space back' again to go there.");
                    return;
                }
            } else {
                SpaceMessageHandler.sendNoPermissionMessage(player);
                return;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You are not in space!");
            return;
        }
    }
}