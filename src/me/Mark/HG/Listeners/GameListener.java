package me.Mark.HG.Listeners;

import java.util.Random;

import me.Mark.HG.Gamer;
import me.Mark.HG.HG;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class GameListener implements Listener {

/*	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		event.disallow(Result.KICK_OTHER, ChatColor.RED
				+ "The game has already started.");
	}*/

	private Random r = new Random();

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player dead = event.getEntity();
		if (event.getEntity().getKiller() != null) {
			Player killer = event.getEntity().getKiller();
			String weapon = WordUtils.capitalizeFully(killer.getItemInHand()
					.getType().toString().replace("_", " ").toLowerCase());
			String killname = killer.getName() + "("
					+ Gamer.getGamer(killer).getKit().getKitName() + ")";
			int random = r.nextInt(2);
			if (random == 0)
				event.setDeathMessage("%p entered their next life, courtesy of "
						+ killname + "'s " + weapon);
			else if (random == 1)
				event.setDeathMessage("%p was killed by " + killname
						+ " with a " + weapon);
		} else if (event.getEntity().getLastDamageCause().getCause() == DamageCause.FALL) {
			event.setDeathMessage("%p fell to their death");
		} else {
			event.setDeathMessage(event.getDeathMessage().replace(
					dead.getName(), "%p"));
		}

		event.setDeathMessage(ChatColor.AQUA
				+ event.getDeathMessage().replace(
						"%p",
						dead.getName() + "("
								+ Gamer.getGamer(dead).getKit().getKitName()
								+ ")") + ".\n" + HG.check()
				+ " players remaining.");
		
		dead.setGameMode(GameMode.CREATIVE);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
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

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(event.getPlayer().getLocation());
		event.getPlayer()
				.sendMessage(
						ChatColor.GRAY
								+ "You are now a spectator, /go <player> to teleport to a player");
	}

	@EventHandler
	public void onTarget(EntityTargetLivingEntityEvent event) {
		if (event.getTarget() instanceof Player) {
			Gamer g = Gamer.getGamer((Player) event.getTarget());
			if (!g.isAlive())
				event.setCancelled(true);
		}
	}

	// Spectating

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Gamer g = Gamer.getGamer(event.getPlayer());
		if (!g.isAlive())
			event.setCancelled(true);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Gamer g = Gamer.getGamer(event.getPlayer());
		if (!g.isAlive())
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Gamer g = Gamer.getGamer(event.getPlayer());
		if (!g.isAlive())
			event.setCancelled(true);
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		Gamer g = Gamer.getGamer(event.getPlayer());
		if (!g.isAlive())
			event.setCancelled(true);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Gamer g = Gamer.getGamer(event.getPlayer());
		if (!g.isAlive())
			event.setCancelled(true);
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (!Gamer.getGamer((Player) event.getDamager()).isAlive())
				event.setCancelled(true);
		}
	}
}
