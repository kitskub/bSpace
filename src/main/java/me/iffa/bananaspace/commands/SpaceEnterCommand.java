// Package Declaration
package me.iffa.bananaspace.commands;

// Java Imports
import java.util.HashMap;
import java.util.Map;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;

// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Represents "/space enter [spaceworld]".
 * 
 * @author iffa
 */
public class SpaceEnterCommand extends SpaceCommand {
    // Variables
    public static Map<Player, Location> exitDest;

    /**
     * Constructor of SpaceEnterCommand.
     * 
     * @param plugin BananaSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceEnterCommand(BananaSpace plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        exitDest = new HashMap<Player, Location>();
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        Player player = (Player) sender;
        if (args.length == 1) {
            if (BananaSpace.getPlayerHandler().hasPermission("bananaspace.teleport.enter", player)) {
                if (BananaSpace.worldHandler.getSpaceWorlds().get(0) == player.getWorld()) {
                    player.sendMessage(ChatColor.RED + "You are already in that space world!");
                    BananaSpace.debugLog("Someone tried to use /space enter, but he was already in that space world.");
                    return;
                }
                exitDest.put(player, player.getLocation());
                Location location;
                if (SpaceExitCommand.enterDest.containsKey(player)) {
                    location = SpaceExitCommand.enterDest.get(player);
                } else {
                    location = BananaSpace.worldHandler.getSpaceWorlds().get(0).getSpawnLocation();
                }
                BananaSpace.debugLog("Teleported player '" + player.getName() + "' to space.");
                player.teleport(location);
                return;
            }
            sender.sendMessage(ChatColor.RED + "You don't have permission!");
            return;
        } else if (args.length == 2) {
            if (BananaSpace.getPlayerHandler().hasPermission("bananaspace.teleport.enter", player)) {
                if (plugin.getServer().getWorld(args[1]) == null) {
                    player.sendMessage(ChatColor.RED + "The world was not found!");
                    return;
                }
                if (!BananaSpace.worldHandler.isSpaceWorld(plugin.getServer().getWorld(args[1]))) {
                    player.sendMessage(ChatColor.RED + "The world is not a space world!");
                    return;
                }
                if (plugin.getServer().getWorld(args[1]) == player.getWorld()) {
                    player.sendMessage(ChatColor.RED + "You are already in that space world!");
                    return;
                }
                exitDest.put(player, player.getLocation());
                Location location;
                if (SpaceExitCommand.enterDest.containsKey(player)) {
                    location = SpaceExitCommand.enterDest.get(player);
                } else {
                    location = plugin.getServer().getWorld(args[1]).getSpawnLocation();
                }
                BananaSpace.debugLog("Teleported player '" + player.getName() + "' to space.");
                player.teleport(location);
                return;
            }
        }
        sender.sendMessage(ChatColor.RED + "You don't have permission!");
        return;
    }
}