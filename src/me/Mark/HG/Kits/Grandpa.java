package me.Mark.HG.Kits;

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
}
