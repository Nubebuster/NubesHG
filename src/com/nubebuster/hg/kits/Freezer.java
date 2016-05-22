package com.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.nubebuster.hg.Gamer;
import com.nubebuster.hg.HG;

import net.md_5.bungee.api.ChatColor;

public class Freezer extends Kit {

	@Override
	public String getKitName() {
		return "Freezer";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { getBall(16) };
	}

	private ItemStack getBall(int amount) {
		ItemStack balls = createItem(Material.SNOW_BALL, "Freezer", false);
		balls.setAmount(amount);
		return balls;
	}

	@Override
	protected ItemStack getIcon() {
		return new ItemStack(Material.SNOW_BALL);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you hit someone with a freezer (snowball)");
		list.add("They are surrounded with packed ice");
		list.add("You can prevent people from running away");
		list.add("The ice disappears after 5 seconds.");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = new ArrayList<String>();
		list.add("16 Freezer Balls");
		return list;
	}

	private static final int COOLDOWN_TIME = 30000;

	HashMap<Snowball, Gamer> balls = new HashMap<Snowball, Gamer>();
	HashMap<Gamer, Long> cooldown = new HashMap<Gamer, Long>();

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Snowball) {
			Snowball b = (Snowball) event.getDamager();
			if (balls.containsKey(b)) {
				Gamer g = balls.get(b);
				if (isOnCooldown(g))
					return;
				balls.remove(b);
				cooldown.put(g, System.currentTimeMillis() + COOLDOWN_TIME);
				// spawn thing
				Location l = event.getEntity().getLocation();
				makeCylinder(new Vector(l.getBlockX(), l.getBlockY() - 3, l.getBlockZ()), 3, 3, 6, false);
			}
		}
	}

	public int makeCylinder(Vector pos, double radiusX, double radiusZ, int height, boolean filled) {
		int affected = 0;

		radiusX += 0.5;
		radiusZ += 0.5;

		if (height == 0) {
			return 0;
		} else if (height < 0) {
			height = -height;
			pos = pos.subtract(new Vector(0, height, 0));
		}

		final double invRadiusX = 1 / radiusX;
		final double invRadiusZ = 1 / radiusZ;

		final int ceilRadiusX = (int) Math.ceil(radiusX);
		final int ceilRadiusZ = (int) Math.ceil(radiusZ);

		double nextXn = 0;
		forX: for (int x = 0; x <= ceilRadiusX; ++x) {
			final double xn = nextXn;
			nextXn = (x + 1) * invRadiusX;
			double nextZn = 0;
			forZ: for (int z = 0; z <= ceilRadiusZ; ++z) {
				final double zn = nextZn;
				nextZn = (z + 1) * invRadiusZ;

				double distanceSq = lengthSq(xn, zn);
				if (distanceSq > 1) {
					if (z == 0) {
						break forX;
					}
					break forZ;
				}

				if (!filled) {
					if (lengthSq(nextXn, zn) <= 1 && lengthSq(xn, nextZn) <= 1) {
						continue;
					}
				}

				for (int y = 0; y < height; ++y) {
					setBlock(pos.clone().add(new Vector(x, y, z)), Material.PACKED_ICE);
					setBlock(pos.clone().add(new Vector(-x, y, z)), Material.PACKED_ICE);
					setBlock(pos.clone().add(new Vector(x, y, -z)), Material.PACKED_ICE);
					setBlock(pos.clone().add(new Vector(-x, y, -z)), Material.PACKED_ICE);
				}
			}
		}

		return affected;
	}

	private void setBlock(Vector v, Material mat) {
		final Location l = new Location(Bukkit.getWorld(HG.HG.config.getString("world")), v.getX(), v.getY(), v.getZ());
		if (l.getBlock().getType() != Material.AIR)
			return;
		l.getBlock().setType(mat);
		Bukkit.getScheduler().scheduleSyncDelayedTask(HG.HG, new Runnable() {
			public void run() {
				if (l.getBlock().getType() == Material.PACKED_ICE)
					l.getBlock().setType(Material.AIR);
			}
		}, 100);
	}

	private static double lengthSq(double x, double z) {
		return (x * x) + (z * z);
	}

	@EventHandler
	public void onShoot(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof Snowball && event.getEntity().getShooter() instanceof Player) {
			Gamer g = Gamer.getGamer((Player) event.getEntity().getShooter());
			if (isOnCooldown(g)) {
				g.getPlayer().sendMessage(ChatColor.RED + "You are on cooldown for another "
						+ ((cooldown.get(g) - System.currentTimeMillis()) / 1000) + " seconds.");
				event.setCancelled(true);
				g.getPlayer().getInventory().addItem(getBall(1));
				return;
			}
			balls.put((Snowball) event.getEntity(), g);
		}
	}

	private boolean isOnCooldown(Gamer g) {
		return cooldown.get(g) != null && cooldown.get(g) > System.currentTimeMillis();
	}
}
