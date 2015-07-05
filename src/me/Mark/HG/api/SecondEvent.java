package me.Mark.HG.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SecondEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
