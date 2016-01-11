package com.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
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
	

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.BOW, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you hit someone, there is a");
		list.add(" 1/3 chance they get Poison for 4 seconds");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		return list;
	}
}
