package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Miner extends Kit {

	@Override
	public String getKitName() {
		return "Miner";
	}

	@Override
	public ItemStack[] getItems() {
		ItemStack is = createItem(Material.STONE_PICKAXE, "§lMiner's Pick", false);
		is.addEnchantment(Enchantment.DIG_SPEED, 2);
		is.addEnchantment(Enchantment.DURABILITY, 2);
		return new ItemStack[] { is, new ItemStack(Material.APPLE, 5) };
	}
	
	@Override
	protected ItemStack getIcon() {
		return createItem(Material.STONE_PICKAXE, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("You can start mining quickly");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Stone Pickaxe with Efficiency II");
		list.add(" Unbreaking II");
		return list;
	}

/*	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (!hasAbillity(event.getPlayer()))
			return;
		Block b = event.getBlock();
		if (isOre(b.getType()))
			breakAround(b);
	}*/
/*
	private void breakAround(Block b) {
		List<Block> also = new ArrayList<Block>();
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 3; y++)
				for (int z = 0; z < 3; z++) {
					Location loc = new Location(b.getWorld(), b.getX() - 1 + x, b.getY() - 1 + y, b.getZ() - 1 + z);
					if (isOre(loc.getBlock().getType()))
						also.add(loc.getBlock());
				}
		for (Block b2 : also)
			breakAround(b2);
	}*/

	/*private boolean isOre(Material mat) {
		return mat == Material.IRON_ORE || mat == Material.COAL_ORE || mat == Material.GOLD_ORE;
	}*/
}
