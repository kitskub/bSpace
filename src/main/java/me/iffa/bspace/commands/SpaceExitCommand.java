// Package Declaration
package me.iffa.bspace.commands;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.economy.Economy;
import me.iffa.bspace.handlers.LangHandler;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.PlayerHandler;
import me.iffa.bspace.handlers.SpoutHandler;
import me.iffa.bspace.handlers.WorldHandler;

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
        Player player = (Player) getSender();
        if (WorldHandler.isInAnySpace(player)) {
            if (PlayerHandler.hasPermission("bSpace.teleport.exit", player)) {
                if (!Economy.exitCommand(player)) {
                    MessageHandler.sendNotEnoughMoneyMessage(player);
                    return;
                }
                enterDest.put(player, player.getLocation());
                Location location;
                if (SpaceEnterCommand.exitDest.containsKey(player)) {
                    location = SpaceEnterCommand.exitDest.get(player);
                    MessageHandler.debugPrint(Level.INFO, "Teleported player '" + player.getName() + "' out of space.");
                    player.teleport(location);
                    if (Bukkit.getPluginManager().getPlugin("Spout") != null) {
                        SpoutHandler.setOrReset(getPlugin(), (SpoutPlayer)player, location);
                    }
                    return;
                } else {
                    SpaceEnterCommand.exitDest.put(player, Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
                    getSender().sendMessage(ChatColor.RED + LangHandler.getNoExitFoundMessage(1));
                    getSender().sendMessage(ChatColor.RED + LangHandler.getNoExitFoundMessage(2));
                    return;
                }
            } else {
                MessageHandler.sendNoPermissionMessage(player);
                return;
            }
        } else {
            player.sendMessage(ChatColor.RED + LangHandler.getNotInSpaceMessage());
            return;
        }
    }
}