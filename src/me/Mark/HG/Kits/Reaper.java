package me.Mark.HG.Kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Reaper extends Kit {

	@Override
	public String getKitName() {
		return "Reaper";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { createItem(Material.WOOD_HOE, "§lDeath Scythe", false) };
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if (damager.getItemInHand() == null || damager.getItemInHand().getType() != Material.WOOD_HOE
					|| !hasAbillity(damager))
				return;
			Player p = (Player) event.getEntity();
			if (random.nextInt(3) == 1) {
				if (p.hasPotionEffect(PotionEffectType.WITHER))
					p.removePotionEffect(PotionEffectType.WITHER);
				p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 0));
			}
		}
	}
}
