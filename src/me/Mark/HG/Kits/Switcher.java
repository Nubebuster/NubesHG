package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class Switcher extends Kit {

	@Override
	public String getKitName() {
		return "Switcher";
	}

	@Override
	public ItemStack[] getItems() {
		ItemStack ball = createItem(Material.SNOW_BALL, "§lSwitcher ball", false);
		ball.setAmount(10);
		return new ItemStack[] { ball };
	}

	private HashMap<String, Long> cooldown = new HashMap<String, Long>();
	private List<Snowball> balls = new ArrayList<Snowball>();

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if ((event.getDamager() instanceof Snowball)) {
			if (!balls.contains((Snowball) event.getDamager()))
				return;
			Player thrower = (Player) ((Projectile) event.getDamager()).getShooter();
			Location loc1 = thrower.getPlayer().getLocation().clone();
			Location loc2 = event.getEntity().getLocation().clone();
			thrower.getPlayer().teleport(loc2);
			event.getEntity().teleport(loc1);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if ((event.getEntityType() == EntityType.SNOWBALL) && ((event.getEntity().getShooter() instanceof Player))) {
			Player p = (Player) event.getEntity().getShooter();
			Entity ball = event.getEntity();
			if (!hasAbillity(p) || !p.getInventory().getItemInHand().hasItemMeta())
				return;

			if ((cooldown.containsKey(p.getName())) && cooldown.get(p.getName()) > System.currentTimeMillis()) {
				event.setCancelled(true);
				ItemStack balli = createItem(Material.SNOW_BALL, "§lSwitcher ball", false);
				p.getInventory().addItem(new ItemStack[] { balli });
				p.updateInventory();
				p.sendMessage(ChatColor.RED + "Still on cooldown for "
						+ (int) Math.ceil((cooldown.get(p.getName()) - System.currentTimeMillis()) / 1000D)
						+ " seconds.");
				return;
			}

			balls.add((Snowball) ball);
			cooldown.put(p.getName(), System.currentTimeMillis() + 5000);
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		for (ItemStack is : event.getDrops())
			if ((is.getType() == Material.SNOW_BALL) && (is.getDurability() == 1)) {
				is.setType(Material.AIR);
			}
	}
	
	@Override
	protected ItemStack getIcon() {
		return createItem(Material.SNOW_BALL, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you hit someone with your snowball");
		list.add(" you switch places");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("10 Snowballs");
		return list;
	}
}
