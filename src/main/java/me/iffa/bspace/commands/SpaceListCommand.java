// Package Declaration
package me.iffa.bspace.commands;

// Java Imports
import java.util.ArrayList;
import java.util.List;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.handlers.LangHandler;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.PlayerHandler;
import me.iffa.bspace.handlers.WorldHandler;

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
        if (!PlayerHandler.hasPermission("bSpace.teleport.list", (Player) this.getSender())) {
            MessageHandler.sendNoPermissionMessage((Player) getSender());
            return;
        }
        if (WorldHandler.getSpaceWorlds().isEmpty()) {
            getSender().sendMessage(ChatColor.RED + LangHandler.getNoSpaceLoaded());
            return;
        }
        getSender().sendMessage(ChatColor.GOLD + Space.getPrefix() + " " + LangHandler.getListOfSpaceMessage());
        List<String> spaceWorlds = new ArrayList<String>();
        for (World world : WorldHandler.getSpaceWorlds()) {
            spaceWorlds.add(world.getName());
        }
        getSender().sendMessage(ChatColor.GRAY + spaceWorlds.toString().replace("]", "").replace("[", ""));
        return;
    }
}
