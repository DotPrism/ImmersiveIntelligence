package pl.pabilo8.immersiveintelligence.common.util;

import net.minecraftforge.fml.common.Loader;

/**
 * @author GabrielV (gabriel@iiteam.net)
 * @created 23/10/2023 - 5:31 PM
 * A utility class for interacting with mods
 */
public class IIModManager
{
	// Disable the constructor
	private IIModManager() {}

	public static boolean isModLoaded(String modid)
	{
		return Loader.isModLoaded(modid);
	}
}
