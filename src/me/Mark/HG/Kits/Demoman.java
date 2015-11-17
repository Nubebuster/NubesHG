package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Demoman extends Kit {

	@Override
	public String getKitName() {
		return "Demoman";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { new ItemStack(Material.GRAVEL, 8), new ItemStack(Material.STONE_PLATE, 8) };
	}

	private static List<Block> traps = new ArrayList<Block>();

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (!hasAbillity(event.getPlayer()))
			return;
		Block placed = event.getBlock();
		if (placed.getType() != Material.STONE_PLATE)
			return;
		if (placed.getRelative(BlockFace.DOWN).getType() != Material.GRAVEL)
			return;
		traps.add(placed);
		event.getPlayer().sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Trap Placed!");
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (traps.contains(event.getBlock()))
			traps.remove(event.getBlock());
	}

	@EventHandler
	public void onExplode(BlockExplodeEvent event) {
		if (traps.contains(event.getBlock()))
			traps.remove(event.getBlock());
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (!(event.getAction() == Action.PHYSICAL))
			return;
		Block b = event.getClickedBlock();
		Location ex = b.getRelative(BlockFace.UP).getLocation();
		if (b.getType() == Material.STONE_PLATE && traps.contains(b)) {
			traps.remove(b);
			b.setType(Material.AIR);
			b.getWorld().createExplosion(ex, 5F);
		}
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.STONE_PLATE, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you place a stone pressure plate over gravel");
		list.add(" it turns into a landmine!");
		list.add("Do not step on your own pressure plate!");
		list.add("You can cook stone and dig new gravel");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("8 Gravel");
		list.add("8 Stone Pressure Plates");
		return list;
	}
}
