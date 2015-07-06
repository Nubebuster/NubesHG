package me.Mark.HG.Kits;

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
}
