// Package Declaration
package me.iffa.bananaspace.economy;

// Register Imports
import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Method.MethodAccount;
import com.nijikokun.register.payment.Methods;

// BananaSpace Imports
import java.util.logging.Level;
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.config.SpaceConfig;

// Bukkit Imports
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
    private static BananaSpace plugin;
    private boolean use;

    /**
     * Constructor of Economy.
     * 
     * @param plugin BananaSpace instance
     */
    public Economy(BananaSpace plugin) {
        Economy.plugin = plugin;
        use = true;
        getMethod();
        BananaSpace.getMessageHandler().debugPrint(Level.INFO, "Hooked into " + method.getName());
    }
    
    /**
     * Constructor of Economy #2.
     */
    public Economy() {
        use = false;
    }

    /**
     * Checks the economy enabled-state of the plugin.
     * 
     * @param plugin BananaSpace instance
     * 
     * @return True if economy is enabled
     */
    public static boolean checkEconomy(BananaSpace plugin) {
        if (SpaceConfig.getConfig().getBoolean("economy.enabled", true)) {
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
    public boolean enter(Player player) {
        if (use == false) {
            return true;
        }
        if (method.hasAccount(player.getName())) {
            int amount = SpaceConfig.getConfig().getInt("economy.entercost", 20);
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
    public boolean exit(Player player) {
        if (use == false) {
            return true;
        }
        if (method.hasAccount(player.getName())) {
            int amount = SpaceConfig.getConfig().getInt("economy.exitcost", 20);
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
    public boolean enterCommand(Player player) {
        if (use == false) {
            return true;
        }
        if (method.hasAccount(player.getName())) {
            int amount = SpaceConfig.getConfig().getInt("economy.entercommandcost", 20);
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
    public boolean exitCommand(Player player) {
        if (use == false) {
            return true;
        }
        if (method.hasAccount(player.getName())) {
            int amount = SpaceConfig.getConfig().getInt("economy.exitcommandcost", 20);
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
    private boolean subtract(Player player, int amount) {
        if (BananaSpace.getPlayerHandler().hasPermission("bananspace.economy.exempt", player)) {
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
            Methods.setMethod(plugin.getServer().getPluginManager());
            method = Methods.getMethod();
        }
        return method;
    }
}
