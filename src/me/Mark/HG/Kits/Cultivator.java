package me.Mark.HG.Kits;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;

public class Cultivator extends Kit {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (!hasAbillity(event.getPlayer()))
			return;
		if (event.getBlock().getType() == Material.SAPLING) {
			event.getBlock().setType(Material.AIR);
			event.getPlayer().getWorld().generateTree(event.getBlock().getLocation(), TreeType.TREE);
		} else {
			event.getBlock().setData((byte) 7);
		}
	}

	@EventHandler
	public void onInt(PlayerInteractEvent event) {
		if (!hasAbillity(event.getPlayer()))
			return;
		if (event.getClickedBlock() == null)
			return;
		Block b = event.getClickedBlock().getRelative(BlockFace.UP);
		if (b.getType() == Material.CROPS) {
			Crops c = (Crops) b.getState();
			c.setState(CropState.RIPE);
		}
	}

	@Override
	public String getKitName() {
		return "Cultivator";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}
}
