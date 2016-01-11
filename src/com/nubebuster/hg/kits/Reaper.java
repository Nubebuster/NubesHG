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
	

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.WOOD_HOE, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you someone with your Death Scythe,");
		list.add("They get Wither I");
		list.add("They cannot see their hearts well, ");
		list.add(" and get poison damage too");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Death Scythe");
		return list;
	}
}
