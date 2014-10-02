package me.Mark.HG;

import java.util.ArrayList;
import java.util.List;

import me.Mark.HG.Kits.Kit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Gamer {

	private String name;
	private Kit kit = Kit.getKitFromName("None");
	private boolean alive = true;

	public Gamer(Player player) {
		this.name = player.getName();
		gamers.add(this);
	}

	public Kit getKit() {
		return kit;
	}
	
	@SuppressWarnings("deprecation")
	public Player getPlayer() {
		return Bukkit.getPlayer(name);
	}

	public void setKit(Kit kit) {
		this.kit = kit;
	}

	public String getName() {
		return name;
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
			if (g.getName().equals(p.getName()))
				return g;
		return null;
	}

	public static Gamer getGamer(String name) {
		for (Gamer g : gamers)
			if (g.getName().equals(name))
				return g;
		return null;
	}
	
	public static List<Gamer> getGamers() {
		 return gamers;
	}

}
