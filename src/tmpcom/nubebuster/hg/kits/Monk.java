package tmpcom.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		if (cooldowns.containsKey(vic.getName()) && cooldowns.get(vic.getName()) > System.currentTimeMillis()) {
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

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.BLAZE_ROD, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you hit your opponent with your");
		list.add(" magic wand, the item in their hand");
		list.add(" is replaced with a random item in");
		list.add(" their inventory");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Magic Wand");
		return list;
	}
}
