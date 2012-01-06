// Package Declaration
package me.iffa.bspace.commands;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceLangHandler;
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpacePlayerHandler;
import me.iffa.bspace.api.SpaceWorldHandler;
import me.iffa.bspace.economy.Economy;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

/**
 * Represents "/space enter [spaceworld]".
 * 
 * @author iffa
 */
public class SpaceEnterCommand extends SpaceCommand {
    // Variables
    public static Map<Player, Location> exitDest = new HashMap<Player, Location>();

    /**
     * Constructor of SpaceEnterCommand.
     * 
     * @param plugin bSpace instance
     * @param sender Command sender
     * @param args Command arguments
     */
    public SpaceEnterCommand(Space plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    /**
     * Does the command.
     */
    @Override
    public void command() {
        Player player = (Player) this.getSender();
        if (getArgs().length == 1) {
            if (SpacePlayerHandler.hasPermission("bSpace.teleport.enter", player)) {
                if (SpaceWorldHandler.getSpaceWorlds().isEmpty()) {
                    player.sendMessage(ChatColor.RED + SpaceLangHandler.getNoSpaceLoaded());
                    return;
                }
                if (SpaceWorldHandler.getSpaceWorlds().get(0) == player.getWorld()) {
                    player.sendMessage(ChatColor.RED + SpaceLangHandler.getAlreadyInThatWorldMessage());
                    SpaceMessageHandler.debugPrint(Level.INFO, player.getName() + "tried to use /space enter, but he was already in that space world.");
                    return;
                }
                if (!Economy.enterCommand(player)) {
                    SpaceMessageHandler.sendNotEnoughMoneyMessage(player);
                    return;
                }
                exitDest.put(player, player.getLocation());
                Location location;
                if (SpaceExitCommand.enterDest.containsKey(player)) {
                    location = SpaceExitCommand.enterDest.get(player);
                } else {
                    location = SpaceWorldHandler.getSpaceWorlds().get(0).getSpawnLocation();
                }
                SpaceMessageHandler.debugPrint(Level.INFO, "Teleported player '" + player.getName() + "' to space.");
                player.teleport(location, TeleportCause.COMMAND);
                return;
            }
            SpaceMessageHandler.sendNoPermissionMessage(player);
            return;
        } else if (getArgs().length >= 2) {
            if (SpacePlayerHandler.hasPermission("bSpace.teleport.enter", player)) {
                if (!Economy.enterCommand(player)) {
                    SpaceMessageHandler.sendNotEnoughMoneyMessage(player);
                    return;
                }
                if (Bukkit.getServer().getWorld(getArgs()[1]) == null) {
                    player.sendMessage(ChatColor.RED + SpaceLangHandler.getWorldNotFoundMessage());
                    return;
                }
                if (!SpaceWorldHandler.isSpaceWorld(Bukkit.getServer().getWorld(this.getArgs()[1]))) {
                    player.sendMessage(ChatColor.RED + SpaceLangHandler.getWorldNotSpaceMessage());
                    return;
                }
                if (Bukkit.getServer().getWorld(getArgs()[1]) == player.getWorld()) {
                    player.sendMessage(ChatColor.RED + SpaceLangHandler.getAlreadyInThatWorldMessage());
                    return;
                }
                exitDest.put(player, player.getLocation());
                Location location;
                if (SpaceExitCommand.enterDest.containsKey(player)) {
                    location = SpaceExitCommand.enterDest.get(player);
                } else {
                    location = Bukkit.getServer().getWorld(getArgs()[1]).getSpawnLocation();
                }
                SpaceMessageHandler.debugPrint(Level.INFO, "Teleported player '" + player.getName() + "' to space.");
                player.teleport(location, TeleportCause.COMMAND);
                return;
            }
        }
        SpaceMessageHandler.sendNoPermissionMessage(player);
    }
}