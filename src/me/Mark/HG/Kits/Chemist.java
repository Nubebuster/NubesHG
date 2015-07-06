package me.Mark.HG.Kits;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import me.Mark.HG.Gamer;
import me.Mark.HG.HG;
import me.Mark.HG.api.SecondEvent;

public class Chemist extends Kit {

	@Override
	public String getKitName() {
		return "Chemist";
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
}
