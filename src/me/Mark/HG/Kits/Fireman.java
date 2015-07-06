package me.Mark.HG.Kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Fireman extends Kit {

	@Override
	public String getKitName() {
		return "Fireman";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { createItem(Material.WATER_BUCKET, "§lWater Bucket", false) };
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player))
			if ((event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)
					|| (event.getCause() == EntityDamageEvent.DamageCause.FIRE)
					|| (event.getCause() == EntityDamageEvent.DamageCause.LAVA)
					|| (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING))
				if (hasAbillity((Player) event.getEntity()))
					event.setCancelled(true);
	}
}
