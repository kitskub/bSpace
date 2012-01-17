// Package Declaration
package me.iffa.bspace.listeners.misc;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.handlers.ConfigHandler;
import me.iffa.bspace.handlers.MessageHandler;
import me.iffa.bspace.handlers.WorldHandler;

// Bukkit Imports
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

/**
 * WeatherListener for weather changes.
 * 
 * @author iffa
 */
public class SpaceWeatherListener extends WeatherListener {
    /**
     * Called when the weather attempts to change.
     * 
     * @param event Event data
     */
    @Override
    public void onWeatherChange(WeatherChangeEvent event) {
        if (WorldHandler.isSpaceWorld(event.getWorld()) 
                && !ConfigHandler.allowWeather(ConfigHandler.getID(event.getWorld())) 
                && event.toWeatherState()) {
            event.setCancelled(true);
            MessageHandler.debugPrint(Level.INFO, "Cancelled WeatherChangeEvent for spaceworld '" + event.getWorld().getName() + "'.");
        }
    }
}
