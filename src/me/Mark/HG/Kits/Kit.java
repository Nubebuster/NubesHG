package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Mark.HG.Gamer;
import me.Mark.HG.HG;
import me.Mark.HG.Utils.NameAlreadyInUseException;
import me.Mark.HG.Utils.Undroppable;

public abstract class Kit implements Listener {

	protected static final Random random = new Random();

	public static List<Kit> kits = new ArrayList<Kit>();
	private static List<String> disabled = new ArrayList<String>();

	public static void init() {
		for (String s : HG.HG.config.getStringList("disabled"))
			disabled.add(s.toLowerCase());
		try {
			registerKit(new None());
			registerKit(new Archer());
			registerKit(new Demoman());
			registerKit(new Endermage());
			registerKit(new FisherMan());
			registerKit(new Miner());
			registerKit(new Monk());
			registerKit(new Ninja());
			registerKit(new Reaper());
			registerKit(new Snail());
			registerKit(new Stomper());
			registerKit(new Viper());
		} catch (NameAlreadyInUseException e) {
			e.printStackTrace();
		}
	}

	public static void registerKit(Kit kit) throws NameAlreadyInUseException {
		for (Kit k : kits)
			if (k.getKitName().equalsIgnoreCase(kit.getKitName()))
				throw new NameAlreadyInUseException(
						"Tried to register kit with the name " + kit.getKitName() + ", but that is already in use.");
		if (disabled.contains(kit.getKitName().toLowerCase()))
			return;
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

	public static void registerKitEvents() {
		for (Kit k : kits)
			Bukkit.getPluginManager().registerEvents(k, HG.HG);
	}

	public static void unregisterKitEvents(HG hg) {
		for (Kit k : kits)
			HandlerList.unregisterAll(k);
	}

	protected static ItemStack createItem(Material mat, String name, boolean droppable) {
		ItemStack is = new ItemStack(mat);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		if (!droppable)
			is.addUnsafeEnchantment(Undroppable.ench, 1);
		return is;
	}
}
