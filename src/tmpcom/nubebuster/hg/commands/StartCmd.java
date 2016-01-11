package tmpcom.nubebuster.hg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import tmpcom.nubebuster.hg.HG;

public class StartCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("hg.start")) {
			sender.sendMessage(ChatColor.RED + "No permission.");
			return false;
		}
		if (HG.HG.preTime == -1) {
			sender.sendMessage(ChatColor.RED + "The game has already begun.");
			return false;
		}
		if (args.length == 0) {
			HG.HG.preTime = 0;
			Bukkit.getServer().broadcastMessage(ChatColor.RED + sender.getName() + " has forced the game to start!");
			return false;
		}
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Usage: \"/start [delay]\"");
			return false;
		}
		int time;
		try {
			time = Integer.parseInt(args[0]);
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "Delay must be an integer.");
			return false;
		}
		HG.HG.preTime = time;
		Bukkit.getServer().broadcastMessage(
				ChatColor.RED + sender.getName() + " has set the startup time to " + time + " seconds.");
		return false;
	}
}
