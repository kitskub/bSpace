// Package Declaration
package me.iffa.bananaspace.listeners;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceConfigHandler;
import me.iffa.bananaspace.api.event.misc.AntiMobSpawnEvent;

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

    // Constructor
    public SpaceEntityListener(BananaSpace instance) {
        plugin = instance;
    }

    /**
     * Called when a creature spawns.
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
                    // Notify listeners.
                    AntiMobSpawnEvent e = new AntiMobSpawnEvent(
                            "AntiMobSpawnEvent", event.getEntity());
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (!e.isCancelled()) {
                        event.setCancelled(true);
                    }
                }
            }
            if (!SpaceConfigHandler.allowNeutralMobs(event.getEntity().getWorld())) {
                if (event.getCreatureType() == CreatureType.CHICKEN
                        || event.getCreatureType() == CreatureType.COW
                        || event.getCreatureType() == CreatureType.PIG
                        || event.getCreatureType() == CreatureType.SHEEP
                        || event.getCreatureType() == CreatureType.SQUID
                        || event.getCreatureType() == CreatureType.WOLF) {
                    // Notify listeners.
                    AntiMobSpawnEvent e = new AntiMobSpawnEvent(
                            "AntiMobSpawnEvent", event.getEntity());
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (!e.isCancelled()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * Called when an entity takes damage.
     * 
     * @param event Event data
     */
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getEntity() instanceof Player
                && BananaSpace.worldHandler.isInAnySpace((Player) event.getEntity())) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == DamageCause.VOID) {
                Player killhim = (Player) event.getEntity();
                killhim.setHealth(0);
            }
        }
    }

    /**
     * Called when an entity dies.
     * 
     * @param event Event data
     */
    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (SpacePlayerListener.taskid.containsKey(p)) {
                if (BananaSpace.scheduler.isCurrentlyRunning(SpacePlayerListener.taskid.get(p))) {
                    BananaSpace.scheduler.cancelTask(SpacePlayerListener.taskid.get(p));
                }
            }
        }
    }
}
