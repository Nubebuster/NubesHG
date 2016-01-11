package com.nubebuster.hg.kits;

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

public class Chemist extends Kit {

	@Override
	public String getKitName() {
		return "Chemist";
	}

	@EventHandler
	public void onSec(SecondEvent event) {
		if (HG.HG.gameTime % 240 == 0)
			for (Gamer g : Gamer.getGamers())
				if (g.getKit() == this) {
					g.getPlayer().getInventory().addItem(getItemss());
					g.getPlayer()
							.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "You got a new batch of pots!");
				}
	}

	private ItemStack[] getItemss() {
		Potion pot1 = new Potion(PotionType.INSTANT_DAMAGE, 2);
		pot1.setSplash(true);
		ItemStack itempot1 = pot1.toItemStack(1);

		Potion pot2 = new Potion(PotionType.POISON, 2);
		pot2.setSplash(true);
		ItemStack itempot2 = pot2.toItemStack(1);

		Potion pot3 = new Potion(PotionType.WEAKNESS, 2);
		pot3.setSplash(true);
		pot3.setHasExtendedDuration(true);
		ItemStack itempot3 = pot3.toItemStack(1);

		ItemStack[] is = { itempot1, itempot2, itempot3 };
		return is;
	}

	// 0 seconds is also pots time.
	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.BOW, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("You receive a new batch of potions every 4 minutes");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Potion of Instant Damage II (6 hearts of damage)");
		list.add("1 Potion of Weakness II (opponent deals 1 heart of damage less)");
		list.add("1 Potion of Poison 2 for 16 seconds (takes opponent's health down to .5 hearts)");
		return list;
	}
}
