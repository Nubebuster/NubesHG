package me.Mark.HG.Kits;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

public class Jumper extends Kit {

	@Override
	public String getKitName() {
		return "Jumper";
	}

	@Override
	public ItemStack[] getItems() {
		ItemStack pearl = createItem(Material.ENDER_PEARL, "§lTeleporter", false);
		pearl.setAmount(6);
		return new ItemStack[] { pearl };
	}

	@EventHandler
	public void onPearl(PlayerTeleportEvent event) {
		if (event.getCause() == TeleportCause.ENDER_PEARL)
			if (hasAbillity(event.getPlayer())) {
				event.setCancelled(true);
				event.getPlayer().teleport(event.getTo());
				event.getPlayer().setFallDistance(0f);
			}
	}
}
