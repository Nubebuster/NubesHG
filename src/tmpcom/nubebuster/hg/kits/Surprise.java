package tmpcom.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Surprise extends Kit {

	@Override
	public String getKitName() {
		return "Surprise";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.FIREWORK, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("You get a random kit at the start of the game");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Random kit");
		return list;
	}
}
