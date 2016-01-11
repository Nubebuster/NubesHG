package tmpcom.nubebuster.hg.commands;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Lag implements Runnable, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
		double tps = doubleRoundTo2Decimals(getTPS());
		int lag = (int) Math.round((20 - tps) / 20 * 100);
		if (tps > 19.9)
			tps = 20;
		if (lag < 0)
			lag = 0;
		sender.sendMessage(ChatColor.GOLD + "Server running at " + tps + " tps");
		sender.sendMessage(ChatColor.GOLD + "Lag is approx " + lag + "%");
		return false;
	}

	public Double doubleRoundTo2Decimals(double val) {
		try {
			DecimalFormat df = new DecimalFormat("##.##");
			return Double.valueOf(df.format(val));
		} catch (Exception e) {
			DecimalFormat df = new DecimalFormat("##,##");
			return Double.valueOf(df.format(val));
		}
	}

	public static int TICK_COUNT = 0;
	public static long[] TICKS = new long[600];
	public static long LAST_TICK = 0L;

	public static double getTPS() {
		return getTPS(100);
	}

	public static double getTPS(int ticks) {
		if (TICK_COUNT < ticks) {
			return 20.0D;
		}
		int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
		long elapsed = System.currentTimeMillis() - TICKS[target];

		return ticks / (elapsed / 1000.0D);
	}

	public static long getElapsed(int tickID) {
		if (TICK_COUNT - tickID >= TICKS.length) {
		}

		long time = TICKS[(tickID % TICKS.length)];
		return System.currentTimeMillis() - time;
	}

	@Override
	public void run() {
		TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();

		TICK_COUNT += 1;
	}
}
