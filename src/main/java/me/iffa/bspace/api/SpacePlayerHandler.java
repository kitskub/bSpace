// Package Declaration
package me.iffa.bspace.api;

// Java Imports
import java.util.logging.Level;

// Bukkit Imports
import me.iffa.bspace.handlers.ConfigHandler;
import me.iffa.bspace.handlers.MessageHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Player related methods.
 * External use only
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
     * @param helmetid helmet id
     * @param player Player to give 
     */
    public static void giveSpaceSuit(String armortype, int helmetid, Player player) {
        Material helmet = Material.getMaterial(helmetid);
        Material chestplate = Material.getMaterial(armortype.toUpperCase() + "_CHESTPLATE");
        Material leggings = Material.getMaterial(armortype.toUpperCase() + "_LEGGINGS");
        Material boots = Material.getMaterial(armortype.toUpperCase() + "_BOOTS");
        if (helmet == null){
            MessageHandler.print(Level.SEVERE, "Invalid helmet '" + helmetid + "' in config!");
            player.sendMessage(ChatColor.RED + "Nag at server owner: Invalid helmet in bSpace config!");
            return;
        }
        if (chestplate == null) {
            MessageHandler.print(Level.SEVERE, "Invalid armortype '" + armortype + "' in config!");
            player.sendMessage(ChatColor.RED + "Nag at server owner: Invalid armortype in bSpace config!");
            return;
        }
        player.getInventory().setHelmet(new ItemStack(helmet));
        player.getInventory().setChestplate(new ItemStack(chestplate));
        player.getInventory().setLeggings(new ItemStack(leggings));
        player.getInventory().setBoots(new ItemStack(boots));
    }
    
    /**
     * Gives a player the specified helmet
     * 
     * @param id helmet id
     * @param player Player to give 
     */
    public static void giveHelmet(int id, Player player){
        player.getInventory().setHelmet(new ItemStack(id, 1));
    }
    /**
     * Checks if a player has a spacesuit (of the given armortype)
     * 
     * @param p Player
     * @param armortype Can be diamond, chainmail, gold, iron or leather
     * 
     * @return true if the player has a spacesuit of the type
     */
    public static boolean hasSuit(Player p, String armortype, int helmetid) {
        Material playerChest = p.getInventory().getChestplate().getType();
        Material playerLeg = p.getInventory().getLeggings().getType();
        Material playerBoot = p.getInventory().getBoots().getType();
        
        if (armortype.equalsIgnoreCase("diamond")) {
            // Diamond
            if (playerBoot != Material.DIAMOND_BOOTS 
                    || playerChest != Material.DIAMOND_CHESTPLATE 
                    || playerLeg != Material.DIAMOND_LEGGINGS) {
                return false;
            }
        } else if (armortype.equalsIgnoreCase("chainmail")) {
            // Chainmail
            if (playerBoot != Material.CHAINMAIL_BOOTS 
                    || playerChest != Material.CHAINMAIL_CHESTPLATE 
                    || playerLeg != Material.CHAINMAIL_LEGGINGS) {
                return false;
            }
        } else if (armortype.equalsIgnoreCase("gold")) {
            // Gold
            if (playerBoot != Material.GOLD_BOOTS 
                    || playerChest != Material.GOLD_CHESTPLATE 
                    || playerLeg != Material.GOLD_LEGGINGS) {
                return false;
            }
        } else if (armortype.equalsIgnoreCase("iron")) {
            // Iron
            if (playerBoot != Material.IRON_BOOTS 
                    || playerChest != Material.IRON_CHESTPLATE 
                    || playerLeg != Material.IRON_LEGGINGS) {
                return false;
            }
        } else if (armortype.equalsIgnoreCase("leather")) {
            // Leather
            if (playerBoot != Material.LEATHER_BOOTS 
                    || playerChest != Material.LEATHER_CHESTPLATE 
                    || playerLeg != Material.LEATHER_LEGGINGS) {
                return false;
            }
        }
        return hasHelmet(p, helmetid); //Made it this far, has bottom 3
    }
    
    
    /**
     * 
     * @param p Player
     * @param id helmetid
     * @return true if player's helmet id is <code>id</code>
     */
    public static boolean hasHelmet(Player p, int id) {
        return p.getInventory().getHelmet().getTypeId()==id;
    }
        
    /**
     * Checks if a player should start suffocating.
     * 
     * @param player Player
     * @return if needs suffocation
     */
    public static boolean checkNeedsSuffocation(Player player) {
        SuitCheck suit = null;
        String id = ConfigHandler.getID(player.getWorld());
        if (ConfigHandler.getRequireSuit(id)) {
            suit = SuitCheck.FULL_SUIT;
        } else if (ConfigHandler.getRequireHelmet(id)) {
            suit = SuitCheck.HELMET_ONLY;
        } else{
            return false;
        }
        if (suit == SuitCheck.FULL_SUIT) {
            if(hasSuit(player, ConfigHandler.getArmorType(), ConfigHandler.getHelmetBlock())){
                return false;
            }          
        }
        else if (suit == SuitCheck.HELMET_ONLY) {
            if(hasHelmet(player,ConfigHandler.getHelmetBlock())){
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
        String id = ConfigHandler.getID(loc.getWorld());
        while (i < ConfigHandler.getRoomHeight(id)) {
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
        if (ConfigHandler.isSuitGiven()) {
            SpacePlayerHandler.giveSpaceSuit(ConfigHandler.getArmorType(), ConfigHandler.getHelmetBlock(), player);
            return;
        }
        if (ConfigHandler.isHelmetGiven()) {
            giveHelmet(ConfigHandler.getHelmetBlock(), player);
        }       
    }
    
    public static void removeSuitOrHelmet(Player player){
        if (ConfigHandler.isSuitGiven()) {
            removeSuit(player);
        } 
        if (ConfigHandler.isHelmetGiven()) {
            removeHelmet(player);
        }
   
    }
    
    public static void removeSuit(Player player){
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }
    
    public static void removeHelmet(Player player){
        player.getInventory().setHelmet(null);
    }
    /**
     * Enum to make things easier.
     */
    private enum SuitCheck {
        // Enums
        HELMET_ONLY,
        FULL_SUIT;
    }

    /**
     * Constructor of SpacePlayerHandler.
     */
    protected SpacePlayerHandler() {
    }
}
