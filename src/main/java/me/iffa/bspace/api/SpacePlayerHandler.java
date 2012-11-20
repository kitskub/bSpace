// Package Declaration
package me.iffa.bspace.api;

// Java Imports
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static void giveSpaceSuit(Player player) {
		String armortype = ConfigHandler.getArmorType();
		ItemStack chestplate;
		ItemStack leggings;
		ItemStack boots;
		if (!ConfigHandler.getArmorType().equalsIgnoreCase("id")) {
			Material chestplate_material;
			Material leggings_material;
			Material boots_material;
			chestplate_material = Material.matchMaterial(armortype + "_CHESTPLATE");
			leggings_material = Material.matchMaterial(armortype + "_LEGGINGS");
			boots_material = Material.matchMaterial(armortype + "_BOOTS");
			if (chestplate_material == null) {
				MessageHandler.print(Level.SEVERE, "Invalid armortype '" + armortype + "' in config!");
				player.sendMessage(ChatColor.RED + "Nag at server owner: Invalid armortype in bSpace config!");
				return;
			}
			chestplate = new ItemStack(chestplate_material);
			leggings = new ItemStack(leggings_material);
			boots = new ItemStack(boots_material);
		} else {
			chestplate = toItemStack(ConfigHandler.getChestPlate());
			leggings = toItemStack(ConfigHandler.getLeggings());
			boots = toItemStack(ConfigHandler.getBoots());
		}
        player.getInventory().setChestplate(new ItemStack(chestplate));
        player.getInventory().setLeggings(new ItemStack(leggings));
        player.getInventory().setBoots(new ItemStack(boots));
		giveHelmet(player);
    }
    
    /**
     * Gives a player the specified helmet
     * 
     * @param player Player to give 
     */
    public static void giveHelmet(Player player){
		ItemStack helmet = toItemStack(ConfigHandler.getHelmet());
		if (helmet == null){
			MessageHandler.print(Level.SEVERE, "Invalid helmet '" + ConfigHandler.getHelmet() + "' in config!");
			player.sendMessage(ChatColor.RED + "Nag at server owner: Invalid helmet in bSpace config!");
			return;
		}
        player.getInventory().setHelmet(helmet);
    }
    /**
     * Checks if a player has a spacesuit (of the given armortype)
     * 
     * @param p Player
     * @return true if the player has a spacesuit of the type
     */
    public static boolean hasSuit(Player p) {
        if(p.getInventory().getChestplate() == null 
                || p.getInventory().getLeggings() == null 
                || p.getInventory().getBoots() == null){
            return false;
        }
		String armortype = ConfigHandler.getArmorType();
        ItemStack playerChest = p.getInventory().getChestplate();
        ItemStack playerLeg = p.getInventory().getLeggings();
        ItemStack playerBoot = p.getInventory().getBoots();
		if (playerChest == null || playerLeg == null || playerBoot == null) return false;

        if (armortype.equalsIgnoreCase("diamond")) {
            // Diamond
            if (!playerBoot.getType().equals(Material.DIAMOND_BOOTS)
                    || !playerChest.getType().equals(Material.DIAMOND_CHESTPLATE)
                    || !playerLeg.getType().equals(Material.DIAMOND_LEGGINGS)
					) {
                return false;
            }
        } else if (armortype.equalsIgnoreCase("chainmail")) {
            // Chainmail
            if (!playerBoot.getType().equals(Material.CHAINMAIL_BOOTS ) 
					|| playerChest.getType().equals(Material.CHAINMAIL_CHESTPLATE)
                    || playerLeg.getType().equals(Material.CHAINMAIL_LEGGINGS)
					) {
                return false;
            }
        } else if (armortype.equalsIgnoreCase("gold")) {
            // Gold
            if (!playerBoot.getType().equals(Material.GOLD_BOOTS)
                    || !playerChest.getType().equals(Material.GOLD_CHESTPLATE)
                    || !playerLeg.getType().equals(Material.GOLD_LEGGINGS)
					) {
                return false;
            }
        } else if (armortype.equalsIgnoreCase("iron")) {
            // Iron
            if (!playerBoot.getType().equals(Material.IRON_BOOTS)
                    || !playerChest.getType().equals(Material.IRON_CHESTPLATE) 
                    || !playerLeg.getType().equals(Material.IRON_LEGGINGS)
					) {
                return false;
            }
        } else if (armortype.equalsIgnoreCase("leather")) {
            // Leather
            if (!playerBoot.getType().equals(Material.LEATHER_BOOTS)
                    || !playerChest.getType().equals(Material.LEATHER_CHESTPLATE)
                    || !playerLeg.getType().equals(Material.LEATHER_LEGGINGS)
					) {
                return false;
            }
        } else if (armortype.equalsIgnoreCase("id")) {
			// By id
			if (!typeAndAmountEqual(playerChest, toItemStack(ConfigHandler.getChestPlate()))
					|| !typeAndAmountEqual(playerLeg, toItemStack(ConfigHandler.getLeggings()))
					|| !typeAndAmountEqual(playerBoot, toItemStack(ConfigHandler.getBoots()))
					) {
				return false;
			}
		}
        return hasHelmet(p); //Made it this far, has bottom 3
    }
    
    
    /**
     * 
     * @param p Player
     * @return true if player's helmet id is <code>id</code>
     */
    public static boolean hasHelmet(Player p) {
		if (p.getInventory().getHelmet() == null) return false;
        return typeAndAmountEqual(p.getInventory().getHelmet(), toItemStack(ConfigHandler.getHelmet()));
    }

    public static ItemStack toItemStack(String materialOrId) {
		Material matchMaterial = Material.matchMaterial(materialOrId);
		if (matchMaterial != null) {
			return new ItemStack(matchMaterial);
		}
		try {
			int id = Integer.parseInt(materialOrId);
			return new ItemStack(id);
		} catch (NumberFormatException ex) {
			MessageHandler.debugPrint(Level.WARNING, "You have an error in your config.yml: " + materialOrId + " is not a material name or id.");
		}
		return null;
    }
	
	public static boolean typeAndAmountEqual(ItemStack one, ItemStack two) {
		return one.getAmount() == two.getAmount() && one.getTypeId() == two.getTypeId();
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
            if(hasSuit(player)){
                return false;
            }          
        }
        else if (suit == SuitCheck.HELMET_ONLY) {
            if(hasHelmet(player)){
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
            SpacePlayerHandler.giveSpaceSuit(player);
            return;
        }
        if (ConfigHandler.isHelmetGiven()) {
            giveHelmet(player);
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
