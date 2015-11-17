package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Fireman extends Kit {

	@Override
	public String getKitName() {
		return "Fireman";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { createItem(Material.WATER_BUCKET, "§lWater Bucket", false) };
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player))
			if ((event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)
					|| (event.getCause() == EntityDamageEvent.DamageCause.FIRE)
					|| (event.getCause() == EntityDamageEvent.DamageCause.LAVA)
					|| (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING))
				if (hasAbillity((Player) event.getEntity()))
					event.setCancelled(true);
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.WATER_BUCKET, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("You are immume for fire and lava");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Water Bucket");
		return list;
	}
}
