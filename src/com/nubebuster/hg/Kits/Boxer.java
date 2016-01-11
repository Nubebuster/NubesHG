package com.nubebuster.hg.Kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Boxer extends Kit {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if ((event.getDamager() instanceof Player)) {
			Player p = (Player) event.getDamager();
			if (hasAbillity(p) && (p.getItemInHand() == null))
				event.setDamage(5);
		}
		if ((event.getEntity() instanceof Player)) {
			Player damaged = (Player) event.getEntity();
			if (hasAbillity(damaged) && (event.getDamage() > 1))
				event.setDamage(event.getDamage() - 1);
		}
	}

	@Override
	public String getKitName() {
		return "Boxer";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.STONE_SWORD, getKitName(), true);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = getNewStringList();
		list.add("Your fist deals the same amount of damage as a stone sword");
		list.add("All sword damage you get is reduced by 1hp");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		return getNewStringList();
	}
}
