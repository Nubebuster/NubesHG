package me.Mark.HG.Kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Tank extends Kit {

	@Override
	public String getKitName() {
		return "Tank";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if ((event.getEntity().getKiller() instanceof Player)) {
			if (hasAbillity((Player) event.getEntity().getKiller()))
				event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 5.0F);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (((event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
				|| (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION))
				&& ((event.getEntity() instanceof Player))) {
			Player p = (Player) event.getEntity();
			if (hasAbillity(p))
				event.setCancelled(true);
		}
	}
}
