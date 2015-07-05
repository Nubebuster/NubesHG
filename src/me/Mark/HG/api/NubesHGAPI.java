package me.Mark.HG.api;

import me.Mark.HG.Kits.Kit;
import me.Mark.HG.Utils.NameAlreadyInUseException;

public class NubesHGAPI {

	/**
	 * Register your custom kits
	 * 
	 * @param kit
	 *            The me.Mark.HG.Kit to register
	 * @throws NameAlreadyInUseException
	 *             if the kit name is already in use
	 */
	public static void registerKit(Kit kit) throws NameAlreadyInUseException {
		Kit.registerKit(kit);
	}
}
