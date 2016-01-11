package com.nubebuster.hg.Handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.nubebuster.hg.HG;
import com.nubebuster.hg.api.FeastEvent;

public class Feast implements Listener {

	private static boolean happened = false;
	public static List<ItemStack> items;
	private static Location feast;

	public static Location getFeastLoc() {
		return feast;
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (feast == null)
			return;
		Location loc = event.getBlock().getLocation().clone();
		if (feast.getBlockY() - loc.getBlockY() < 5 && feast.getBlockY() - loc.getBlockY() > -20) {
			loc.setY(feast.getY());
			if (feast.distance(loc) < 22)
				event.setCancelled(true);
		}
	}
	
	public static Location createFeast(boolean forced) {
		if (!forced && happened)
			return null;
		if (!happened)
			addItems();
		happened = true;
		int radius = 20;
		World w = Bukkit.getServer().getWorld("world");
		final Location loc = w.getSpawnLocation();
		loc.setX(getRandom(-100, 100));
		loc.setZ(getRandom(-100, 100));
		int y = 128;
		Material mat = Material.AIR;
		while (mat == Material.AIR || mat == Material.LEAVES || mat == Material.LEAVES_2 || mat == Material.LOG
				|| mat == Material.LOG_2) {
			y--;
			mat = loc.getWorld().getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getType();
		}
		loc.setY(y);
		feast = loc.clone();

		int radiusSquared = radius * radius;

		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if ((x * x) + (z * z) <= radiusSquared) {
					w.getBlockAt((int) loc.getX() + x, (int) loc.getY() - 1, (int) loc.getZ() + z)
							.setType(Material.GRASS);
					for (int i = 0; i < 20; i++) {
						w.getBlockAt((int) loc.getX() + x, (int) loc.getY() + i, (int) loc.getZ() + z)
								.setType(Material.AIR);
					}
				}
			}
		}
		Bukkit.getPluginManager().callEvent(new FeastEvent(loc));
		if (forced) {
			placeChests(loc);
			return loc;
		}
		Bukkit.getServer().broadcastMessage(ChatColor.RED + "Feast will begin at (" + loc.getX() + ", " + (loc.getY())
				+ ", " + loc.getZ() + ") in 5 minutes. /feast to set your compass to the feast.");
		startTimer(loc);
		return loc;
	}

	private static void fillChest(Inventory inv) {
		if (!items.isEmpty()) {
			for (int i = 0; i < inv.getSize(); i++) {
				int chancedia = getRandom(1, 3);
				int chancepot = getRandom(1, 3);
				if (getRandom(1, 7) == 1) {
					int size = items.size() - 1;
					ItemStack item = items.get(getRandom(0, size));
					ItemStack is = new ItemStack(item);
					if (item.getType() == Material.ARROW || item.getType() == Material.MUSHROOM_SOUP) {
						is.setAmount(getRandom(1, 12));
					} else if (item.getType() == Material.COOKED_BEEF || item.getType() == Material.GRILLED_PORK) {
						is.setAmount(getRandom(1, 16));
					} else if (item.getType() == Material.WEB || item.getType() == Material.TNT) {
						is.setAmount(getRandom(1, 8));
					} else if (item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.DIAMOND_HELMET
							|| item.getType() == Material.DIAMOND_CHESTPLATE
							|| item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS
							|| item.getType() == Material.BOW) {
						if (chancedia == 1) {
							is.setType(Material.AIR);
						}
					} else if (item.getType() == Material.POTION) {
						if (chancepot == 1) {
							is.setType(Material.AIR);
						}
					}
					inv.setItem(i, is);
				}
			}
		}
	}

	private static int timer = 300;
	private static int spawnChestTimer;

	public static void startTimer(final Location loc) {
		spawnChestTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.HG, new Runnable() {
			@Override
			public void run() {
				if (timer == -1) {
					Bukkit.getScheduler().cancelTask(spawnChestTimer);
					return;
				}
				if (timer == 0) {
					timer = -1;
					placeChests(loc);
					return;
				}
				if (timer == 180 || timer == 120 || timer == 60) {
					int minutes = timer / 60;
					Bukkit.getServer().broadcastMessage(ChatColor.RED + "Feast will begin at (" + loc.getX() + ", "
							+ (loc.getY()) + ", " + loc.getZ() + ") in " + minutes + " minutes");
					timer--;
					return;
				}
				if (timer == 30 || timer == 15 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2
						|| timer == 1) {
					int seconds = timer;
					Bukkit.getServer().broadcastMessage(ChatColor.RED + "Feast will begin at (" + loc.getX() + ", "
							+ (loc.getY()) + ", " + loc.getZ() + ") in " + seconds + " seconds");
					timer--;
					return;
				}
				timer--;
			}
		}, 20, 20);
	}

	public static void placeChests(Location loc) {
		Block ench = loc.getBlock();
		ench.setType(Material.ENCHANTMENT_TABLE);

		Block chest1 = ench.getRelative(BlockFace.SOUTH_EAST);
		chest1.setType(Material.CHEST);

		Block chest2 = chest1.getRelative(BlockFace.SOUTH_EAST);
		chest2.setType(Material.CHEST);

		Block chest3 = ench.getRelative(BlockFace.SOUTH);
		chest3 = chest3.getRelative(BlockFace.SOUTH);
		chest3.setType(Material.CHEST);

		Block chest4 = ench.getRelative(BlockFace.SOUTH_WEST);
		chest4.setType(Material.CHEST);

		Block chest5 = chest4.getRelative(BlockFace.SOUTH_WEST);
		chest5.setType(Material.CHEST);

		Block chest6 = ench.getRelative(BlockFace.WEST);
		chest6 = chest6.getRelative(BlockFace.WEST);
		chest6.setType(Material.CHEST);

		Block chest7 = ench.getRelative(BlockFace.NORTH_WEST);
		chest7.setType(Material.CHEST);

		Block chest8 = chest7.getRelative(BlockFace.NORTH_WEST);
		chest8.setType(Material.CHEST);

		Block chest9 = ench.getRelative(BlockFace.NORTH);
		chest9 = chest9.getRelative(BlockFace.NORTH);
		chest9.setType(Material.CHEST);

		Block chest10 = ench.getRelative(BlockFace.NORTH_EAST);
		chest10.setType(Material.CHEST);

		Block chest11 = chest10.getRelative(BlockFace.NORTH_EAST);
		chest11.setType(Material.CHEST);

		Block chest12 = ench.getRelative(BlockFace.EAST);
		chest12 = chest12.getRelative(BlockFace.EAST);
		chest12.setType(Material.CHEST);

		Chest chest1s = ((Chest) chest1.getState());
		fillChest(chest1s.getInventory());

		Chest chest2s = ((Chest) chest2.getState());
		fillChest(chest2s.getInventory());

		Chest chest3s = ((Chest) chest3.getState());
		fillChest(chest3s.getInventory());

		Chest chest4s = ((Chest) chest4.getState());
		fillChest(chest4s.getInventory());

		Chest chest5s = ((Chest) chest5.getState());
		fillChest(chest5s.getInventory());

		Chest chest6s = ((Chest) chest6.getState());
		fillChest(chest6s.getInventory());

		Chest chest7s = ((Chest) chest7.getState());
		fillChest(chest7s.getInventory());

		Chest chest8s = ((Chest) chest8.getState());
		fillChest(chest8s.getInventory());

		Chest chest9s = ((Chest) chest9.getState());
		fillChest(chest9s.getInventory());

		Chest chest10s = ((Chest) chest10.getState());
		fillChest(chest10s.getInventory());

		Chest chest11s = ((Chest) chest11.getState());
		fillChest(chest11s.getInventory());

		Chest chest12s = ((Chest) chest12.getState());
		fillChest(chest12s.getInventory());
	}

	@SuppressWarnings("serial")
	public static void addItems() {
		items = new ArrayList<ItemStack>() {
			{
				add(new ItemStack(Material.ARROW));
				add(new ItemStack(Material.MUSHROOM_SOUP));
				add(new ItemStack(Material.COOKED_BEEF));
				add(new ItemStack(Material.GRILLED_PORK));
				add(new ItemStack(Material.WEB));
				add(new ItemStack(Material.TNT));
				add(new ItemStack(Material.DIAMOND_SWORD));
				add(new ItemStack(Material.DIAMOND_HELMET));
				add(new ItemStack(Material.DIAMOND_CHESTPLATE));
				add(new ItemStack(Material.DIAMOND_LEGGINGS));
				add(new ItemStack(Material.DIAMOND_BOOTS));
				add(new ItemStack(Material.BOW));
			}
		};

		ItemStack item = new ItemStack(Material.POTION);
		Potion pot = new Potion(1);
		pot.setType(PotionType.POISON);
		pot.setHasExtendedDuration(false);
		pot.setLevel(2);
		pot.setSplash(true);
		pot.apply(item);
		items.add(item);

		ItemStack item1 = new ItemStack(Material.POTION);
		Potion pot1 = new Potion(1);
		pot1.setType(PotionType.SLOWNESS);
		pot1.setHasExtendedDuration(false);
		pot1.setLevel(1);
		pot1.setSplash(true);
		pot1.apply(item1);
		items.add(item1);

		ItemStack item2 = new ItemStack(Material.POTION);
		Potion pot2 = new Potion(1);
		pot2.setType(PotionType.INSTANT_DAMAGE);
		pot2.setLevel(2);
		pot2.setSplash(true);
		pot2.apply(item2);
		items.add(item2);

		ItemStack item3 = new ItemStack(Material.POTION);
		Potion pot3 = new Potion(1);
		pot3.setType(PotionType.REGEN);
		pot3.setHasExtendedDuration(false);
		pot3.setLevel(2);
		pot3.setSplash(true);
		pot3.apply(item3);
		items.add(item3);

		ItemStack item4 = new ItemStack(Material.POTION);
		Potion pot4 = new Potion(1);
		pot4.setType(PotionType.STRENGTH);
		pot4.setHasExtendedDuration(false);
		pot4.setLevel(2);
		pot4.setSplash(true);
		pot4.apply(item4);
		items.add(item4);
	}

	private static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}

}
