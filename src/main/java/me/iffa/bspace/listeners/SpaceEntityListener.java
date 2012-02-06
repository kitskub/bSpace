// Package Declaration
package me.iffa.bspace.listeners;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.handlers.ConfigHandler;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.WorldHandler;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * EntityListener.
 * 
 * @author iffa
 */
public class SpaceEntityListener implements Listener {

    /**
     * Called when a creature attempts to spawn.
     * 
     * @param event Event data
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (WorldHandler.isSpaceWorld(event.getEntity().getWorld())) {
            String id = ConfigHandler.getID(event.getEntity().getWorld());
            if (!ConfigHandler.allowHostileMobs(id)) {
                if (event.getCreatureType() == CreatureType.CREEPER
                        || event.getCreatureType() == CreatureType.GHAST
                        || event.getCreatureType() == CreatureType.GIANT
                        || event.getCreatureType() == CreatureType.MONSTER
                        || event.getCreatureType() == CreatureType.SKELETON
                        || event.getCreatureType() == CreatureType.PIG_ZOMBIE
                        || event.getCreatureType() == CreatureType.SPIDER
                        || event.getCreatureType() == CreatureType.ZOMBIE
                        || event.getCreatureType() == CreatureType.SLIME
                        || event.getCreatureType() == CreatureType.CAVE_SPIDER
                        || event.getCreatureType() == CreatureType.SILVERFISH) {
                    event.setCancelled(true);

                }
            }
            if (!ConfigHandler.allowNeutralMobs(id)) {
                if (event.getCreatureType() == CreatureType.CHICKEN
                        || event.getCreatureType() == CreatureType.COW
                        || event.getCreatureType() == CreatureType.PIG
                        || event.getCreatureType() == CreatureType.SHEEP
                        || event.getCreatureType() == CreatureType.SQUID
                        || event.getCreatureType() == CreatureType.WOLF) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * Called when an entity (attempts) to take damage.
     * 
     * @param event Event data
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == DamageCause.DROWNING) {
            Player player = (Player) event.getEntity();
            if (ConfigHandler.getStopDrowning()) {
                for (World world : ConfigHandler.getStopDrowningWorlds()) {
                    if (world == player.getWorld() && player.getInventory().getHelmet().getTypeId() == ConfigHandler.getHelmetBlock()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * Called when an entity dies.
     * 
     * @param event Event data
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if(SpaceSuffocationListener.stopSuffocating(p)){
                MessageHandler.debugPrint(Level.INFO, "Cancelled suffocating task for player '" + p.getName() + "' because (s)he died.");
            }
        }
    }
}
