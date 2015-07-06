package me.Mark.HG.Kits;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Mark.HG.HG;

public class Cannibal extends Kit {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
			Player damager = (Player) event.getDamager();
			if (hasAbillity(damager)) {
				if (HG.HG.gameTime < 120) {
					return;
				}
				Player damaged = (Player) event.getEntity();
				if (new Random().nextInt(3) == 1)
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 0), true);
				if (damager.getFoodLevel() < 20) {
					damager.setFoodLevel(damager.getFoodLevel() + 2);
					if (damaged.getFoodLevel() > 1)
						damaged.setFoodLevel(damaged.getFoodLevel() - 2);
				}
			}
		}
	}

	public ItemStack[] getItems() {
		ItemStack fish = new ItemStack(Material.RAW_FISH);
		@SuppressWarnings("deprecation")
		ItemStack egg = new ItemStack(383, 1, (short) 98);
		return new ItemStack[] { egg, fish };
	}

	@Override
	public String getKitName() {
		return "Cannibal";
	}
}
