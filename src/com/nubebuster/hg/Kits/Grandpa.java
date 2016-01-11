package com.nubebuster.hg.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Grandpa extends Kit {

	@Override
	public String getKitName() {
		return "Grandpa";
	}

	@Override
	public ItemStack[] getItems() {
		ItemStack stick = createItem(Material.STICK, "§lGrandpa's Walking Stick", false);
		stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		return new ItemStack[] { stick };
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.BOW, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("You can easily hit people off towers");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Stick with Knockback II");
		return list;
	}
}
