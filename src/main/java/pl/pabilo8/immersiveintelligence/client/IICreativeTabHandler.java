package pl.pabilo8.immersiveintelligence.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pl.pabilo8.immersiveintelligence.client.gui.IIGuiContainerCreative;

public class IICreativeTabHandler
{
	@SubscribeEvent
	public void drawScreen(BackgroundDrawnEvent event)
	{
		GuiScreen screen = event.getGui();
		// Override creative gui
		if(screen instanceof GuiContainerCreative)
			IIClientUtils.displayScreen(new IIGuiContainerCreative(IIClientUtils.getPlayer()));
	}
}
