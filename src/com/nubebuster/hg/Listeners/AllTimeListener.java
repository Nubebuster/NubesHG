package com.nubebuster.hg.Listeners;

import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.nubebuster.hg.Gamer;
import com.nubebuster.hg.HG;
import com.nubebuster.hg.Data.MySQL;
import com.nubebuster.hg.Utils.Undroppable;

public class AllTimeListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (!Gamer.getGamer(event.getPlayer()).isAlive())
			event.setFormat("<§8%s§r> %s");
	}

	@EventHandler
	public void onPing(ServerListPingEvent event) {
		if (HG.HG.preTime > -1) {
			event.setMotd(HG.HG.motd + ChatColor.RED + "Game starting in " + HG.HG.preTime + " seconds.");
		} else {
			event.setMotd(HG.HG.motd + ChatColor.RED + "The game has already started.");
		}
	}

	@EventHandler
	public void onJoinOP(PlayerJoinEvent event) {
		if (event.getPlayer().isOp()) {
			event.getPlayer().setPlayerListName(ChatColor.RED + event.getPlayer().getName());
			event.getPlayer().setDisplayName(ChatColor.RED + event.getPlayer().getName() + ChatColor.RESET);
		}
	}

	/*
	 * @EventHandler public void onMove(PlayerMoveEvent event) { Gamer g =
	 * Gamer.getGamer(event.getPlayer()); if (g.isAlive()) { for (Gamer og :
	 * Gamer.getGamers()) { if (!g.isAlive())
	 * g.getPlayer().hidePlayer(og.getPlayer()); else
	 * g.getPlayer().showPlayer(og.getPlayer()); } } else for (Gamer og :
	 * Gamer.getGamers()) g.getPlayer().showPlayer(og.getPlayer()); }
	 */

	@EventHandler
	public void onInv(InventoryClickEvent event) {
		if (!Gamer.getGamer((Player) event.getWhoClicked()).isAlive())
			event.setCancelled(true);
		if (event.getInventory() == null)
			return;
		if (((event.getCurrentItem() != null && event.getCurrentItem().containsEnchantment(Undroppable.ench))
				|| (event.getClick() == ClickType.NUMBER_KEY
						&& event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) != null
						&& event.getWhoClicked().getInventory().getItem(event.getHotbarButton())
								.containsEnchantment(Undroppable.ench)))
				&& event.getInventory().getHolder() != event.getWhoClicked().getInventory().getHolder())
			event.setCancelled(true);
	}

	@EventHandler
	public void onGM(PlayerGameModeChangeEvent event) {
		Gamer g = Gamer.getGamer(event.getPlayer());
		g.setAlive(event.getNewGameMode() == GameMode.SURVIVAL);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().containsEnchantment(Undroppable.ench))
			event.setCancelled(true);
	}

	@EventHandler
	public void onPortal(PlayerPortalEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		if (HG.HG.gameTime < 120)
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent event) {
		Gamer g = Gamer.getGamer(event.getPlayer());
		if (!g.isAlive())
			g.remove();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		Gamer g = Gamer.getGamer(p);
		if (HG.HG.gameTime > -1 && !g.isAlive())
			event.getPlayer().setGameMode(GameMode.CREATIVE);
		Bukkit.getScheduler().scheduleAsyncDelayedTask(HG.HG, new Runnable() {
			public void run() {
				try {
					MySQL.updateName(p.getUniqueId(), p.getName());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	HashMap<String, Long> ffc = new HashMap<String, Long>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onForce(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (ffc.containsKey(p.getName()) && ffc.get(p.getName()) > System.currentTimeMillis())
			return;
		ffc.put(p.getName(), System.currentTimeMillis() + 500);
		Location loc = p.getLocation();
		if (!(loc.getBlockX() > 500) && !(loc.getBlockX() < -500) && !(loc.getBlockZ() > 500)
				&& !(loc.getBlockZ() < -500)) {
			if (!(loc.getBlockX() > 450) && !(loc.getBlockX() < -450) && !(loc.getBlockZ() > 450)
					&& !(loc.getBlockZ() < -450))
				return;
			p.sendMessage(ChatColor.GOLD + "Warning: forcefield is nearby!");
			return;
		}
		p.sendMessage(ChatColor.GOLD + "YOU ARE INSIDE THE FORCEFIELD!!!!");
		Location gloc = loc.clone();
		if (loc.getX() > 500)
			gloc.setX(900);
		if (loc.getX() < -500)
			gloc.setX(-900);
		if (loc.getZ() > 500)
			gloc.setZ(900);
		if (loc.getZ() < -500)
			gloc.setZ(-900);
		Entity ghast = p.getWorld().spawnCreature(gloc, EntityType.GHAST);
		p.damage(4, ghast);
		ghast.remove();
		p.playSound(p.getLocation(), Sound.FIZZ, 10, 1);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (p.getInventory().getItemInHand().getType() == Material.COMPASS) {
			Player nearest = getNearest(p);
			if (nearest == null) {
				p.setCompassTarget(p.getWorld().getSpawnLocation());
				p.sendMessage(ChatColor.RED + "No valid targets found, compass pointing toward spawn.");
				return;
			}
			p.setCompassTarget(nearest.getLocation());
			p.sendMessage(ChatColor.YELLOW + "Compass pointing at " + nearest.getName());
		}
	}

	public Player getNearest(Player p) {
		double distance = Double.POSITIVE_INFINITY;
		Player target = null;
		for (Player op : Bukkit.getOnlinePlayers()) {
			if (op == p)
				continue;
			if (!(op.getWorld() == p.getWorld()))
				continue;
			if (!Gamer.getGamer(op).isAlive())
				continue;
			double distanceto = p.getLocation().distance(op.getLocation());
			if (distanceto > distance)
				continue;
			if (distanceto < 25)
				continue;
			distance = distanceto;
			target = op;
		}
		return target;
	}
}
