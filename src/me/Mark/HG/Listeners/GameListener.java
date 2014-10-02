package me.Mark.HG.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class GameListener implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		// TODO make spectating work
		event.disallow(Result.KICK_OTHER, ChatColor.RED
				+ "The game has already started.");
	}

	@SuppressWarnings("deprecation")
	public void nerfCrits(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if (!damager.isOnGround() && damager.getFallDistance() > 0) {
				double damage = event.getDamage() / 150;
				damage *= 100;
				event.setDamage(damage);
			}
		}
	}
}
