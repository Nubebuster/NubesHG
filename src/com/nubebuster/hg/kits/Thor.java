package com.nubebuster.hg.kits;

import java.util.ArrayList;
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

import com.nubebuster.hg.HG;

public class Thor extends Kit {

	@Override
	public String getKitName() {
		return "Thor";
	}

	@Override
	public ItemStack[] getItems() {
		ItemStack axe = createItem(Material.WOOD_AXE, "§lThor's Hammer", false);
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
	
	@Override
	protected ItemStack getIcon() {
		return createItem(Material.BOW, getKitName(), false);
	}

	@Override
	protected List<String> getDescription() {
		List<String> list = new ArrayList<String>();
		list.add("When you click with your hammer on the ground");
		list.add(" there strikes a lighting bolt and deals 2 hearts");
		list.add("If the highest block on the location is above y=80");
		list.add("Netherrack appears. Another strike will make the ");
		list.add(" rack explode! You can build up more rack");
		return list;
	}

	@Override
	protected List<String> getStartingItems() {
		List<String> list = getNewStringList();
		list.add("1 Thor's Hammer");
		return list;
	}
}
