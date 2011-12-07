// Package Declaration
package me.iffa.bspace.commands;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceSpoutHandler;
import me.iffa.bspace.economy.Economy;
import me.iffa.bspace.api.SpaceLangHandler;
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpacePlayerHandler;

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
     * @param plugin bSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceExitCommand(Space plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        Player player = (Player) sender;
        if (Space.getWorldHandler().isInAnySpace(player)) {
            if (SpacePlayerHandler.hasPermission("bSpace.teleport.exit", player)) {
                if (Bukkit.getPluginManager().getPlugin("Register") != null && !Economy.exitCommand(player)) {
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