package tmpcom.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import tmpcom.nubebuster.hg.api.SecondEvent;

public class Dwarf extends Kit {

	@Override
	public String getKitName() {
		return "Dwarf";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	public int cooldown = 5;
	private HashMap<String, Long> cooldownExpires = new HashMap<String, Long>();
	public static HashMap<String, Long> startedSneaking = new HashMap<String, Long>();

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		Player p = event.getPlayer();
		if (hasAbillity(p)) {
			if (cooldownExpires.containsKey(p.getName())
					&& this.cooldownExpires.get(p.getName()) < System.currentTimeMillis())
				this.cooldownExpires.remove(p.getName());
			if (event.isSneaking()) {
				startedSneaking.put(p.getName(), System.currentTimeMillis());
			} else if (startedSneaking.containsKey(p.getName())) {
				cooldownExpires.put(p.getName(), System.currentTimeMillis() + cooldown * 1000);
				long sneakingTime = System.currentTimeMillis() - startedSneaking.get(p.getName());
				if (sneakingTime > 10000L)
					sneakingTime = 10000L;
				double knockBack = 0.5D * (sneakingTime / 1000L);
				for (Entity entity : p.getNearbyEntities(knockBack, knockBack, knockBack)) {
					if ((!(entity instanceof Player)) || (!((Player) entity).isSneaking())) {
						Vector vector = entity.getLocation().toVector().subtract(p.getLocation().toVector())
								.normalize();
						entity.setVelocity(vector.multiply(knockBack));
					}
				}
			}
		}
	}

	@EventHandler
	public void onSecond(SecondEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (hasAbillity(p) && p.isSneaking()) {
				long sneakingTime = System.currentTimeMillis() - startedSneaking.get(p.getName());
				if (sneakingTime > 10000L)
					sneakingTime = 10000L;
				p.sendMessage(ChatColor.GREEN + "Dwarf load: " + sneakingTime / 100L + "%");
			}
		}
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.WOOD_AXE, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you sneak, you charge up for a launch!");
		list.add("Sneak for 5 seconds to chargy fully");
		list.add(" and when you release, any players within 2.5 blocks");
		list.add(" will be launched into the sky");
		list.add("Victims can sneak to not get launched");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		return list;
	}
}
