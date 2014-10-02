package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.List;

import me.Mark.HG.Gamer;
import me.Mark.HG.HG;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Kit implements Listener {

	public static List<Kit> kits = new ArrayList<Kit>();

	public static void init() {
		registerKit(new None());
		registerKit(new Archer());
	}

	public static void registerKit(Kit kit) {
		kits.add(kit);
	}

	public static Kit getKitFromName(String kit) {
		for (Kit k : kits)
			if (k.getKitName().equalsIgnoreCase(kit))
				return k;
		return null;
	}

	public abstract String getKitName();

	public abstract ItemStack[] getItems();

	protected boolean hasAbillity(Player p) {
		return Gamer.getGamer(p).getKit() == this;
	}

	public static void registerKitEvents(HG hg) {
		for (Kit k : kits)
			Bukkit.getPluginManager().registerEvents(k, hg);
	}

	public static void unregisterKitEvents(HG hg) {
		for (Kit k : kits)
			HandlerList.unregisterAll(k);
	}
}
