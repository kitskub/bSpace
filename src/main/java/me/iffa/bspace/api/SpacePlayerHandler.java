// Package Declaration
package me.iffa.bspace.api;

// Java Imports
import java.util.logging.Level;

// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Player related methods.
 * 
 * @author iffa
 */
public class SpacePlayerHandler {
    /**
     * Checks if a player has the specified permission node.
     * 
     * @param permission Permission node to check
     * @param player Player to check
     * 
     * @return true if the player has permission
     */
    public static boolean hasPermission(String permission, Player player) {
        if (player.hasPermission(permission)) {
            return true;
        }
        return false;
    }

    /**
     * Gives a player the specified space suit
     * 
     * @param armortype Diamond, chainmail, gold, iron, leather or null
     * @param player Player to give 
     */
    public static void giveSpaceSuit(String armortype, Player player) {
        Material helmet = Material.getMaterial(armortype.toUpperCase() + "_HELMET");
        Material chestplate = Material.getMaterial(armortype.toUpperCase() + "_CHESTPLATE");
        Material leggings = Material.getMaterial(armortype.toUpperCase() + "_LEGGINGS");
        Material boots = Material.getMaterial(armortype.toUpperCase() + "_BOOTS");
        if (helmet == null) {
            SpaceMessageHandler.print(Level.SEVERE, "Invalid armortype '" + armortype + "' in config!");
            player.sendMessage(ChatColor.RED + "Nag at server owner: Invalid armortype in bSpace config!");
            return;
        }
        player.getInventory().setHelmet(new ItemStack(helmet));
        player.getInventory().setChestplate(new ItemStack(chestplate));
        player.getInventory().setLeggings(new ItemStack(leggings));
        player.getInventory().setBoots(new ItemStack(boots));
    }

    /**
     * Constructor of SpacePlayerHandler.
     */
    private SpacePlayerHandler() {
    }
}
