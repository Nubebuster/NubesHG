package tmpcom.nubebuster.hg.api;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	List<Player> participants;

	public GameStartEvent(List<Player> particicpants) {
		this.participants = particicpants;
	}

	public List<Player> getParticipants() {
		return participants;
	}
}
