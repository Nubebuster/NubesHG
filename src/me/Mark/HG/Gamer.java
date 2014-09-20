package me.Mark.HG;

import me.Mark.HG.Kits.Kit;

import org.bukkit.entity.Player;

public class Gamer {

	private String name;
	private Kit kit;

	public Gamer(Player player, Kit kit) {
		this.name = player.getName();
		this.kit = kit;
	}

}
