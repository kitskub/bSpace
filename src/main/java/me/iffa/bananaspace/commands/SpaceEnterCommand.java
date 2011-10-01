// Package Declaration
package me.iffa.bananaspace.commands;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceMessageHandler;

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
    public static Map<Player, Location> exitDest = new HashMap<Player, Location>();;

    /**
     * Constructor of SpaceEnterCommand.
     * 
     * @param plugin BananaSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceEnterCommand(BananaSpace plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        Player player = (Player) this.sender;
        if (args.length == 1) {
            if (BananaSpace.getPlayerHandler().hasPermission("bananaspace.teleport.enter", player)) {
                if (BananaSpace.worldHandler.getSpaceWorlds().get(0) == player.getWorld()) {
                    player.sendMessage(ChatColor.RED + "You are already in that space world!");
                    SpaceMessageHandler.debugPrint(Level.INFO, "Someone tried to use /space enter, but he was already in that space world.");
                    return;
                }
                if(!plugin.getEconomy().enterCommand(player)){
                        SpaceMessageHandler.sendNotEnoughMoneyMessage(player);
                        return;
                }
                exitDest.put(player, player.getLocation());
                Location location;
                if (SpaceExitCommand.enterDest.containsKey(player)) {
                    location = SpaceExitCommand.enterDest.get(player);
                } else {
                    location = BananaSpace.worldHandler.getSpaceWorlds().get(0).getSpawnLocation();
                }
                SpaceMessageHandler.debugPrint(Level.INFO, "Teleported player '" + player.getName() + "' to space.");
                player.teleport(location);
                return;
            }
            SpaceMessageHandler.sendNoPermissionMessage(player);
            return;
        } else if (args.length == 2) {
            if (BananaSpace.getPlayerHandler().hasPermission("bananaspace.teleport.enter", player)) {
                if(!plugin.getEconomy().enterCommand(player)){
                        SpaceMessageHandler.sendNotEnoughMoneyMessage(player);
                        return;
                }
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
                SpaceMessageHandler.debugPrint(Level.INFO, "Teleported player '" + player.getName() + "' to space.");
                player.teleport(location);
                return;
            }
        }
        SpaceMessageHandler.sendNoPermissionMessage(player);
        return;
    }
}