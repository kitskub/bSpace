// Package Declaration
package me.iffa.bananaspace.listeners.misc;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;
import me.iffa.bananaspace.api.SpaceConfigHandler;

// Bukkit Imports
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

/**
 * WeatherListener for weather changes.
 * 
 * @author iffa
 * 
 */
public class SpaceWeatherListener extends WeatherListener {
    // Variables
    private BananaSpace plugin;
    
    // Constructor
    public SpaceWeatherListener(BananaSpace plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when the weather changes.
     * 
     * @param event Event data
     */
    @Override
    public void onWeatherChange(WeatherChangeEvent event) {
        if (BananaSpace.worldHandler.isSpaceWorld(event.getWorld())) {
            if (!SpaceConfigHandler.allowWeather(event.getWorld())) {
                if (event.toWeatherState() == true) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
