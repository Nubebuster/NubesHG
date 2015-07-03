package me.Mark.HG.Kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Snail extends Kit {

	@Override
	public String getKitName() {
		return "Snail";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] {};
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			if (!hasAbillity((Player) event.getDamager()))
				return;
			Player p = (Player) event.getEntity();
			if (random.nextInt(3) == 1) {
				if (p.hasPotionEffect(PotionEffectType.SLOW))
					p.removePotionEffect(PotionEffectType.SLOW);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 0));
			}
		}
	}
}
