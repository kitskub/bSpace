// Package Declaration
package me.iffa.bananaspace.listeners;

// Java Imports
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceConfigHandler;
import me.iffa.bananaspace.api.SpaceMessageHandler;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

/**
 * EntityListener.
 * 
 * @author iffa
 * 
 */
public class SpaceEntityListener extends EntityListener {
    // Variables
    private BananaSpace plugin;

    /**
     * Constructor for SpaceEntityListener.
     * 
     * @param plugin BananaSpace 
     */
    public SpaceEntityListener(BananaSpace plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when a creature attempts to spawn.
     * 
     * @param event Event data
     */
    @Override
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (BananaSpace.worldHandler.isSpaceWorld(event.getEntity().getWorld())) {
            if (!SpaceConfigHandler.allowHostileMobs(event.getEntity().getWorld())) {
                if (event.getCreatureType() == CreatureType.CREEPER
                        || event.getCreatureType() == CreatureType.GHAST
                        || event.getCreatureType() == CreatureType.GIANT
                        || event.getCreatureType() == CreatureType.MONSTER
                        || event.getCreatureType() == CreatureType.SKELETON
                        || event.getCreatureType() == CreatureType.PIG_ZOMBIE
                        || event.getCreatureType() == CreatureType.SPIDER
                        || event.getCreatureType() == CreatureType.ZOMBIE
                        || event.getCreatureType() == CreatureType.SLIME) {
                    event.setCancelled(true);
                    
                }
            }
            if (!SpaceConfigHandler.allowNeutralMobs(event.getEntity().getWorld())) {
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
     * Called when an entity "attempts" to take damage.
     * 
     * @param event Event data
     */
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getEntity() instanceof Player && BananaSpace.worldHandler.isInAnySpace((Player) event.getEntity()) && event.getCause() == DamageCause.VOID) {
            Player player = (Player) event.getEntity();
            player.setHealth(0);
            SpaceMessageHandler.debugPrint(Level.INFO, "Killed player '" + player.getName() + "' in void.");
        }
    }

    /**
     * Called when an entity dies.
     * 
     * @param event Event data
     */
    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        // TODO: Test this code as someone reported this does not work.
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (SpacePlayerListener.taskid.containsKey(p) && BananaSpace.scheduler.isCurrentlyRunning(SpacePlayerListener.taskid.get(p))) {
                    BananaSpace.scheduler.cancelTask(SpacePlayerListener.taskid.get(p));
                    SpaceMessageHandler.debugPrint(Level.INFO, "Cancelled suffocating task for player '" + p.getName() + "' because (s)he died.");
            }
        }
    }
}
