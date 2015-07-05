package me.Mark.HG.Listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import me.Mark.HG.Gamer;
import me.Mark.HG.HG;
import me.Mark.HG.Utils.Undroppable;

public class GameListener implements Listener {

	/*
	 * @EventHandler public void onLogin(PlayerLoginEvent event) {
	 * event.disallow(Result.KICK_OTHER, ChatColor.RED +
	 * "The game has already started."); }
	 */

	@EventHandler
	public void onDamageInv(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && HG.HG.gameTime < 120)
			event.setCancelled(true);
	}

	private Random r = new Random();

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player dead = event.getEntity();
		List<ItemStack> drops = new ArrayList<ItemStack>();
		for (ItemStack is : event.getDrops())
			if (!is.containsEnchantment(Undroppable.ench))
				drops.add(is);
		event.getDrops().clear();
		for (ItemStack is : drops)
			event.getDrops().add(is);
		DamageCause cause = event.getEntity().getLastDamageCause().getCause();
		if (event.getEntity().getKiller() != null) {
			Player killer = event.getEntity().getKiller();
			String weapon = WordUtils
					.capitalizeFully(killer.getItemInHand().getType().toString().replace("_", " ").toLowerCase());
			String killname = killer.getName() + "(" + Gamer.getGamer(killer).getKit().getKitName() + ")";
			if (r.nextBoolean())
				event.setDeathMessage("%p entered their next life, courtesy of " + killname + "'s " + weapon);
			else
				event.setDeathMessage("%p was killed by " + killname + " with a " + weapon);
		} else if (cause == DamageCause.FALL) {
			event.setDeathMessage(r.nextBoolean() ? "%p fell to their death" : "%p fell really far");
		} else if (cause == DamageCause.BLOCK_EXPLOSION || cause == DamageCause.ENTITY_EXPLOSION) {
			String[] causes = new String[] { "%p Don't. Play. With. TNT", "%p was blown to smithereens",
					"%p exploded into a million pieces" };
			event.setDeathMessage(causes[r.nextInt(causes.length)]);
		} else if (event.getDeathMessage().contains("by Ghast")) {
			event.setDeathMessage("%p ran into the forcefield!");
		} else if (event.getDeathMessage().contains("by Zombie")) {
			event.setDeathMessage("%p was eaten alive! RUUUN!!!");
		} else {
			event.setDeathMessage(event.getDeathMessage().replace(dead.getName(), "%p"));
		}
		dead.setGameMode(GameMode.CREATIVE);
		event.setDeathMessage(ChatColor.AQUA
				+ event.getDeathMessage().replace("%p",
						dead.getName() + "(" + Gamer.getGamer(dead).getKit().getKitName() + ")")
				+ ".\n" + HG.check() + " players remaining.");
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void nerfCrits(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if (!damager.isOnGround() && damager.getFallDistance() > 0) {
				double damage = event.getDamage() / 150;
				damage *= 100;
				event.setDamage(damage - 1);
			}
		}
	}

	@EventHandler
	public void onSoup(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (p.getItemInHand() == null || p.getItemInHand().getType() != Material.MUSHROOM_SOUP
				|| (p.getHealth() == 20 && p.getFoodLevel() >= 20))
			return;
		if (p.getHealth() < 20)
			p.setHealth(p.getHealth() <= 13 ? p.getHealth() + 7 : 20);
		else if (p.getFoodLevel() < 20)
			p.setFoodLevel(p.getFoodLevel() <= 13 ? p.getFoodLevel() + 7 : 20);
		p.getItemInHand().setType(Material.BOWL);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(event.getPlayer().getLocation());
		event.getPlayer().sendMessage(ChatColor.GRAY + "You are now a spectator, /go <player> to teleport to a player");
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

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		final Gamer g = Gamer.getGamer(event.getPlayer());
		if (!g.isAlive())
			return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(HG.HG, new Runnable() {
			public void run() {
				if (g.getPlayer() == null) {
					g.remove();
					Bukkit.getServer().broadcastMessage(ChatColor.AQUA + g.getName() + "(" + g.getKit().getKitName()
							+ ") was disconnected for too long and has forfeit!");
					HG.check();
				}
			}
		}, 1200);
	}
}
