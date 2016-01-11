package tmpcom.nubebuster.hg.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Turtle extends Kit {

	@Override
	public String getKitName() {
		return "Turtle";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player p = (Player) event.getEntity();
			if (!hasAbillity(p) || p.getLocation().getBlock().getType() == Material.STONE_PLATE
					|| p.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() == Material.STONE_PLATE
					|| p.getLocation().getBlock().getRelative(BlockFace.EAST).getType() == Material.STONE_PLATE
					|| p.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType() == Material.STONE_PLATE
					|| p.getLocation().getBlock().getRelative(BlockFace.WEST).getType() == Material.STONE_PLATE)
				return;
			if ((p.isSneaking()) && (event.getDamage() > 1))
				if (p.isBlocking())
					event.setDamage(1);
				else
					event.setDamage(2);
		}
	}

	@EventHandler
	public void onDamage1(EntityDamageByEntityEvent event) {
		if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
			Player p = (Player) event.getDamager();
			if (p.isSneaking())
				if (hasAbillity(p)) {
					p.sendMessage(ChatColor.RED + "Turtles can't attack whilst crouched!");
					event.setCancelled(true);
				}
		}
	}
	
	@Override
	protected ItemStack getIcon() {
		return createItem(Material.CAKE, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you sneak you can not take more");
		list.add(" damage than .5 hearts per hit");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		return list;
	}
}
