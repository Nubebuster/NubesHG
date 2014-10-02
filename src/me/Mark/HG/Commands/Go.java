package me.Mark.HG.Commands;

import me.Mark.HG.Gamer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Go implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		Gamer g = Gamer.getGamer((Player) sender);
		if (g.isAlive()) {
			sender.sendMessage(ChatColor.RED + "Spectators only!");
			return false;
		}
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "\"/go <player>\"");
			return false;
		}
		@SuppressWarnings("deprecation")
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(ChatColor.RED + args[0] + " is not online!");
			return false;
		}
		g.getPlayer().teleport(target);
		return false;
	}
}