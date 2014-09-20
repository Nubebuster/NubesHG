package me.Mark.HG;

import java.io.File;
import java.io.IOException;

import me.Mark.HG.Listeners.GameListener;
import me.Mark.HG.Listeners.PreGameListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HG extends JavaPlugin {

	public static HG HG;
	public static int PreTime = 180, GameTime = -1;
	public static FileConfiguration config;

	@Override
	public void onEnable() {
		HG = this;
		configs();

		startPregameTimer();

		registerPreEvents();
		registerCommands();
	}

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
						}
						PreTime--;
					}
				}, 0, 20);
	}

	private void start() {
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
	}

	private void registerPreEvents() {
		preListener = new PreGameListener();
		Bukkit.getPluginManager().registerEvents(preListener, this);
	}

	private void unRegisterPreEvents() {
		HandlerList.unregisterAll(preListener);
	}

	private void registerCommands() {
	}

	private void configs() {
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		File file = new File(getDataFolder() + File.separator
				+ "config.yml");
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
}
