package com.nubebuster.hg.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.nubebuster.hg.HG;

/**
 * @author Mark Cockram - NubeBuster. 
 * Removing or disabling this class will disobey the
 *         License!
 */
public class HGCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "This server is running NubesHG version "
				+ HG.HG.getDescription().getVersion() + " made by NubeBuster. You can download it here: " + HG.HG.getDescription().getWebsite());
		return false;
	}
}
