package tmpcom.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class Stomper extends Kit {

	@Override
	public String getKitName() {
		return "Stomper";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] {};
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player p = (Player) event.getEntity();
		if (event.getCause() != DamageCause.FALL || !hasAbillity(p))
			return;
		for (Entity e : p.getNearbyEntities(3, 3, 3)) {
			if (e instanceof LivingEntity)
				((LivingEntity) e).damage(e instanceof Player
						? (((Player) e).isSneaking() ? (((Player) e).isBlocking() ? 6 : 12) : event.getDamage())
						: event.getDamage(), p);
			event.setDamage(event.getDamage() > 4 ? 4 : event.getDamage());
		}
	}
	
	@Override
	protected ItemStack getIcon() {
		return createItem(Material.ANVIL, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you hit the ground, players within");
		list.add(" 3 blocks get your falldamage");
		list.add("Your maximum falldamage is 2 hearts");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		return list;
	}
}
