package com.nubebuster.hg.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GM implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("hg.gm") || !(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		if (p.getGameMode() == GameMode.CREATIVE)
			p.setGameMode(GameMode.SURVIVAL);
		else
			p.setGameMode(GameMode.CREATIVE);
		return false;
	}
}
