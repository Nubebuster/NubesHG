package tmpcom.nubebuster.hg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tmpcom.nubebuster.hg.Gamer;

public class InvCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player.");
			return false;
		}
		if(!sender.hasPermission("hg.inv")) {
			sender.sendMessage(ChatColor.RED + "No permission.");
			return false;
		}
		Gamer g = Gamer.getGamer((Player) sender);
		if (g.isAlive()) {
			sender.sendMessage(ChatColor.RED + "You must be a spectator to do this.");
			return false;
		}
		if (args.length == 0) {
			Player nearest = null;
			double distance = 0;
			for (Gamer og : Gamer.getAliveGamers()) {
				double dis = og.getPlayer().getLocation().distance(g.getPlayer().getLocation());
				if (dis < distance) {
					nearest = og.getPlayer();
					distance = dis;
				}
			}
			if (nearest == null) {
				sender.sendMessage(ChatColor.RED + "No nearby player could be found.");
				return false;
			}
			g.getPlayer().openInventory(nearest.getInventory());
			sender.sendMessage(ChatColor.GRAY + "Showing inventory of the neareast player, " + nearest.getName());
			return false;
		}
		@SuppressWarnings("deprecation")
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(ChatColor.RED + "That player is not online.");
			return false;
		}
		if (!Gamer.getGamer(target).isAlive()) {
			sender.sendMessage(ChatColor.RED + "That player is not alive.");
			return false;
		}
		g.getPlayer().openInventory(target.getInventory());
		return false;
	}
}
