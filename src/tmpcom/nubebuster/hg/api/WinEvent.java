package tmpcom.nubebuster.hg.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import tmpcom.nubebuster.hg.Gamer;

public class WinEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

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
