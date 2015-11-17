package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class None extends Kit {

	@Override
	public String getKitName() {
		return "None";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.BARRIER, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("This kit is for the real pro's!");
		list.add("Let's see how well you play");
		list.add(" with a disadvantage...");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Challange");
		return list;
	}
}
