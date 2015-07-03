package me.Mark.HG.Kits;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Monk extends Kit {

	@Override
	public String getKitName() {
		return "Monk";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { createItem(Material.BLAZE_ROD, "§lMagical Wand", false) };
	}

	private static HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		Player p = event.getPlayer();
		if (!hasAbillity(p) || p.getItemInHand() == null || p.getItemInHand().getType() != Material.BLAZE_ROD
				|| !(event.getRightClicked() instanceof Player))
			return;
		Player vic = (Player) event.getRightClicked();
		if (cooldowns.get(vic.getName()) > System.currentTimeMillis()) {
			p.sendMessage(ChatColor.BOLD + "You can't monk them again yet!");
			return;
		}
		Inventory inv = vic.getInventory();
		int slot = random.nextInt(36);
		ItemStack slis = inv.getItem(slot), heldis = vic.getItemInHand();
		vic.setItemInHand(slis);
		inv.setItem(slot, heldis);
		p.sendMessage(ChatColor.YELLOW + "Monked!");
		cooldowns.put(vic.getName(), System.currentTimeMillis() + 60000);
	}
}
