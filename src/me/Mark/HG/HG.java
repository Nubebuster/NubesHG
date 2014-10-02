package me.Mark.HG;

import java.io.File;
import java.io.IOException;

import me.Mark.HG.Commands.Go;
import me.Mark.HG.Commands.Kitcmd;
import me.Mark.HG.Kits.Kit;
import me.Mark.HG.Listeners.AllTimeListener;
import me.Mark.HG.Listeners.GameListener;
import me.Mark.HG.Listeners.PreGameListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HG extends JavaPlugin {

	public static HG HG;
	public int PreTime = 180, GameTime = -1;
	public FileConfiguration config;

	@Override
	public void onEnable() {
		HG = this;
		configs();
		startPregameTimer();
		Bukkit.getPluginManager().registerEvents(new AllTimeListener(), this);
		registerPreEvents();
		registerCommands();
	}

	@SuppressWarnings("unused")
	private int PreGameTask, GameTask;

	private void startPregameTimer() {
		PreGameTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
				new Runnable() {
					@Override
					public void run() {
						if (PreTime == 180 || PreTime == 120 || PreTime == 60) {
							Bukkit.getServer().broadcastMessage(
									ChatColor.RED + "Tournament starting in "
											+ PreTime / 60 + " minutes.");
						} else if (PreTime == 45 || PreTime == 30
								|| PreTime == 15
								|| (PreTime > 0 && PreTime <= 10)) {
							Bukkit.getServer().broadcastMessage(
									ChatColor.RED + "Tournament starting in "
											+ PreTime + " seconds.");
						} else if (PreTime == 0) {
							PreTime = -1;
							start();
							return;
						}
						PreTime--;
					}
				}, 0, 20);
	}

	private void start() {
		Bukkit.getScheduler().cancelTask(PreGameTask);
		unRegisterPreEvents();
		registerGameEvents();
		Bukkit.getServer().broadcastMessage(
				ChatColor.RED + "The tournament has started!\n"
						+ "There are 10 players participating.\n"
						+ "Everyone is invincible for 2 minutes.\n"
						+ "Good Luck!");
	}

	private Listener preListener, gameListener;

	private void registerGameEvents() {
		gameListener = new GameListener();
		Bukkit.getPluginManager().registerEvents(gameListener, this);
		Kit.registerKitEvents(this);
	}

	private void registerPreEvents() {
		preListener = new PreGameListener();
		Bukkit.getPluginManager().registerEvents(preListener, this);
	}

	private void unRegisterPreEvents() {
		HandlerList.unregisterAll(preListener);
	}

	private void registerCommands() {
		getCommand("kit").setExecutor(new Kitcmd());
		getCommand("go").setExecutor(new Go());
	}

	private void configs() {
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		File file = new File(getDataFolder() + File.separator + "config.yml");
		config = getConfig();
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			config.options().copyDefaults(true);
			saveConfig();
		}
		if (config.getBoolean("rewriteconfig")) {
			try {
				file.delete();
				file.createNewFile();
				config.options().copyDefaults(true);
				saveConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int check() {
		int players = Gamer.getGamers().size();
		if (players <= 1) {
			winner(Gamer.getGamers().get(0));
		}
		return players;
	}

	private static void winner(Gamer gamer) {
		if (gamer == null) {
			shutdown("§eNobody won!");
			return;
		}
		HG.registerPreEvents();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(HG, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().broadcastMessage(
						ChatColor.RED + gamer.getName() + " has won!!!");
			}
		}, 1, 20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(HG, new Runnable() {
			@Override
			public void run() {
				shutdown(ChatColor.RED + gamer.getName()
						+ " has won!!!\nServer restarting.");
			}
		}, 200);
	}

	private static void shutdown(String message) {
		for (Player p : Bukkit.getOnlinePlayers())
			p.kickPlayer(message);
		Bukkit.getServer().shutdown();
	}
}
