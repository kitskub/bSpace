// Package Declaration
package me.iffa.bananaspace.commands;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.event.misc.SpaceCommandEvent;

// Bukkit Imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles sending the command ahead to the SpaceCommand-objects.
 * 
 * @author iffa
 */
public class SpaceCommandHandler implements CommandExecutor {
    // Variables
    private BananaSpace plugin;

    /**
     * Constructor for SpaceCommandHandler.
     * 
     * @param plugin BananaSpace
     */
    public SpaceCommandHandler(BananaSpace plugin) {
        this.plugin = plugin;
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
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        /* Notify listeners start */
        SpaceCommandEvent e = new SpaceCommandEvent("SpaceCommandEvent", sender, args);
        if (e.isCancelled()) {
            BananaSpace.debugLog("External plugin cancelled SpaceCommandEvent using the API.");
            return true;
        }
        /* Notify listeners end */
        // Safety checks:
        //  - Commands not available if no worlds are loaded or Multiverse is used
        //  - Sender must be a player
        if (!BananaSpace.worldHandler.getStartupLoaded() || BananaSpace.worldHandler.getUsingMV()) {
            BananaSpace.debugLog("Cancelled event because no worlds were loaded on startup or MV is being used.");
            return true;
        }
        if (!(sender instanceof Player)) {
            BananaSpace.debugLog("An unknown person tried to use the command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("enter")) {
            // SpaceEnterCommand, 1 argument
            SpaceEnterCommand enterCommand = new SpaceEnterCommand(plugin, sender, args);
            enterCommand.command();
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("enter")) {
            // SpaceEnterCommand, 2 arguments
            SpaceEnterCommand enterCommand = new SpaceEnterCommand(plugin, sender, args);
            enterCommand.command();
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("back")) {
            // SpaceExitCommand, 1 argument
            SpaceExitCommand exitCommand = new SpaceExitCommand(plugin, sender, args);
            exitCommand.command();
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            // SpaceListCommand, 1 argument
            SpaceListCommand listCommand = new SpaceListCommand(plugin, sender, args);
            listCommand.command();
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            // SpaceHelpCommand, 1 argument
            SpaceHelpCommand helpCommand = new SpaceHelpCommand(plugin, sender, args);
            helpCommand.command();
            return true;
        } else {
            // SpaceHelpCommand, 1 argument
            SpaceHelpCommand helpCommand = new SpaceHelpCommand(plugin, sender, args);
            helpCommand.command();
            return true;
        }
    }
}