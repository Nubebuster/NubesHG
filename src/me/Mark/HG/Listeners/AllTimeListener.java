package me.Mark.HG.Listeners;

import me.Mark.HG.Gamer;
import me.Mark.HG.HG;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AllTimeListener implements Listener {

	@EventHandler
	public void onPortal(PlayerPortalEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		if (HG.GameTime < 120)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Gamer.getGamer(event.getPlayer()).remove();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		new Gamer(event.getPlayer());
	}
}
