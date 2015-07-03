package me.Mark.HG.Listeners;

import java.util.HashMap;

import me.Mark.HG.Gamer;
import me.Mark.HG.HG;
import me.Mark.HG.Utils.Undroppable;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AllTimeListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (!Gamer.getGamer(event.getPlayer()).isAlive())
			event.setFormat("<§8%s§r> %s");
	}

	@EventHandler
	public void onJoinOP(PlayerJoinEvent event) {
		if (event.getPlayer().isOp()) {
			event.getPlayer().setPlayerListName(ChatColor.RED + event.getPlayer().getName());
			event.getPlayer().setDisplayName(ChatColor.RED + event.getPlayer().getName() + ChatColor.RESET);
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Gamer g = Gamer.getGamer(event.getPlayer());
		if (g.isAlive()) {
			for (Gamer og : Gamer.getGamers()) {
				if (!g.isAlive())
					g.getPlayer().hidePlayer(og.getPlayer());
				else
					g.getPlayer().showPlayer(og.getPlayer());
			}
		} else
			for (Gamer og : Gamer.getGamers()) {
				g.getPlayer().showPlayer(og.getPlayer());
			}
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
		if (HG.HG.GameTime < 120)
			event.setCancelled(true);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Gamer.getGamer(event.getPlayer()).remove();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		new Gamer(event.getPlayer());
		if (HG.HG.GameTime > -1) {
			event.getPlayer().setGameMode(GameMode.CREATIVE);
		}
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
			if (getNearest(p) == null) {
				p.setCompassTarget(p.getWorld().getSpawnLocation());
				p.sendMessage(ChatColor.RED + "No valid targets found, compass pointing toward spawn.");
				return;
			}
			p.setCompassTarget(getNearest(p).getLocation());
			p.sendMessage(ChatColor.YELLOW + "Compass pointing at " + getNearest(p).getName());
		}
	}

	public Player getNearest(Player p) {
		double distance = Double.POSITIVE_INFINITY;
		Player target = null;
		for (Entity e : p.getNearbyEntities(1000, 1000, 1000)) {
			if (!(e instanceof Player))
				continue;
			Player p1 = (Player) e;
			if (!Gamer.getGamer(p1).isAlive())
				continue;
			if (e == p)
				continue;
			double distanceto = p.getLocation().distance(e.getLocation());
			if (distanceto > distance)
				continue;
			if (distanceto < 25)
				continue;
			distance = distanceto;
			target = (Player) e;
		}
		return target;
	}
}
