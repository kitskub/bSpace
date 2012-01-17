// Package Declaration
package me.iffa.bspace.economy;

// Register Imports
import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Method.MethodAccount;
import com.nijikokun.register.payment.Methods;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.config.SpaceConfig.Defaults;
import me.iffa.bspace.config.SpaceConfig;
import me.iffa.bspace.config.SpaceConfig.ConfigFile;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.PlayerHandler;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Handles economy using Register.
 * 
 * @author kitskub
 * @author iffa
 */
public class Economy {
    // Variables
    private static Method method;
    private static boolean use = false;

    /**
     * Constructor of Economy.
     */
    public Economy() {
        use = true;
        getMethod();
        MessageHandler.debugPrint(Level.INFO, "Hooked into " + method.getName());
    }

    /**
     * Checks the economy enabled-state of the plugin.
     * 
     * @return True if economy is enabled
     */
    public static boolean checkEconomy() {
        if (SpaceConfig.getConfig(ConfigFile.CONFIG).getBoolean("economy.enabled", (Boolean) Defaults.ECONOMY_ENABLED.getDefault())) {
            return (getMethod() != null);
        }
        return false;
    }

    /**
     * Checks the cost for entering (general).
     * 
     * @param player Player
     * 
     * @return True if the player has enough money to enter
     */
    public static boolean enter(Player player) { 
        if (use == false) {
            return true;
        }
        if(!checkRegister()) {
            return false;
        }
        if (method.hasAccount(player.getName())) {
            int amount = SpaceConfig.getConfig(ConfigFile.CONFIG).getInt("economy.entercost", (Integer) Defaults.ENTER_COST.getDefault());
            return subtract(player, amount);
        } else {
            return false;
        }
    }

    /**
     * Checks the cost for exiting (general).
     * 
     * @param player Player
     * 
     * @return True if the player has enough money to exit 
     */
    public static boolean exit(Player player) {
        if (use == false) {
            return true;
        }
        if(!checkRegister()) {
            return false;
        }
        if (method.hasAccount(player.getName())) {
            int amount = SpaceConfig.getConfig(ConfigFile.CONFIG).getInt("economy.exitcost", (Integer) Defaults.EXIT_COST.getDefault());
            return subtract(player, amount);
        } else {
            return false;
        }

    }

    /**
     * Checks the cost for entering with the command.
     * 
     * @param player Player
     * 
     * @return True if the player has enough money to enter 
     */
    public static boolean enterCommand(Player player) {
        if (use == false) {
            return true;
        }
        if(!checkRegister()) {
            return false;
        }
        if (method.hasAccount(player.getName())) {
            int amount = SpaceConfig.getConfig(ConfigFile.CONFIG).getInt("economy.entercommandcost", (Integer) Defaults.ENTER_COMMAND_COST.getDefault());
            return subtract(player, amount);
        } else {
            return false;
        }

    }

    /**
     * Checks the cost for exiting with the command.
     * 
     * @param player Player
     * 
     * @return True if the player has enough money to exit
     */
    public static boolean exitCommand(Player player) {
        if (use == false) {
            return true;
        }
        if(!checkRegister()) {
            return false;
        }
        if (method.hasAccount(player.getName())) {
            int amount = SpaceConfig.getConfig(ConfigFile.CONFIG).getInt("economy.exitcommandcost", (Integer) Defaults.EXIT_COMMAND_COST.getDefault());
            return subtract(player, amount);
        } else {
            return false;
        }
    }

    /**
     * Subtracts from a player's balance if possible.
     * 
     * @param player Player to subtract from
     * @param amount Amount to subtract
     * 
     * @return True if subtract was successful
     */
    private static boolean subtract(Player player, int amount) {
        if (PlayerHandler.hasPermission("bananspace.economy.exempt", player)) {
            return true;
        }
        MethodAccount balance = method.getAccount(player.getName());
        if (!balance.hasEnough(amount)) {
            return false;
        }
        balance.subtract(amount);
        return true;
    }

    /**
     * Gets the payment method.
     * 
     * @return Payment method
     */
    public static Method getMethod() {
        if (method == null) {
            Methods.setMethod(Bukkit.getServer().getPluginManager());
            method = Methods.getMethod();
        }
        return method;
    }
    
    
    private static boolean checkRegister() {
        if(Bukkit.getPluginManager().getPlugin("Register") == null){
            MessageHandler.debugPrint(Level.WARNING, "Economy is enabled, but Register is not active! Disabling economy");
            use=false;
            return false;
        }
        return true;
    }

}
