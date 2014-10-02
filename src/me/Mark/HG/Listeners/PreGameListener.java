package me.Mark.HG.Listeners;

import me.Mark.HG.Gamer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PreGameListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (Gamer.getGamer(event.getPlayer()).isAlive())
			event.setCancelled(true);
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (Gamer.getGamer(event.getPlayer()).isAlive())
			event.setCancelled(true);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
			event.setCancelled(true);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().teleport(
				new Location(Bukkit.getWorld("world"), .5, 80, .5));
		event.getPlayer().performCommand("/kit");
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (Gamer.getGamer(event.getPlayer()).isAlive())
			event.setCancelled(true);
	}
}
