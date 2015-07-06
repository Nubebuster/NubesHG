package me.Mark.HG.Kits;

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
}
