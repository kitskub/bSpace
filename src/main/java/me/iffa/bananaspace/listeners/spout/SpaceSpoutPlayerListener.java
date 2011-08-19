// Package Declaration
package me.iffa.bananaspace.listeners.spout;

// BananaSpace Imports
import me.iffa.bananaspace.BananaSpace;

// Bukkit Imports
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerTeleportEvent;

// Spout Imports
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SkyManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * PlayerListener for things which require Spout.
 * 
 * @author iffa
 */
public class SpaceSpoutPlayerListener extends PlayerListener {
    // Variables
    private BananaSpace plugin;

    // Constructor
    public SpaceSpoutPlayerListener(BananaSpace plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when a player teleports.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        SkyManager sky = SpoutManager.getSkyManager();
        if (BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld()) && !BananaSpace.worldHandler.isSpaceWorld(event.getFrom().getWorld())) {
            sky.setCloudsVisible(player, false);
            sky.setMoonSizePercent(player, 20);
        }
        if (BananaSpace.worldHandler.isSpaceWorld(event.getFrom().getWorld()) && !BananaSpace.worldHandler.isSpaceWorld(event.getTo().getWorld())) {
            sky.setCloudsVisible(player, true);
        }
    }
    
    /**
     * Called when a player joins the game.
     * 
     * @param event 
     */
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
        SkyManager sky = SpoutManager.getSkyManager();
        if (BananaSpace.worldHandler.isSpaceWorld(player.getWorld())) {
            sky.setCloudsVisible(player, false);
            sky.setMoonSizePercent(player, 20);
        }
    }
}
