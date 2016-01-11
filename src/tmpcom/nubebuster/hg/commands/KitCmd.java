package tmpcom.nubebuster.hg.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import tmpcom.nubebuster.hg.Gamer;
import tmpcom.nubebuster.hg.HG;
import tmpcom.nubebuster.hg.kits.Kit;

public class KitCmd implements CommandExecutor, TabCompleter {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (HG.HG.gameTime > -1)
			return false;
		if (args.length == 0) {
			String kitss = null;
			String nop = null;
			for (Kit k : Kit.kits) {
				if (!(sender.hasPermission("hg.kits.*")
						|| sender.hasPermission("hg.kits." + k.getKitName().toLowerCase()))) {
					if (nop == null)
						nop = k.getKitName();
					else
						nop = nop.concat(", " + k.getKitName());
				} else {
					if (kitss == null)
						kitss = k.getKitName();
					else
						kitss = kitss.concat(", " + k.getKitName());
				}
			}
			sender.sendMessage(ChatColor.GREEN + "Your kits: §r" + kitss + "\n§aOther Kits: §r"
					+ (nop == null ? "You have all kits!" : nop));
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
}
