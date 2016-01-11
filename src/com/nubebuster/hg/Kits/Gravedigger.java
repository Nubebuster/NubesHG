package com.nubebuster.hg.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Gravedigger extends Kit {

	@Override
	public String getKitName() {
		return "Gravedigger";
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onDeath(PlayerDeathEvent event) {
		if (event.getEntity().getKiller() != null && hasAbillity(event.getEntity().getKiller())) {
			Block b = event.getEntity().getLocation().getBlock();
			b.setType(Material.CHEST);
			Chest c = (Chest) b.getState();
			for (ItemStack is : event.getEntity().getPlayer().getInventory()) {
				if (c.getInventory().firstEmpty() == -1)
					continue;
				c.getInventory().addItem(is);
				event.getDrops().remove(is);
			}
		}
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.CHEST, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you kill someone,");
		list.add(" their items go into their grave");
		list.add("Their grave is a chest");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		return list;
	}
}
