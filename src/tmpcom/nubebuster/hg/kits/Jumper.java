package tmpcom.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.ENDER_PEARL, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Ender Pearls do no damage to you");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("6 Ender Pearls");
		return list;
	}
}
