package com.nubebuster.hg.Kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public class Ninja extends Kit {

	@Override
	public String getKitName() {
		return "Ninja";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	private static HashMap<String, Pair> ninjas = new HashMap<String, Pair>();
	private static HashMap<String, Long> cooldown = new HashMap<String, Long>();

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if (!hasAbillity(damager))
				return;
			Player victim = (Player) event.getEntity();
			ninjas.put(damager.getName(), new Pair(victim.getName()));
		}
	}

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		Player p = event.getPlayer();
		if (p.isSneaking() && hasAbillity(p)) {
			if (cooldown.containsKey(p.getName()) && cooldown.get(p.getName()) > System.currentTimeMillis() + 10000) {
				p.sendMessage(ChatColor.RED + "You are still on cooldown for another "
						+ Math.ceil(((System.currentTimeMillis() + 10000D - cooldown.get(p.getName())) / 1000D))
						+ " seconds.");
				return;
			}
			if (ninjas.containsKey(p.getName())) {
				Pair pa = ninjas.get(p.getName());
				if (pa.getTime() > System.currentTimeMillis() + 10000) {
					@SuppressWarnings("deprecation")
					Player vic = Bukkit.getPlayer(pa.getVictim());
					if (vic == null)
						return;
					p.teleport(vic);
					ninjas.remove(p.getName());
					cooldown.put(p.getName(), System.currentTimeMillis());
				}
			}
		}
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.FEATHER, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("If you sneak within 10 seconds");
		list.add(" after hitting someone,");
		list.add(" you will be teleported to them");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		return list;
	}
}

class Pair {

	private String victim;
	private long time;

	public Pair(String victim) {
		this.victim = victim;
		this.time = System.currentTimeMillis();
	}

	public String getVictim() {
		return victim;
	}

	public long getTime() {
		return time;
	}
}