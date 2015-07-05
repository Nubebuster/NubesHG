package me.Mark.HG.api;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FeastEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	private Location loc;
	
	public FeastEvent(Location loc) {
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
}
