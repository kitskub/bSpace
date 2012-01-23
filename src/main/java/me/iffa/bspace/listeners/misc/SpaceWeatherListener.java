// Package Declaration
package me.iffa.bspace.listeners.misc;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.handlers.ConfigHandler;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.WorldHandler;

// Bukkit Imports
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * WeatherListener for weather changes.
 * 
 * @author iffa
 * @deprecated Multiverse (and most world managers, really) does this
 */
@Deprecated
public class SpaceWeatherListener implements Listener {
    /**
     * Called when the weather attempts to change.
     * 
     * @param event Event data
     */
    @EventHandler(event = WeatherChangeEvent.class, priority = EventPriority.NORMAL)
    public void onWeatherChange(WeatherChangeEvent event) {
        if (WorldHandler.isSpaceWorld(event.getWorld()) 
                && !ConfigHandler.allowWeather(ConfigHandler.getID(event.getWorld())) 
                && event.toWeatherState()) {
            event.setCancelled(true);
            MessageHandler.debugPrint(Level.INFO, "Cancelled WeatherChangeEvent for spaceworld '" + event.getWorld().getName() + "'.");
        }
    }
}
