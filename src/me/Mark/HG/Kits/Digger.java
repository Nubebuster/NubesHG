package me.Mark.HG.Kits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.Mark.HG.HG;

public class Digger extends Kit {

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		if (hasAbillity(p) && event.getBlockPlaced().getType() == Material.DRAGON_EGG) {
			remove(event.getBlockPlaced());
			event.getBlockPlaced().setType(Material.AIR);
			p.sendMessage(ChatColor.RED + "You placed the egg, run!");
		}
	}

	public void remove(final Block b) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(HG.HG, new Runnable() {
			public void run() {
				int dist = (int) Math.ceil(2.0D);
				for (int y = -1; y >= -5; y--)
					for (int x = -dist; x <= dist; x++)
						for (int z = -dist; z <= dist; z++)
							if (b.getY() + y > 0) {
								Block block = b.getWorld().getBlockAt(b.getX() + x, b.getY() + y, b.getZ() + z);
								if (block.getType() != Material.BEDROCK)
									block.setType(Material.AIR);
							}
			}
		}, 60L);
	}

	@Override
	public String getKitName() {
		return "Digger";
	}

	@Override
	public ItemStack[] getItems() {
		ItemStack egg = createItem(Material.DRAGON_EGG, "§lDragon Egg", false);
		egg.setAmount(6);
		return new ItemStack[] { egg };
	}
}
