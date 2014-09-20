package me.Mark.HG.Kits;

import java.util.ArrayList;
import java.util.List;

import me.Mark.HG.HG;

import org.bukkit.event.Listener;

public abstract class Kit implements Listener {

	public static List<Kit> kits = new ArrayList<Kit>();

	public static void init() {
		for (String s : HG.config.getStringList("kits")) {
			try {
				Class<?> kit = Class.forName("me.Mark.HG.Kits." + s);
				kits.add(kit);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
