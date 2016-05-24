package com.nubebuster.hg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.nubebuster.hg.kits.Kit;

public class Gamer {

	private String name;
	private UUID uuid;
	private Kit kit = Kit.getKitFromName("None");
	private boolean alive = false;

	private Gamer(Player player) {
		this.name = player.getName();
		this.uuid = player.getUniqueId();
		gamers.put(uuid, this);
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
		gamers.remove(uuid);
	}

	// private static final List<Gamer> gamers = new ArrayList<Gamer>();

	private static final HashMap<UUID, Gamer> gamers = new HashMap<UUID, Gamer>();

	public static Gamer getGamer(Player p) {
		Gamer g = gamers.get(p.getUniqueId());
		return g != null ? g : new Gamer(p);
	}

	/**
	 * @deprecated use getGamer(UUID) instead
	 */
	@Deprecated
	public static Gamer getGamer(String name) {
		for (Gamer g : gamers.values())
			if (g.getName().equalsIgnoreCase(name))
				return g;
		return null;
	}

	public static Gamer getGamer(UUID id) {
		return gamers.get(id);
	}

	public static Collection<Gamer> getGamers() {
		return gamers.values();
	}

	public static List<Gamer> getAliveGamers() {
		List<Gamer> alive = new ArrayList<Gamer>();
		boolean started = HG.HG.gameTime > -1;
		for (Gamer g : gamers.values())
			if (g.getPlayer() == null) {
				g.remove();
			} else if (started ? g.isAlive() : g.getPlayer().getGameMode() == GameMode.SURVIVAL)
				alive.add(g);
		return alive;
	}

	public void applyKit() {
		if (kit.getItems() != null)
			for (ItemStack is : kit.getItems())
				getPlayer().getInventory().addItem(is);
	}
}
