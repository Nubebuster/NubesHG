package me.Mark.HG.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.Mark.HG.Gamer;

public class WinEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	private Player player;
	private String kit;

	public WinEvent(Gamer gamer) {
		this.player = gamer.getPlayer();
		this.kit = gamer.getKit().getKitName();
	}

	public Player getPlayer() {
		return player;
	}

	public String getKit() {
		return kit;
	}
}
