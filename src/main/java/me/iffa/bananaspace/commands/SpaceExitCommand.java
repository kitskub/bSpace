// Package Declaration
package me.iffa.bananaspace.commands;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceSpoutHandler;
import me.iffa.bananaspace.economy.Economy;
import me.iffa.bananaspace.api.SpaceLangHandler;
import me.iffa.bananaspace.api.SpaceMessageHandler;
import me.iffa.bananaspace.api.SpacePlayerHandler;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Spout Imports
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Represents "/space back".
 * 
 * @author iffa
 */
public class SpaceExitCommand extends SpaceCommand {
    // Variables
    public static Map<Player, Location> enterDest = new HashMap<Player, Location>();

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
                if (BananaSpace.pm.getPlugin("Register") != null && !Economy.exitCommand(player)) {
                    SpaceMessageHandler.sendNotEnoughMoneyMessage(player);
                    return;
                }
                enterDest.put(player, player.getLocation());
                Location location;
                if (SpaceEnterCommand.exitDest.containsKey(player)) {
                    location = SpaceEnterCommand.exitDest.get(player);
                    SpaceMessageHandler.debugPrint(Level.INFO, "Teleported player '" + player.getName() + "' out of space.");
                    player.teleport(location);
                    SpaceSpoutHandler.setOrReset(plugin, (SpoutPlayer)player, location);
                    return;
                } else {
                    SpaceEnterCommand.exitDest.put(player, Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
                    sender.sendMessage(ChatColor.RED + SpaceLangHandler.getNoExitFoundMessage(1));
                    sender.sendMessage(ChatColor.RED + SpaceLangHandler.getNoExitFoundMessage(2));
                    return;
                }
            } else {
                SpaceMessageHandler.sendNoPermissionMessage(player);
                return;
            }
        } else {
            player.sendMessage(ChatColor.RED + SpaceLangHandler.getNotInSpaceMessage());
            return;
        }
    }
}