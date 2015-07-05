package me.Mark.HG.api;

import me.Mark.HG.Kits.Kit;
import me.Mark.HG.Utils.AlreadyInUseException;

public class HGAPI {

	/**
	 * Register your custom kits
	 * 
	 * @param kit
	 *            The me.Mark.HG.Kit to register
	 * @throws AlreadyInUseException
	 *             if the kit name is already in use
	 */
	public static void registerKit(Kit kit) throws AlreadyInUseException {
		Kit.registerKit(kit);
	}
}
