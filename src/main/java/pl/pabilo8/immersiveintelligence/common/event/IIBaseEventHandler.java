package pl.pabilo8.immersiveintelligence.common.event;

import net.minecraftforge.common.MinecraftForge;

public abstract class IIBaseEventHandler
{
	public void registerEventHandler()
	{
		System.out.println("[ImmersiveIntelligence] Registering event handler: "+this.toString());
		MinecraftForge.EVENT_BUS.register(this);
	}
}
