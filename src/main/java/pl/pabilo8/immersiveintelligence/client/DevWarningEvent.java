package pl.pabilo8.immersiveintelligence.client;

import blusunrize.immersiveengineering.client.ImmersiveModelRegistry;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence.VersionType;
import pl.pabilo8.immersiveintelligence.client.gui.GuiDevWarning;

public class DevWarningEvent
{
	@SubscribeEvent
	public void onGuiOpenEvent(GuiOpenEvent event)
	{
		if (event.getGui() instanceof GuiMainMenu&& !GuiDevWarning.accepted&&ImmersiveIntelligence.VERSION_TYPE==VersionType.DEV||ImmersiveIntelligence.VERSION_TYPE==VersionType.BETA)
			event.setGui(new GuiDevWarning(event.getGui()));
	}
}
