// Package Declaration
package me.iffa.bspace.listeners;

// Java Imports
import java.util.logging.Level;
import me.iffa.bspace.api.SpacePlayerHandler;

// bSpace Imports
import me.iffa.bspace.handlers.ConfigHandler;
import me.iffa.bspace.handlers.MessageHandler;

// Bukkit Imports
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
                    if (world == player.getWorld() && player.getInventory().getHelmet() != null && player.getInventory().getHelmet() == SpacePlayerHandler.toItemStack(ConfigHandler.getHelmet())) {
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
