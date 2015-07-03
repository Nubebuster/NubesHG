package me.Mark.HG.api;

import me.Mark.HG.Kits.Kit;

public class HGAPI {
	
	/**
	 * Register your custom kits
	 * @param kit The me.Mark.HG.Kit to register
	 */
	public static void registerKit(Kit kit) {
		Kit.registerKit(kit);
	}
}
