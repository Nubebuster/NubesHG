package tmpcom.nubebuster.hg.api;

import tmpcom.nubebuster.hg.kits.Kit;
import tmpcom.nubebuster.hg.utils.NameAlreadyInUseException;

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
