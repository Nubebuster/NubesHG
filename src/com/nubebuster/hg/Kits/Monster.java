package com.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Monster extends Kit {

	@Override
	public String getKitName() {
		return "Monster";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[0];
	}

	@EventHandler
	public void onTarget(EntityTargetLivingEntityEvent event) {
		if (event.getTarget() instanceof Player) {
			if (hasAbillity((Player) event.getTarget()))
				event.setCancelled(true);
		}
	}
	
	@Override
	protected ItemStack getIcon() {
		return createItem(Material.MOB_SPAWNER, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Mobs will not attack you");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		return list;
	}
}
