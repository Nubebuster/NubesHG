package com.nubebuster.hg.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.nubebuster.hg.Gamer;
import com.nubebuster.hg.HG;
import com.nubebuster.hg.api.SecondEvent;

public class Scout extends Kit {

	@Override
	public String getKitName() {
		return "Scout";
	}

	@EventHandler
	public void onSec(SecondEvent event) {
		if (HG.HG.gameTime % 240 == 0)
			for (Gamer g : Gamer.getGamers())
				if (g.getKit() == this) {
					g.getPlayer().getInventory().addItem(getItemss());
					g.getPlayer().sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "You got some pots!");
				}
	}

	private ItemStack[] getItemss() {
		Potion pot1 = new Potion(PotionType.SPEED, 2);
		pot1.setSplash(true);
		ItemStack itempot1 = pot1.toItemStack(2);
		return new ItemStack[] { itempot1 };
	}

	// 0 seconds is also pots time.
	@Override
	public ItemStack[] getItems() {
		return null;
	}
	
	@Override
	protected ItemStack getIcon() {
		return createItem(Material.FEATHER, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("You get a new batch of potions every 4 minutes");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("2 Potions of Swiftness II");
		return list;
	}
}
