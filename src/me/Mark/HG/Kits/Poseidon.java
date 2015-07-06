package me.Mark.HG.Kits;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poseidon extends Kit {

	@Override
	public String getKitName() {
		return "Poseidon";
	}

	@Override
	public ItemStack[] getItems() {
		return null;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (hasAbillity(p)) {
			Block b = p.getLocation().getBlock();
			if (b.isLiquid()) {
				p.removePotionEffect(PotionEffectType.SPEED);
				p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 1));
				event.getPlayer().setRemainingAir(200);
			}
		}
	}
}
