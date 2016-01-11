package tmpcom.nubebuster.hg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import tmpcom.nubebuster.hg.HG;

public class FTimeCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("hg.ftime")) {
			sender.sendMessage(ChatColor.RED + "No permission.");
			return false;
		}
		if (HG.HG.gameTime == -1) {
			sender.sendMessage(ChatColor.RED + "The game has not begun yet.");
			return false;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: \"/ftime <time>\"");
			return false;
		}
		int time;
		try {
			time = Integer.parseInt(args[0]);
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "The time must be an integer.");
			return false;
		}
		HG.HG.gameTime = time;
		Bukkit.getServer()
				.broadcastMessage(ChatColor.RED + sender.getName() + " has set the game time to " + time + " seconds.");
		return false;
	}
}
