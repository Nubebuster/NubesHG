package me.Mark.HG.Kits;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Mark.HG.HG;

public class Thor extends Kit {

	@Override
	public String getKitName() {
		return "Thor";
	}

	@Override
	public ItemStack[] getItems() {
		ItemStack axe = new ItemStack(Material.WOOD_AXE);
		return new ItemStack[] { axe };
	}

	private HashMap<String, Long> cooldown = new HashMap<String, Long>();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = event.getPlayer();
			if (hasAbillity(p) && (p.getItemInHand().getType() == Material.WOOD_AXE)) {
				if (HG.HG.gameTime < 120) {
					p.sendMessage(ChatColor.RED + "You can't do that during invincibility.");
					return;
				}
				if (cooldown.containsKey(p.getName()))
					if (cooldown.get(p.getName()) > System.currentTimeMillis()) {
						p.sendMessage(ChatColor.RED + "You may not do that at this time,");
						return;
					}
				cooldown.put(p.getName(), Long.valueOf(System.currentTimeMillis() + 4000L));
				p.getWorld().strikeLightningEffect(
						p.getWorld().getHighestBlockAt(event.getClickedBlock().getLocation()).getLocation());
				for (Entity entity : Bukkit.getWorld("world").getEntities()) {
					if (entity.getLocation().distance(event.getClickedBlock().getLocation()) <= 3.0D) {
						if (((entity instanceof LivingEntity)) && (entity != p)) {
							LivingEntity e = (LivingEntity) entity;
							e.damage(4, p);
						}
					}
				}
				Block b = event.getClickedBlock();
				Location loc = b.getLocation();
				Block hb = p.getWorld().getHighestBlockAt(loc);

				if ((hb.getType() == Material.FIRE)
						&& (hb.getRelative(BlockFace.DOWN).getType() == Material.NETHERRACK)) {
					CreateExplosion(hb.getRelative(BlockFace.DOWN).getLocation());
				} else if (hb.getRelative(BlockFace.DOWN).getType() == Material.NETHERRACK) {
					CreateExplosion(hb.getRelative(BlockFace.DOWN).getLocation());
				} else if (hb.getLocation().getY() >= 80.0D) {
					hb.setType(Material.NETHERRACK);
					hb.getRelative(BlockFace.UP).setType(Material.FIRE);
				}
			}
		}
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		List<Block> explodeblocks = event.blockList();
		for (Block b : explodeblocks)
			if (b.getType() == Material.NETHERRACK)
				CreateSmallExplosion(b.getLocation());
	}

	public void CreateExplosion(Location loc) {
		TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawn(loc, TNTPrimed.class);
		tnt.setFuseTicks(0);
		tnt.setYield(2.5F);
	}

	public void CreateSmallExplosion(Location loc) {
		TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawn(loc, TNTPrimed.class);
		tnt.setFuseTicks(0);
		tnt.setYield(2.0F);
	}
}
