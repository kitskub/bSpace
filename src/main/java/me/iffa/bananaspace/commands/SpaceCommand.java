// Package Declaration
package me.iffa.bananaspace.commands;

// Java Imports
import java.util.HashMap;
import java.util.Map;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.event.misc.SpaceCommandEvent;

// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CommandExecutor for 'space'.
 * 
 * @author iffa
 */
public class SpaceCommand implements CommandExecutor {
    // Variables
    private Map<Player, Location> exitDest;
    private Map<Player, Location> enterDest;
    private BananaSpace plugin;

    /**
     * Constructor for SpaceCommand.
     * 
     * @param plugin BananaSpace
     */
    public SpaceCommand(BananaSpace plugin) {
        this.plugin = plugin;
        exitDest = new HashMap<Player, Location>();
        enterDest = new HashMap<Player, Location>();
    }

    /**
     * Called when a player uses the command 'space'.
     * 
     * @param sender CommandSender
     * @param command Command
     * @param label Command label
     * @param args Command arguments
     * 
     * @return true if no usage information should be sent, i.e command was successfull
     */
    public boolean onCommand(CommandSender sender, Command command,
            String label, String args[]) {
        // Notify listeners.
        SpaceCommandEvent e = new SpaceCommandEvent("SpaceCommandEvent", sender, args);
        if (e.isCancelled()) {
            BananaSpace.debugLog("External plugin cancelled SpaceCommandEvent.");
            return true;
        }
        if (!BananaSpace.worldHandler.getStartupLoaded() || BananaSpace.worldHandler.getUsingMV()) {
            BananaSpace.debugLog("Cancelled event because no worlds were loaded on startup or MV is being used.");
            return true;
        }
        if (!(sender instanceof Player)) {
            BananaSpace.debugLog("An unknown person tried to use the command.");
            return true;
        }
        Player player = (Player) sender;
        if (BananaSpace.getPlayerHandler().hasPermission("bananaspace.teleport.enter", player) || BananaSpace.getPlayerHandler().hasPermission("bananaspace.teleport.exit", player)) {
            if (args.length == 2 && args[0].equalsIgnoreCase("enter")) {
                if (args.length == 2 && args[0].equalsIgnoreCase("enter")) {
                if (BananaSpace.getPlayerHandler().hasPermission("bananaspace.teleport.enter", player)) {
                    if(!plugin.getEconomy().enterCommand(player)){
                        player.sendMessage("You don't have enough money");
                        return true;
                    }
                    if (plugin.getServer().getWorld(args[1]) == null) {
                        player.sendMessage(ChatColor.RED + "The world was not found!");
                        return true;
                    }
                    if (!BananaSpace.worldHandler.isSpaceWorld(plugin.getServer().getWorld(args[1]))) {
                        player.sendMessage(ChatColor.RED + "The world is not a space world!");
                        return true;
                    }
                    if (plugin.getServer().getWorld(args[1]) == player.getWorld()) {
                        player.sendMessage(ChatColor.RED + "You are already in that space world!");
                        return true;
                    }
                    exitDest.put(player, player.getLocation());
                    Location location;
                    if (enterDest.containsKey(player)) {
                        location = enterDest.get(player);
                    } else {
                        location = plugin.getServer().getWorld(args[1]).getSpawnLocation();
                    }
                    BananaSpace.debugLog("Teleported player '" + player.getName() + "' to space.");
                    player.teleport(location);
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "You don't have permission!");
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("back") && sender instanceof Player) {
                if (BananaSpace.worldHandler.isInAnySpace(player)) {
                    Location location;
                
                    if (BananaSpace.getPlayerHandler().hasPermission("bananaspace.teleport.exit", player)) {
                        if(!plugin.getEconomy().exitCommand(player)){
                            player.sendMessage("You don't have enough money");
                            return true;
                        }
                    }
                        enterDest.put(player, player.getLocation());
                        if (exitDest.containsKey(player)) {
                            location = exitDest.get(player);
                            BananaSpace.debugLog("Teleported player '" + player.getName() + "' out of space.");
                            player.teleport(location);
                            return true;
                        } else {
                            exitDest.put(player, plugin.getServer().getWorlds().get(0).getSpawnLocation());
                            sender.sendMessage(ChatColor.RED + "Exit destination not found, setting to default world spawn location.");
                            sender.sendMessage(ChatColor.RED + "Type '/space back' again to go there.");
                            return true;
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have permission!");
                        return true;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in space!");
                    return true;
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("list") && sender instanceof Player) {
                if (!BananaSpace.getPlayerHandler().hasPermission("bananaspace.teleport.list", (Player) sender)) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission!");
                    return true;
                }
                sender.sendMessage(ChatColor.GREEN + BananaSpace.prefix + " List of spaceworlds:");
                for(World world : BananaSpace.getWorldHandler().getSpaceWorlds()) {
                    sender.sendMessage(ChatColor.DARK_GREEN + "- " + world.getName());
                }
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.GREEN + "[BananaSpace] Usage:");
                sender.sendMessage(ChatColor.GREEN + "/space enter [world] - Go to space (default world or given one)");
                sender.sendMessage(ChatColor.GREEN + "/space back - Leave space or go back where you were in space");
                sender.sendMessage(ChatColor.GREEN + "/space help - Brings up this help message");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "[BananaSpace] Usage:");
                sender.sendMessage(ChatColor.RED + "/space enter [world] - Go to space (default world or given one)");
                sender.sendMessage(ChatColor.RED + "/space back - Leave space or go back where you were in space");
                sender.sendMessage(ChatColor.RED + "/space help - Brings up this help message");
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "You don't have permission!");
        return true;
    }
}
