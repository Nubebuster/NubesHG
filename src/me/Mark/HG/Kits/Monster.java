package me.Mark.HG.Kits;

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
}
