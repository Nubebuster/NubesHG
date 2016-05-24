package com.nubebuster.hg.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.nubebuster.hg.Gamer;
import com.nubebuster.hg.HG;
import com.nubebuster.hg.kits.Kit;

public class KitCmd implements CommandExecutor, TabCompleter, Listener {

	public KitCmd() {
		Bukkit.getPluginManager().registerEvents(this, HG.HG);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		Gamer g = Gamer.getGamer((Player) sender);
		if (HG.HG.gameTime > -1) {
			sender.sendMessage(ChatColor.RED
					+ "You cannot choose a kit because your game has already started.\nYou are currently the "
					+ g.getKit().getKitName() + " kit.");
			return false;
		}
		if (args.length == 0) {
			g.getPlayer().openInventory(createInventory(g.getPlayer()));
			return false;
		}
		if (!(sender instanceof Player))
			return false;
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Type \"/kit <kit>\" or \"/kit\" to view all kits.");
			return false;
		}
		String kit = args[0].toLowerCase();
		if (!(sender.hasPermission("hg.kits.*") || sender.hasPermission("hg.kits." + kit.toLowerCase()))) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to this kit.");
			return false;
		}
		Kit k = Kit.getKitFromName(kit);
		if (k == null) {
			sender.sendMessage(ChatColor.RED + "The " + args[0] + " kit does not exist or is disabled!");
			return false;
		}
		Gamer.getGamer(sender.getName()).setKit(k);
		sender.sendMessage(ChatColor.GREEN + "You are now a " + WordUtils.capitalize(kit) + "!");
		return false;
	}

	public Inventory createInventory(Player p) {
		Inventory i = Bukkit.createInventory(null, 45, "§aKits");
		for (Kit k : Kit.kits)
			i.addItem(k.getIconItem());
		return i;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			if (args[0].length() == 0) {
				List<String> owned = new ArrayList<String>();
				for (String s : getKits())
					if (sender.hasPermission("hg.kits.*") || sender.hasPermission("hg.kits." + s.toLowerCase()))
						owned.add(s);
				return owned;
			}
			List<String> pos = new ArrayList<String>();
			for (String s : getKits())
				if (s.toLowerCase().startsWith(args[0].toLowerCase()))
					if (sender.hasPermission("hg.kits.*") || sender.hasPermission("hg.kits." + s.toLowerCase()))
						pos.add(s);
			return pos;
		}
		return null;
	}

	private static List<String> getKits() {
		List<String> kits = new ArrayList<String>();
		for (Kit k : Kit.kits)
			kits.add(k.getKitName());
		kits.remove("None");
		return kits;
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory() == null || !event.getInventory().getTitle().contains("Kits")
				|| event.getCurrentItem() == null)
			return;
		event.setCancelled(true);
		// Gamer g = Gamer.getGamer((Player) event.getWhoClicked());
		if (HG.HG.gameTime > 0)
			return;
		ItemStack item = event.getCurrentItem();
		if (item.getItemMeta() != null) {
			ItemMeta im = item.getItemMeta();
			Bukkit.dispatchCommand(event.getWhoClicked(), "kit " + im.getDisplayName());
			event.getWhoClicked().closeInventory();
		}
	}
}
