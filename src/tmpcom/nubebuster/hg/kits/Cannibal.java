package tmpcom.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import tmpcom.nubebuster.hg.HG;

public class Cannibal extends Kit {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
			Player damager = (Player) event.getDamager();
			if (hasAbillity(damager)) {
				if (HG.HG.gameTime < 120) {
					return;
				}
				Player damaged = (Player) event.getEntity();
				if (new Random().nextInt(3) == 1)
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 120, 0), true);
				if (damager.getFoodLevel() < 20) {
					damager.setFoodLevel(damager.getFoodLevel() + 2);
					if (damaged.getFoodLevel() > 1)
						damaged.setFoodLevel(damaged.getFoodLevel() - 2);
				}
			}
		}
	}

	public ItemStack[] getItems() {
		ItemStack fish = new ItemStack(Material.RAW_FISH);
		@SuppressWarnings("deprecation")
		ItemStack egg = new ItemStack(383, 1, (short) 98);
		return new ItemStack[] { egg, fish };
	}

	@Override
	public String getKitName() {
		return "Cannibal";
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.FERMENTED_SPIDER_EYE, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("You start with a cat egg and a fish to tame your own cat");
		list.add("If you hit someone, there is a 1/3 chance they het Starvation II for 6 seconds");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Cat Spawn Egg");
		list.add("1 Raw Fish");
		return list;
	}
}
