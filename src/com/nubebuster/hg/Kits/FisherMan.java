package com.nubebuster.hg.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class FisherMan extends Kit {

	@Override
	public String getKitName() {
		return "Fisherman";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { createItem(Material.FISHING_ROD, "Reel 'em in!", false) };
	}

	@EventHandler
	public void onFish(PlayerFishEvent event) {
		if (!hasAbillity(event.getPlayer()))
			return;
		Entity caught = event.getCaught();
		if (caught != null)
			caught.teleport(event.getPlayer());
	}

	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent event) {
		if (event.getItem().getType() == Material.FISHING_ROD && hasAbillity(event.getPlayer()))
			event.setCancelled(true);
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.FISHING_ROD, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("You can catch players and drag them towards you");
		list.add("You can jump over a cliff and drag someone in");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Fishing Rod");
		return list;
	}
}
