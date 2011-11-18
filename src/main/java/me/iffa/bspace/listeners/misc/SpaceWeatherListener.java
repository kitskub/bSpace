// Package Declaration
package me.iffa.bspace.listeners.misc;

// Java Imports
import java.util.logging.Level;

// bSpace Imports
import me.iffa.bspace.Space;
import me.iffa.bspace.api.SpaceConfigHandler;
import me.iffa.bspace.api.SpaceMessageHandler;

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
        if (Space.worldHandler.isSpaceWorld(event.getWorld()) && !SpaceConfigHandler.allowWeather(event.getWorld()) && event.toWeatherState()) {
            event.setCancelled(true);
            SpaceMessageHandler.debugPrint(Level.INFO, "Cancelled WeatherChangeEvent for spaceworld '" + event.getWorld().getName() + "'.");
        }
    }
}
