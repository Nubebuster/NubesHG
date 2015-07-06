package me.Mark.HG.Kits;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import me.Mark.HG.Gamer;
import me.Mark.HG.HG;
import me.Mark.HG.api.SecondEvent;

public class Scout extends Kit {

	@Override
	public String getKitName() {
		return "Scout";
	}

	@EventHandler
	public void onSec(SecondEvent event) {
		if (HG.HG.gameTime % 600 == 0)
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
}
