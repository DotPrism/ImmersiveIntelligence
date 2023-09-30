package pl.pabilo8.immersiveintelligence.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;

import java.io.IOException;

public class GuiDevWarning extends GuiScreen
{
	public static boolean accepted = false;
	private final GuiScreen lastScreen;

	public GuiDevWarning(GuiScreen lastScreenIn) {
		this.lastScreen = lastScreenIn;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.yes")));
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.enabled) {
			if (button.id == 0)
			{
				GuiDevWarning.accepted = true;

				this.mc.displayGuiScreen(this.lastScreen);
			}
		}

	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, new StringBuilder().append(TextFormatting.RED).append(TextFormatting.BOLD).append("WARNING!!").append(TextFormatting.RESET).toString(), this.width / 2, (this.height/2) - 25, 16777215);
		String[] messages = new String[]{new StringBuilder().append("This ImmersiveIntelligence version is marked as ").append(ImmersiveIntelligence.VERSION_TYPE).toString(), "That means it may be very unstable.","If you want to play on this version please make a backup of your world.", "Do you want to proceed?"};
		int yPos = this.height/2 - 10;
		for (String s : messages)
		{
			this.drawCenteredString(this.fontRenderer, s, this.width / 2, yPos, 16777215);
			yPos += 10;
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}