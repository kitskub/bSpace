// Package Declaration
package me.iffa.bananaspace.listeners.misc;

// Java Imports
import java.util.logging.Level;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceConfigHandler;
import me.iffa.bananaspace.api.SpaceMessageHandler;

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
     * Constructor for SpaceWeatherListener.
     */
    public SpaceWeatherListener() {
    }

    /**
     * Called when the weather attempts to change.
     * 
     * @param event Event data
     */
    @Override
    public void onWeatherChange(WeatherChangeEvent event) {
        if (BananaSpace.worldHandler.isSpaceWorld(event.getWorld()) && !SpaceConfigHandler.allowWeather(event.getWorld()) && event.toWeatherState()) {
            event.setCancelled(true);
            SpaceMessageHandler.debugPrint(Level.INFO, "Cancelled WeatherChangeEvent for spaceworld '" + event.getWorld().getName() + "'.");
        }
    }
}
