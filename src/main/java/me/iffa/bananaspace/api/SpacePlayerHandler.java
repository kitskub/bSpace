// Package Declaration
package me.iffa.bananaspace.api;

// Bukkit Imports
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Player related methods.
 * 
 * @author iffa
 * 
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
        if (player.hasPermission(permission)) {//Add OP fallback?
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
        if (armortype.equalsIgnoreCase("diamond")) {
            player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
            player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
            player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        } else if (armortype.equalsIgnoreCase("chainmail")) {
            player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
            player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
            player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
        } else if (armortype.equalsIgnoreCase("gold")) {
            player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS, 1));
            player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
            player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
        } else if (armortype.equalsIgnoreCase("iron")) {
            player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
            player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
            player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        } else if (armortype.equalsIgnoreCase("leather")) {
            player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
            player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
            player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
        } else if (armortype.equalsIgnoreCase("null")) {
            player.getInventory().setBoots(new ItemStack(Material.AIR, 1));
            player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
            player.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        }
    }

    /**
     * Constructor of SpacePlayerHandler.
     */
    private SpacePlayerHandler() {
    }
}
