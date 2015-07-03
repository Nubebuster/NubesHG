package me.Mark.HG.Kits;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
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
}
