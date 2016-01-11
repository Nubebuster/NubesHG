package com.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Kangaroo extends Kit {

	@Override
	public String getKitName() {
		return "Kangaroo";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { createItem(Material.FIREWORK, "§lJump", false) };
	}

	private ArrayList<Player> kanga = new ArrayList<Player>();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (!hasAbillity(p))
			return;
		if (p.getItemInHand().getType() == Material.FIREWORK) {
			if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK
					|| event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
				event.setCancelled(true);
			if (!kanga.contains(p)) {
				if (!(p.isSneaking())) {
					p.setFallDistance(-(4F + 1));
					Vector vector = p.getEyeLocation().getDirection();
					vector.multiply(0.6F);
					vector.setY(1.2F);
					p.setVelocity(vector);
				} else {
					p.setFallDistance(-(4F + 1));
					Vector vector = p.getEyeLocation().getDirection();
					vector.multiply(1.2F);
					vector.setY(0.8);
					p.setVelocity(vector);
				}
				kanga.add(p);
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (kanga.contains(p)) {
			Block b = p.getLocation().getBlock();
			if (b.getType() != Material.AIR || b.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
				kanga.remove(p);
			}

		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player) {
			Player player = (Player) e;
			if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL
					&& player.getInventory().contains(Material.FIREWORK))
				if (event.getDamage() >= 7)
					event.setDamage(7);
		}
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.FIREWORK, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you click your firework you");
		list.add(" get a small launch");
		list.add("Next launch is possible after landing");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Firework");
		return list;
	}
}
