package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Mark.HG.HG;

public class Endermage extends Kit {

	private static HashMap<String, Long> cooldown = new HashMap<String, Long>();
	private static List<String> invincible = new ArrayList<String>();

	private static HashMap<String, Integer> times = new HashMap<String, Integer>();
	private static HashMap<String, Integer> task = new HashMap<String, Integer>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerEndermage(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if ((event.getPlayer().getItemInHand().getType() == Material.PORTAL)
				&& (event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock() != null)) {
			event.setCancelled(true);
			p.updateInventory();
			if (!hasAbillity(p))
				return;
			if (HG.HG.GameTime < 120)
				return;
			Block b = event.getClickedBlock();
			if ((b.getRelative(BlockFace.UP).getType() != Material.AIR)
					|| (b.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType() != Material.AIR)) {
				p.sendMessage(ChatColor.RED + "There must be 2 air blocks above the portal to teleport players!");
				return;
			}
			if ((!cooldown.containsKey(p.getName()))
					|| (((Long) cooldown.get(p.getName())).longValue() < System.currentTimeMillis())) {
				Material mat = event.getClickedBlock().getType();
				b.setType(Material.ENDER_PORTAL_FRAME);
				b.setData((byte) 1);

				Location loc2 = b.getLocation();
				loc2.add(0.0D, 1.0D, 0.0D);
				checker(p, loc2, b, mat);
				cooldown.put(p.getName(), Long.valueOf(System.currentTimeMillis() + 5500L));
			}
		}
	}

	public void checker(final Player p, final Location loc2, final Block b, final Material mat) {
		times.put(p.getName(), Integer.valueOf(5));
		task.put(p.getName(), Integer.valueOf(Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.HG, new Runnable() {
			public void run() {
				if (((Integer) times.get(p.getName())).intValue() == 0) {
					b.setType(mat);
					Bukkit.getScheduler().cancelTask(((Integer) task.get(p.getName())).intValue());
					task.remove(p.getName());
					times.remove(p.getName());
					return;
				}

				List<Entity> nearby = p.getNearbyEntities(2.0D, 256.0D, 2.0D);
				List<Entity> teleported = new ArrayList<Entity>();
				for (Entity e : nearby) {
					if (p.getLocation().distance(e.getLocation()) < 2.0D)
						return;
					if ((e instanceof Player)) {
						if (((Player) e).getGameMode() == GameMode.CREATIVE) {
							return;
						}
						Player tp = (Player) e;
						if (!hasAbillity(tp)) {
							p.teleport(loc2);
							tp.teleport(loc2);
							tp.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD
									+ "You got teleported by an endermage. You are invincible for 5 seconds. Prepare to fight!!!");
							invincible.add(tp.getName());
							Remove(tp);
							if (!teleported.contains(tp))
								teleported.add(tp);
						}
					}
				}
				if (teleported.size() >= 1) {
					b.setType(mat);
					p.sendMessage(ChatColor.RED
							+ "Teleport succesful. You are invincible for 5 seconds. Prepare to fight!!!");
					invincible.add(p.getName());
					Remove(p);
					Bukkit.getScheduler().cancelTask(((Integer) task.get(p.getName())).intValue());
					task.remove(p.getName());
					times.remove(p.getName());
					return;
				}

				times.put(p.getName(), Integer.valueOf(((Integer) times.get(p.getName())).intValue() - 1));
			}
		}, 0L, 20L)));
	}

	public void Remove(final Player p) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HG.HG, new Runnable() {
			public void run() {
				invincible.remove(p.getName());
				p.sendMessage(ChatColor.RED + "You are no longer invincible.");
			}
		}, 100L);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player p = (Player) event.getEntity();
			if (invincible.contains(p))
				event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))
				&& ((invincible.contains(((Player) event.getEntity()).getName()))
						|| (invincible.contains(((Player) event.getDamager()).getName()))))
			event.setCancelled(true);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().getType() == Material.PORTAL)
			event.setCancelled(true);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		for (ItemStack is : event.getDrops())
			if (is.getType() == Material.PORTAL)
				is.setType(Material.AIR);
	}

	@Override
	public String getKitName() {
		return "Endermage";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { createItem(Material.PORTAL, "§lPortal", false) };
	}
}
