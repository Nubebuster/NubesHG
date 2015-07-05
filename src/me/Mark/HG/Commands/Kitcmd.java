package me.Mark.HG.Commands;

import me.Mark.HG.Gamer;
import me.Mark.HG.HG;
import me.Mark.HG.Kits.Kit;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class KitCmd implements CommandExecutor, TabCompleter {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (HG.HG.gameTime > -1)
			return false;
		if (args.length == 0) {
			String kitss = null;
			for (Kit k : Kit.kits) {
				if (kitss == null)
					kitss = k.getKitName();
				else
					kitss = kitss.concat(", " + k.getKitName());
			}
			sender.sendMessage(ChatColor.GREEN + "Your kits: §r" + kitss);
			return false;
		}
		if (!(sender instanceof Player))
			return false;
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Type \"/kit <kit>\" or \"/kit\" to view all kits.");
			return false;
		}
		String kit = args[0].toLowerCase();
		Kit k = Kit.getKitFromName(kit);
		if (k == null) {
			sender.sendMessage(ChatColor.RED + "That kit does not exist or is disabled!");
			return false;
		}
		Gamer.getGamer(sender.getName()).setKit(k);
		sender.sendMessage(ChatColor.GREEN + "You are now a " + WordUtils.capitalize(kit) + "!");
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			if (args[0].length() == 0)
				return getKits();
			List<String> pos = new ArrayList<String>();
			for (String s : getKits())
				if (s.toLowerCase().startsWith(args[0].toLowerCase()))
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
}
