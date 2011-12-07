// Package Declaration
package me.iffa.bspace.listeners;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceConfigHandler;
import me.iffa.bspace.api.SpaceMessageHandler;

// Bukkit Imports
import me.iffa.bspace.api.SpacePlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;
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
 */
public class SpaceEntityListener extends EntityListener {
    /**
     * Called when a creature attempts to spawn.
     * 
     * @param event Event data
     */
    @Override
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (Space.getWorldHandler().isSpaceWorld(event.getEntity().getWorld())) {
            if (!SpaceConfigHandler.allowHostileMobs(event.getEntity().getWorld())) {
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
        if (event.getEntity() instanceof Player && Space.getWorldHandler().isInAnySpace((Player) event.getEntity()) && event.getCause() == DamageCause.VOID) {
            Player player = (Player) event.getEntity();
            player.setHealth(0);
            SpaceMessageHandler.debugPrint(Level.INFO, "Killed player '" + player.getName() + "' in void.");
        }
        if (event.getEntity() instanceof Player && event.getCause() == DamageCause.DROWNING) {
            Player player = (Player) event.getEntity();
            if(SpaceConfigHandler.getStopDrowning()){
                for(World world:SpaceConfigHandler.getStopDrowningWorlds()){
                    if(world==player.getWorld()&&SpacePlayerHandler.hasSuit(player,SpaceConfigHandler.getArmorType())){
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
    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        // TODO: Test this code as someone reported this does not work.
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (SpaceSuffocationListener.taskid.containsKey(p) && Bukkit.getScheduler().isCurrentlyRunning(SpaceSuffocationListener.taskid.get(p))) {
                Bukkit.getScheduler().cancelTask(SpaceSuffocationListener.taskid.get(p));
                SpaceMessageHandler.debugPrint(Level.INFO, "Cancelled suffocating task for player '" + p.getName() + "' because (s)he died.");
            }
        }
    }
}
