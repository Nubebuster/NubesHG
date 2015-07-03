package me.Mark.HG.Kits;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class Stomper extends Kit {

	@Override
	public String getKitName() {
		return "Stomper";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] {};
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player p = (Player) event.getEntity();
		if (event.getCause() != DamageCause.FALL || !hasAbillity(p))
			return;
		for (Entity e : p.getNearbyEntities(2.5, 3, 2.5)) {
			if (e instanceof LivingEntity)
				((LivingEntity) e).damage(e instanceof Player
						? (((Player) e).isSneaking() ? (((Player) e).isBlocking() ? 6 : 12) : event.getDamage())
						: event.getDamage(), p);
			event.setDamage(event.getDamage() > 4 ? 4 : event.getDamage());
		}
	}
}
