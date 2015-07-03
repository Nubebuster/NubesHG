package me.Mark.HG.Kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Viper extends Kit {

	@Override
	public String getKitName() {
		return "Viper";
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
				if (p.hasPotionEffect(PotionEffectType.POISON))
					p.removePotionEffect(PotionEffectType.POISON);
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0));
			}
		}
	}
}
