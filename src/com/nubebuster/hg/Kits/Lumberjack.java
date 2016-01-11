package com.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Lumberjack extends Kit {

	@Override
	public String getKitName() {
		return "Lumberjack";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { new ItemStack(Material.WOOD_AXE) };
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.LOG && hasAbillity(event.getPlayer())) {
			Block b = event.getBlock();
			while (b.getType() == Material.LOG) {
				b.breakNaturally();
				b = b.getRelative(BlockFace.UP);
			}
		}
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.LOG, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you break a log, all logs above");
		list.add(" break and drop too");
		list.add("You can make big traps/buildings with this");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Wooden Axe");
		return list;
	}
}
