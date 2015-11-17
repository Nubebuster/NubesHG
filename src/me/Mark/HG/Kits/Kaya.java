package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class Kaya extends Kit {

	@Override
	public String getKitName() {
		return "Kaya";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { new ItemStack(Material.GRASS, 10) };
	}

	public Kaya() {
		ShapelessRecipe re = new ShapelessRecipe(new ItemStack(Material.GRASS));
		re.addIngredient(Material.DIRT);
		re.addIngredient(Material.SEEDS);
		Bukkit.getServer().addRecipe(re);
	}

	public int distanceFromBlocks = 3;
	private transient HashMap<Block, String> kayaBlocks = new HashMap<Block, String>();
	public int lowerHeightFromBlocks = -2;
	public int upperHeightFromBlocks = 2;

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (!event.isCancelled())
			this.kayaBlocks.remove(event.getBlock());
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCraft(PrepareItemCraftEvent event) {
		if ((event.getRecipe().getResult() != null) && (event.getRecipe().getResult().getType() == Material.GRASS)) {
			for (HumanEntity entity : event.getViewers())
				if (hasAbillity((Player) entity))
					return;
			event.getInventory().setItem(0, new ItemStack(0, 0));
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		for (ItemStack is : event.getDrops())
			if (is.getType() == Material.GRASS)
				is.setType(Material.AIR);
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		for (Block b : event.blockList())
			this.kayaBlocks.remove(b);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (p.getGameMode() == GameMode.SURVIVAL && !hasAbillity(p)) {
			Location loc = event.getPlayer().getLocation();
			for (int z = -this.distanceFromBlocks; z <= this.distanceFromBlocks; z++)
				for (int x = -this.distanceFromBlocks; x <= this.distanceFromBlocks; x++)
					for (int y = this.lowerHeightFromBlocks; y <= this.upperHeightFromBlocks; y++) {
						Block block = loc.clone().add(x, y, z).getBlock();
						if ((this.kayaBlocks.containsKey(block)) && (block.getType() == Material.GRASS)
								&& (this.kayaBlocks.get(block) != event.getPlayer().getName())) {
							block.setType(Material.AIR);
							this.kayaBlocks.remove(block);
						}
					}
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		if ((!event.isCancelled()) && (event.getBlock().getType() == Material.GRASS) && hasAbillity(p))
			this.kayaBlocks.put(event.getBlock(), event.getPlayer().getName());
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.GRASS, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Grass Blocks you place disappear when");
		list.add(" someone comes close to them");
		list.add("You can craft new grass with seeds and ");
		list.add(" dirt");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("10 Grass Blocks");
		return list;
	}
}
