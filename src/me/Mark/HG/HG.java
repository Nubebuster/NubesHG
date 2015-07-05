package me.Mark.HG;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.Mark.HG.Commands.GM;
import me.Mark.HG.Commands.GoCmd;
import me.Mark.HG.Commands.KitCmd;
import me.Mark.HG.Commands.Lag;
import me.Mark.HG.Kits.Kit;
import me.Mark.HG.Listeners.AllTimeListener;
import me.Mark.HG.Listeners.GameListener;
import me.Mark.HG.Listeners.PreGameListener;
import me.Mark.HG.Utils.Cakes;
import me.Mark.HG.Utils.Undroppable;

/**
 * @author Mark Cockram - NubeBuster
 * @see taking credit for this code without mentioning my name is against the
 *      license!
 */
public class HG extends JavaPlugin {

	public static HG HG;
	public int preTime, gameTime = -1;
	public FileConfiguration config;

	private int minimumPlayers, resetTime, feastTime;

	@Override
	public void onEnable() {
		HG = this;
		configs();
		Kit.init();
		Bukkit.getPluginManager().registerEvents(new AllTimeListener(), this);
		startPregameTimer();
		registerPreEvents();
		registerCommands();
		registerEnchantments();

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 20L, 1L);

		for (Player p : Bukkit.getOnlinePlayers())
			new Gamer(p);
	}

	@SuppressWarnings("unused")
	private int preGameTask, gameTask;

	private void startPregameTimer() {
		preGameTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				if (preTime == 180 || preTime == 120 || preTime == 60) {
					Bukkit.getServer()
							.broadcastMessage(ChatColor.RED + "Tournament starting in " + preTime / 60 + " minutes.");
				} else if (preTime == 45 || preTime == 30 || preTime == 15 || (preTime > 0 && preTime <= 10)) {
					Bukkit.getServer()
							.broadcastMessage(ChatColor.RED + "Tournament starting in " + preTime + " seconds.");
				} else if (preTime == 0) {
					if (Gamer.getAliveGamers().size() <= minimumPlayers) {
						Bukkit.getServer().broadcastMessage(ChatColor.RED + "Not enough players to start.");
						preTime = resetTime;
						return;
					}
					preTime = -1;
					start();
					return;
				}
				preTime--;
			}
		}, 0, 20);
	}

	private void startGameTimer() {
		gameTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				if (gameTime < 120) {
					int tim = 120 - gameTime;
					if (tim == 120 || tim == 60)
						Bukkit.getServer().broadcastMessage(
								ChatColor.RED + "Invincibillity wears off in " + ((tim) / 60) + " minutes.");
					else if (tim == 30 || tim == 15 || (tim <= 10 && tim > 0))
						Bukkit.getServer().broadcastMessage(
								ChatColor.RED + "Invincibillity wears off in " + (120 - gameTime) + " seconds.");
				} else if (gameTime == 120) {
					Bukkit.getServer().broadcastMessage(ChatColor.RED + "The invincibillity has worn off!");
				} else if (gameTime == feastTime) {
					Feast.createFeast();
				} else if (gameTime > 3600) {
					shutdown(ChatColor.RED + "Game went on for too long!\nRestarting...");
				}
				gameTime++;
			}
		}, 0, 20);
	}

	private void start() {
		Bukkit.getScheduler().cancelTask(preGameTask);
		unRegisterPreEvents();
		registerGameEvents();
		int parts = 0;
		for (Gamer g : Gamer.getGamers())
			if (g.isAlive())
				parts++;
		Bukkit.getServer().broadcastMessage(ChatColor.RED + "The tournament has started!\n" + "There are " + parts
				+ " players participating.\n" + "Everyone is invincible for 2 minutes.\n" + "Good Luck!");
		World world = Bukkit.getWorld("world");
		for (Gamer g : Gamer.getGamers()) {
			if (!g.isAlive())
				continue;
			g.getPlayer().getInventory().clear();
			g.getPlayer().closeInventory();
			g.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
			g.applyKit();
			g.getPlayer().teleport(new Location(world, getRandom(-100, 100), 80, getRandom(-100, 100)));
		}
		startGameTimer();
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
		getCommand("kit").setExecutor(new KitCmd());
		getCommand("go").setExecutor(new GoCmd());
		getCommand("lag").setExecutor(new Lag());
		getCommand("gm").setExecutor(new GM());
	}

	private void registerEnchantments() {
		try {
			try {
				Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Enchantment.registerEnchantment(Undroppable.ench);
			} catch (IllegalArgumentException e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		preTime = config.getInt("pregamedelay");
		minimumPlayers = config.getInt("minimum");
		resetTime = config.getInt("retime");
		feastTime = config.getInt("feast");
	}

	public static int check() {
		int players = Gamer.getAliveGamers().size();
		if (players <= 1) {
			if (Gamer.getAliveGamers().size() > 0)
				winner(Gamer.getAliveGamers().get(0));
			else
				winner(null);
		}
		return players;
	}

	private static void winner(final Gamer gamer) {
		if (gamer == null) {
			shutdown(ChatColor.RED + "Nobody won!");
			return;
		}
		HG.registerPreEvents();
		Cakes.cakes(gamer.getPlayer());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(HG, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().broadcastMessage(ChatColor.RED + gamer.getName() + " has won!!!");
			}
		}, 1, 20);
		Bukkit.getScheduler().scheduleSyncDelayedTask(HG, new Runnable() {
			@Override
			public void run() {
				shutdown(ChatColor.RED + gamer.getName() + " has won!!!\nServer restarting.");
			}
		}, 200);
	}

	private static void shutdown(String message) {
		for (Player p : Bukkit.getOnlinePlayers())
			p.kickPlayer(message);
		Bukkit.getServer().shutdown();
	}

	private static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}
}
