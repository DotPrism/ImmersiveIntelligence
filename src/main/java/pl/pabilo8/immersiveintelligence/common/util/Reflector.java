package pl.pabilo8.immersiveintelligence.common.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import pl.pabilo8.immersiveintelligence.common.IILogger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author GabrielV (gabriel@iiteam.net)
 * @created 25/10/2023 - 8:41 PM
 * <p>
 * Class used for reflection aka hacky stuff to get some private stuff...
 * </p>
 */
public class Reflector
{
	private Reflector()
	{
	}

	private static ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners = null;

	public static Field getField(Class clazz, String name, boolean priv)
	{
		try {
			return priv ? clazz.getDeclaredField(name) : clazz.getField(name);
		} catch(Exception e) {
			IILogger.error("Error while using reflector! "+e.getMessage());
			return null;
		}
	}

	public static void getForgeEventListeners()
	{
		Field listenersField = getField(MinecraftForge.EVENT_BUS.getClass(), "listeners", true);
		listenersField.setAccessible(true);
		if (ConcurrentHashMap.class.isAssignableFrom(listenersField.getType()))
		{
			try {
				listeners = (ConcurrentHashMap<Object, ArrayList<IEventListener>>)listenersField.get(MinecraftForge.EVENT_BUS);

			} catch(Exception e) {
				IILogger.error("[REFLECTOR ERROR] "+e.getMessage());
			}
		}
	}

	/**
	 * Hijack the event handler with your own.<br>
	 * IMPORTANT!!! Your custom event handler <b>has</b> to extend the original event handler's class!!!<br>
	 * It is to prevent any potential problems with static variables, etc.
	 * @param originalEventHandler Class of the original event handler
	 * @param overrideEventHandler Class to replace the event handler with
	 */
	public static void overrideEventHandler(Class originalEventHandler, Object overrideEventHandler)
	{
		if (listeners == null) {
			IILogger.error("[REFLECTOR ERROR] Listener is null");
			return;
		}

		for (Map.Entry<Object, ArrayList<IEventListener>> o : listeners.entrySet())
		{
			Object cl = o.getKey();
			if (!cl.getClass().getName().equals(originalEventHandler.getName())) continue;
			MinecraftForge.EVENT_BUS.unregister(cl);
			MinecraftForge.EVENT_BUS.register(overrideEventHandler);
		}
	}
}
