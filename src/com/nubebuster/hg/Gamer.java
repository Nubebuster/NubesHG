package com.nubebuster.hg;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.nubebuster.hg.Kits.Kit;

public class Gamer {

	private String name;
	private UUID uuid;
	private Kit kit = Kit.getKitFromName("None");
	private boolean alive = false;

	private Gamer(Player player) {
		this.name = player.getName();
		this.uuid = player.getUniqueId();
		gamers.add(this);
	}

	public Kit getKit() {
		return kit;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	public void setKit(Kit kit) {
		this.kit = kit;
	}

	public String getName() {
		return name;
	}

	public UUID getUUID() {
		return uuid;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void remove() {
		gamers.remove(this);
	}

	private static final List<Gamer> gamers = new ArrayList<Gamer>();

	public static Gamer getGamer(Player p) {
		for (Gamer g : gamers)
			if (g.getName().equalsIgnoreCase(p.getName()))
				return g;
		return new Gamer(p);
	}

	/**
	 * @deprecated use getGamer(UUID) instead
	 */
	@Deprecated
	public static Gamer getGamer(String name) {
		for (Gamer g : gamers)
			if (g.getName().equalsIgnoreCase(name))
				return g;
		return null;
	}

	public static Gamer getGamer(UUID id) {
		for (Gamer g : gamers)
			if (g.getPlayer().getUniqueId().equals(id))
				return g;
		return null;
	}

	public static List<Gamer> getGamers() {
		return gamers;
	}

	public static List<Gamer> getAliveGamers() {
		List<Gamer> alive = new ArrayList<Gamer>();
		boolean started = HG.HG.gameTime > -1;
		for (Gamer g : gamers)
			if (started ? g.isAlive() : g.getPlayer().getGameMode() == GameMode.SURVIVAL)
				alive.add(g);
		return alive;
	}

	public void applyKit() {
		if (kit.getItems() != null)
			for (ItemStack is : kit.getItems())
				getPlayer().getInventory().addItem(is);
	}
}
