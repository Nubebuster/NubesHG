package me.Mark.HG.Kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Vampire extends Kit {

	@Override
	public String getKitName() {
		return "Vampire";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if ((event.getEntity().getKiller() instanceof Player))
			if ((event.getEntity() instanceof Player)) {
				Player p = event.getEntity().getKiller();
				if (hasAbillity(p)) {
					if (p.getHealth() > 18) {
						ItemStack item = new ItemStack(373, 1, (short) 16428);

						p.getInventory().addItem(new ItemStack[] { item });
						p.updateInventory();
					}
					p.setHealth(20);
				}
			} else
				event.getEntity().getKiller().setHealth(20);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player p = (Player) event.getEntity();
			if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC && hasAbillity(p)) {
				event.setDamage(0);
				if (p.getHealth() + 5 > 20)
					p.setHealth(20);
				else
					p.setHealth(p.getHealth() + 5);
			}
		}
	}
}
