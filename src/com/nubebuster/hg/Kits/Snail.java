package com.nubebuster.hg.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
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
	
	@Override
	protected ItemStack getIcon() {
		return createItem(Material.BOW, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you hit your target, there is");
		list.add(" 1/3 chance they get Slowness I for 4 seconds");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Bow");
		return list;
	}
}
