// Package Declaration
package me.iffa.bspace.commands;

// Java Imports
import java.util.ArrayList;
import java.util.List;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceLangHandler;
import me.iffa.bspace.api.SpaceMessageHandler;
import me.iffa.bspace.api.SpacePlayerHandler;
import me.iffa.bspace.api.SpaceWorldHandler;

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
        if (!SpacePlayerHandler.hasPermission("bSpace.teleport.list", (Player) this.getSender())) {
            SpaceMessageHandler.sendNoPermissionMessage((Player) getSender());
            return;
        }
        if (SpaceWorldHandler.getSpaceWorlds().isEmpty()) {
            getSender().sendMessage(ChatColor.RED + SpaceLangHandler.getNoSpaceLoaded());
            return;
        }
        getSender().sendMessage(ChatColor.GOLD + Space.getPrefix() + " " + SpaceLangHandler.getListOfSpaceMessage());
        List<String> spaceWorlds = new ArrayList<String>();
        for (World world : SpaceWorldHandler.getSpaceWorlds()) {
            spaceWorlds.add(world.getName());
        }
        getSender().sendMessage(ChatColor.GRAY + spaceWorlds.toString().replace("]", "").replace("[", ""));
        return;
    }
}
