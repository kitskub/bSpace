// Package Declaration
package me.iffa.bspace.api;

// Java Imports
import java.util.logging.Level;

// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
     * Checks if a player has a spacesuit (of the given armortype)
     * 
     * @param p Player
     * @param armortype Can be diamond, chainmail, gold, iron or leather
     * 
     * @return true if the player has a spacesuit of the type
     */
    public static boolean hasSuit(Player p, String armortype) {
        if (armortype.equalsIgnoreCase("diamond")) {
            // Diamond
            if (p.getInventory().getBoots().getType() != Material.DIAMOND_BOOTS || p.getInventory().getChestplate().getType() != Material.DIAMOND_CHESTPLATE || p.getInventory().getLeggings().getType() != Material.DIAMOND_LEGGINGS) {
                return false;
            }
            return true;
        } else if (armortype.equalsIgnoreCase("chainmail")) {
            // Chainmail
            if (p.getInventory().getBoots().getType() != Material.CHAINMAIL_BOOTS || p.getInventory().getChestplate().getType() != Material.CHAINMAIL_CHESTPLATE || p.getInventory().getLeggings().getType() != Material.CHAINMAIL_LEGGINGS) {
                return false;
            }
            return true;
        } else if (armortype.equalsIgnoreCase("gold")) {
            // Gold
            if (p.getInventory().getBoots().getType() != Material.GOLD_BOOTS || p.getInventory().getChestplate().getType() != Material.GOLD_CHESTPLATE || p.getInventory().getLeggings().getType() != Material.GOLD_LEGGINGS) {
                return false;
            }
            return true;
        } else if (armortype.equalsIgnoreCase("iron")) {
            // Iron
            if (p.getInventory().getBoots().getType() != Material.IRON_BOOTS || p.getInventory().getChestplate().getType() != Material.IRON_CHESTPLATE || p.getInventory().getLeggings().getType() != Material.IRON_LEGGINGS) {
                return false;
            }
            return true;
        } else if (armortype.equalsIgnoreCase("leather")) {
            // Leather
            if (p.getInventory().getBoots().getType() != Material.LEATHER_BOOTS || p.getInventory().getChestplate().getType() != Material.LEATHER_CHESTPLATE || p.getInventory().getLeggings().getType() != Material.LEATHER_LEGGINGS) {
                return false;
            }
            return true;
        }
        return false;
    }
        
    /**
     * Checks if a player should start suffocating.
     * 
     * @param player Player
     * @return if needs suffocation
     */
    public static boolean checkNeedsSuffocation(Player player) {
        SuitCheck suit = null;
        if (SpaceConfigHandler.getRequireHelmet(player.getWorld()) && SpaceConfigHandler.getRequireSuit(player.getWorld())) {
            suit = SuitCheck.BOTH;
        } else if (SpaceConfigHandler.getRequireHelmet(player.getWorld())) {
            suit = SuitCheck.HELMET_ONLY;
        } else if (SpaceConfigHandler.getRequireSuit(player.getWorld())) {
            suit = SuitCheck.SUIT_ONLY;
        } else{
            return false;
        }
        if (suit == SuitCheck.SUIT_ONLY) {
            if (hasSuit(player, SpaceConfigHandler.getArmorType())){
                return false;
            }
        }
        else if (suit == SuitCheck.HELMET_ONLY) {
            if(player.getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock()){
                return false;
            }
        } else if (suit == SuitCheck.BOTH) {
            if(player.getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock() 
                    && hasSuit(player, SpaceConfigHandler.getArmorType())){
                return false;
            }          
        }
        return true;
    }
    
    public static boolean insideArea(Player player){
        boolean insideArea = insideArea(player.getLocation());
        return insideArea;
    }
    
    //Used to check if a location is in an area.
    public static boolean insideArea(Location loc){
        int i = 0;
        Block block = loc.getBlock().getRelative(BlockFace.UP);
        boolean insideArea = false;
        while (i < SpaceConfigHandler.getRoomHeight(loc.getWorld())) {
            if (block.getTypeId() != 0) {
                insideArea = true;
                i = 0;
                break;
            }
            i++;
            block = block.getRelative(BlockFace.UP);
        }
        return insideArea;
    }
    
    public static void giveSuitOrHelmet(Player player){
        //Suit and helmet giving
        if (SpaceConfigHandler.isHelmetGiven()) {
            player.getInventory().setHelmet(
                    new ItemStack(SpaceConfigHandler.getHelmetBlock(), 1));
        }
        if (SpaceConfigHandler.isSuitGiven()) {
            SpacePlayerHandler.giveSpaceSuit(SpaceConfigHandler.getArmorType(), player);
        }
    }
    
    public static void removeSuitOrHelmet(Player player){
        if (SpaceConfigHandler.isHelmetGiven()) {
            player.getInventory().setHelmet(new ItemStack(0, 1));
        }
        if (SpaceConfigHandler.isSuitGiven()) {
            SpacePlayerHandler.giveSpaceSuit("air", player);
        }    
    }
    /**
     * Enum to make things easier.
     */
    private enum SuitCheck {
        // Enums
        HELMET_ONLY,
        SUIT_ONLY,
        BOTH;
    }

    /**
     * Constructor of SpacePlayerHandler.
     */
    private SpacePlayerHandler() {
    }
}
