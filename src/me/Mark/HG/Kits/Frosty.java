package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

public class Frosty extends Kit {

	@Override
	public String getKitName() {
		return "Frosty";
	}

	@Override
	public ItemStack[] getItems() {
		return new ItemStack[] { new ItemStack(Material.SNOW_BALL, 6) };
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (hasAbillity(p) && p.getLocation().getBlock().getType() == Material.SNOW) {
			p.removePotionEffect(PotionEffectType.SPEED);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Snowball) || !(event.getEntity().getShooter() instanceof Player))
			return;
		if (!hasAbillity((Player) event.getEntity().getShooter()))
			return;
		BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(),
				event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
		Block hitBlock = null;
		while (iterator.hasNext()) {
			hitBlock = iterator.next();
			if (hitBlock.getTypeId() != 0)
				break;
		}
		if (hitBlock.getType() == Material.LONG_GRASS)
			hitBlock.setType(Material.ICE);
		else if (hitBlock.getRelative(BlockFace.UP).getType() == Material.AIR)
			hitBlock.getRelative(BlockFace.UP).setType(Material.SNOW);
		Location l = hitBlock.getLocation();
		int range = 40;
		int minX = l.getBlockX() - range / 2;
		int minY = l.getBlockY() - range / 2;
		int minZ = l.getBlockZ() - range / 2;
		for (int x = minX; x < minX + range; x++)
			for (int y = minY; y < minY + range; y++)
				for (int z = minZ; z < minZ + range; z++) {
					Block b = Bukkit.getWorld("world").getBlockAt(x, y, z);
					if (b.getType() == Material.WATER)
						b.setType(Material.ICE);
				}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() != Material.SNOW)
			return;
		if (!hasAbillity(event.getPlayer()))
			return;
		event.getBlock().setType(Material.AIR);
		event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5D, 0D, 0.5D),
				new ItemStack(Material.SNOW_BALL, 2));
	}

	@Override
	protected ItemStack getIcon() {
		return createItem(Material.BOW, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("You get Speed II when you walk over snow");
		list.add("When you throw a snowball, a layer of");
		list.add(" snow lands on it's spot");
		list.add("When you break snow you get 2 snowballs");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("10 Snowballs");
		return list;
	}
}
