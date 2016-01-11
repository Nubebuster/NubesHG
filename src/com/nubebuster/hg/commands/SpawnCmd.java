package com.nubebuster.hg.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nubebuster.hg.HG;

public class SpawnCmd implements CommandExecutor {

	private Random r = new Random();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player.");
			return false;
		}
		if (HG.HG.preTime == -1) {
			sender.sendMessage(ChatColor.RED + "The game has already begun.");
			return false;
		}
		Player p = (Player) sender;
		int x = r.nextInt(900) - 450, z = r.nextInt(900) - 450;
		p.teleport(p.getWorld().getHighestBlockAt(x, z).getLocation());
		return false;
	}
}
