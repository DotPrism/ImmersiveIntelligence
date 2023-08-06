package pl.pabilo8.immersiveintelligence.client.manual;

import blusunrize.immersiveengineering.api.ManualHelper;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.lib.manual.IManualPage;
import blusunrize.lib.manual.ManualInstance.ManualEntry;
import blusunrize.lib.manual.ManualPages;
import blusunrize.lib.manual.ManualUtils;
import blusunrize.lib.manual.gui.GuiButtonManual;
import blusunrize.lib.manual.gui.GuiManual;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.pabilo8.immersiveintelligence.client.manual.IIManualObject.ManualObjectInfo;
import pl.pabilo8.immersiveintelligence.client.manual.objects.*;
import pl.pabilo8.immersiveintelligence.common.util.easynbt.EasyNBT;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Pabilo8
 * @since 20.03.2022
 */
public class IIManualPage extends ManualPages
{
	//--- Statics and Internals ---//

	private static final int WIDTH = 120; //width of a single manual page
	public static final HashMap<String, BiFunction<ManualObjectInfo, EasyNBT, IIManualObject>> registeredObjects = new HashMap<>();
	final static Pattern patternObject = Pattern.compile("\\|(.+?)\\|");
	/**
	 * thanks to <a href="https://davidwells.io/snippets/regex-match-markdown-links">https://davidwells.io/snippets/regex-match-markdown-links</a> for improved pattern
	 */
	final static Pattern patternLink = Pattern.compile("\\[([^\\[]+)]\\((.*?)\\)");

	final static Pattern patternHighlight = Pattern.compile("\\[(.+?)]");
	final static Pattern patternBold = Pattern.compile("\\*\\*(.+?)\\*\\*");
	final static Pattern patternItalic = Pattern.compile("\\*(.+?)\\*");
	final static Pattern patternUnderline = Pattern.compile("__(.+?)__");
	final static Pattern patternStrikethrough = Pattern.compile("~~(.+?)~~");


	private final ArrayList<IIManualObject> manualObjects = new ArrayList<>();

	static
	{
		registeredObjects.put("image", IIManualImage::new);
		registeredObjects.put("text", IIManualText::new);
		registeredObjects.put("table", IIManualTable::new);
		registeredObjects.put("scenario", IIManualScenario::new);
		//registeredObjects.put("chart", IIManualChart::new);

		registeredObjects.put("multiblock", IIManualMultiblock::new);
		registeredObjects.put("crafting", IIManualCraftingRecipe::new);
		registeredObjects.put("blueprint", IIManualBlueprint::new);
		//registeredObjects.put("machine_recipe", IIManualMachineRecipe::new);

		registeredObjects.put("item_display", IIManualItemDisplay::new);
		registeredObjects.put("datatype", IIManualDataType::new);
		registeredObjects.put("data_packet", IIManualDataPacket::new);
		registeredObjects.put("upgrade_display", IIManualUpgradeDisplay::new);
	}

	private IIManualEntry entry;
	private HoverTooltipWrapper tooltipWrapper;

	public IIManualPage(String name)
	{
		super(null, name);
	}

	public void setParent(IIManualEntry entry)
	{
		this.entry = entry;
	}

	public void setManual()
	{
		manual = ManualHelper.getManual();
	}

	//--- Initialization on Opening ---//

	@SideOnly(Side.CLIENT)
	@Override
	public void initPage(GuiManual gui, int x, int y, List<GuiButton> pageButtons)
	{
		highlighted = ItemStack.EMPTY;
		String file = entry.fetchPage(text); //get text for this page

		if(file!=null&&!file.isEmpty())
		{
			//turn markdown into IE tags

			//object
			manualObjects.clear();
			file = addObjects(file, x, y, pageButtons, gui);
			pageButtons.add(this.tooltipWrapper = new HoverTooltipWrapper(gui));

			//link
			file = addLinks(file);

			//text types
			file = matchReplaceSimple(patternHighlight, file, TextFormatting.BOLD, TextFormatting.GOLD); //highlight
			file = matchReplaceSimple(patternBold, file, TextFormatting.BOLD); //bold
			file = matchReplaceSimple(patternItalic, file, TextFormatting.ITALIC); //italic
			file = matchReplaceSimple(patternUnderline, file, TextFormatting.UNDERLINE); //underline
			file = matchReplaceSimple(patternStrikethrough, file, TextFormatting.STRIKETHROUGH); //strikethrough

			//process the text IE way
			boolean uni = manual.fontRenderer.getUnicodeFlag();
			manual.fontRenderer.setUnicodeFlag(true);
			this.localizedText = manual.formatText(file);
			this.localizedText = addLinks(manual, gui, this.localizedText, x, y, WIDTH, pageButtons);

			if(this.localizedText==null)
				this.localizedText = "";

			manual.fontRenderer.setUnicodeFlag(uni);
		}
	}

	private String addLinks(String file)
	{
		return matchReplace(patternLink, file, (stringBuilder, matcher) ->
				{
					String link = matcher.group(2), sub = "";
					if(link.startsWith("#")) //link to page from this entry
					{
						sub = ";"+entry.getSubPageID(link.substring(1));
						link = this.entry.getName();
					}
					else if(link.contains("#")) //link to a subpage of another page
					{
						String[] split = link.split("#");
						if(split.length > 1)
						{
							List<ManualEntry> manualEntries = manual.manualContents.values().stream()
									.filter(me -> me.getName().equals(split[0]))
									.collect(Collectors.toList());
							if(manualEntries.size() > 0)
							{
								IManualPage[] pages = manualEntries.get(0).getPages();
								link = split[0];

								for(int i = 0; i < pages.length; i++)
								{
									IManualPage page = pages[i];
									if(page instanceof ManualPages)
									{
										String pageName = ReflectionHelper.getPrivateValue(ManualPages.class, ((ManualPages)page), "text");
										if(pageName.equals(split[1]))
										{
											sub = ";"+i;
											break;
										}
									}
								}
							}
						}
					}

					stringBuilder
							.append("<link;")
							.append(link)
							.append(";")
							.append(Arrays.stream(matcher.group(1).split(" "))
									.map(s -> TextFormatting.ITALIC.toString()+TextFormatting.UNDERLINE+s)
									.collect(Collectors.joining(" "))
							)
							.append(TextFormatting.RESET)
							.append(sub)
							.append(">");
				}
		);
	}

	//--- GUI Rendering and Element Handling ---//

	@SideOnly(Side.CLIENT)
	@Override
	public void renderPage(GuiManual gui, int x, int y, int mx, int my)
	{
		if(localizedText!=null&&!localizedText.isEmpty())
			ManualUtils.drawSplitString(manual.fontRenderer, localizedText, x, y, WIDTH, manual.getTextColour());

		GlStateManager.enableBlend();

		GlStateManager.pushMatrix();
		for(IIManualObject object : manualObjects)
		{
			this.tooltipWrapper.tooltip = object.getTooltip(gui.mc, mx, my);
			if(this.tooltipWrapper.tooltip!=null)
				break;
		}
		GlStateManager.popMatrix();

	}

	@Override
	public void buttonPressed(GuiManual gui, GuiButton button)
	{
		super.buttonPressed(gui, button);
	}

	@Override
	public void mouseDragged(int x, int y, int clickX, int clickY, int mx, int my, int lastX, int lastY, int button)
	{
		for(IIManualObject obj : manualObjects)
			obj.mouseDragged(x, y, clickX, clickY, mx, my, lastX, lastY, button);
	}

	@Override
	public boolean listForSearch(String searchTag)
	{
		return false;
	}

	//--- Private Methods (for parsing) ---//

	private String matchReplace(Pattern pattern, String text, BiConsumer<StringBuilder, Matcher> operation)
	{
		//iterate, skip fragments with no significance
		StringBuilder builder = new StringBuilder();
		Matcher matcher = pattern.matcher(text);
		int i = 0;
		while(matcher.find())
		{
			//replace from marker
			builder.append(text, i, matcher.start());
			operation.accept(builder, matcher);
			//move the marker
			i = matcher.end();
		}
		//build
		builder.append(text.substring(i));
		return builder.toString();
	}

	private String stripLine(String text)
	{
		text = matchReplaceSimple(patternLink, text);
		text = matchReplaceSimple(patternHighlight, text);
		text = matchReplaceSimple(patternBold, text);
		text = matchReplaceSimple(patternItalic, text);
		text = matchReplaceSimple(patternUnderline, text);
		return matchReplaceSimple(patternStrikethrough, text).replace("§r", "").trim();
	}

	private String matchReplaceSimple(Pattern pattern, String text, TextFormatting... formats)
	{
		return matchReplace(pattern, text, (stringBuilder, matcher) ->
				{
					for(TextFormatting format : formats)
						stringBuilder.append(format);
					stringBuilder
							.append(matcher.group(1))
							.append(TextFormatting.RESET);
				}
		);
	}

	private String addObjects(String file, int x, int y, List<GuiButton> pageButtons, GuiManual gui)
	{
		StringBuilder builder = new StringBuilder();
		String[] split = file.split("\n");
		final int[] lines = {0}; //wiurd intellij fix... but it works

		boolean flag = manual.fontRenderer.getUnicodeFlag();
		manual.fontRenderer.setUnicodeFlag(true);
		for(String line : split)
		{
			line = matchReplace(IIManualPage.patternObject, line, (stringBuilder, matcher) ->
					{
						String text = matcher.group(1);
						IIManualObject object = parseObject(gui, pageButtons, text, x, y+lines[0]);

						if(object!=null)
						{
							object.postInit(this);
							manualObjects.add(object);
							pageButtons.add(object);
							for(int i = 0; i < MathHelper.ceil(object.height/11f); i++)
							{
								lines[0] += 11;
								stringBuilder.append("\n");
							}
						}
					}
			);
			if(!line.isEmpty())
				lines[0] += manual.fontRenderer.getWordWrappedHeight(stripLine(line), WIDTH);
			builder.append(line).append("\n");
		}
		manual.fontRenderer.setUnicodeFlag(flag);

		return builder.toString();
	}

	@Nullable
	private IIManualObject parseObject(GuiManual gui, List<GuiButton> pageButtons, String text, int x, int y)
	{
		final Matcher matcherName = Pattern.compile("\\[(.+?)]").matcher(text);
		final Matcher matcherTag = Pattern.compile("\\{(.+?)}\\|").matcher(text+"|");

		String objectID = matcherName.find()?matcherName.group(1): "";
		String objectTag = matcherTag.find()?String.format("{%s}", matcherTag.group(1)): "";

		if(objectID.isEmpty())
			return null;

		BiFunction<ManualObjectInfo, EasyNBT, IIManualObject> fun = registeredObjects.getOrDefault(objectID, null);
		if(fun!=null)
			return fun.apply(getInfoForNext(gui, pageButtons, x, y), EasyNBT.parseEasyNBT(objectTag));

		return null;
	}

	private ManualObjectInfo getInfoForNext(GuiManual gui, List<GuiButton> pageButtons, int x, int y)
	{
		return new ManualObjectInfo(gui, x, y, pageButtons.size()+100);
	}

	@Nullable
	public EasyNBT getDataSource(String name)
	{
		return entry.getSource(name);
	}

	private static class HoverTooltipWrapper extends GuiButtonManual
	{
		List<String> tooltip;

		public HoverTooltipWrapper(GuiManual gui)
		{
			super(gui, 999, -1, -1, 1, 1, "");
		}

		@Override
		public void drawButton(Minecraft mc, int mx, int my, float partialTicks)
		{
			if(tooltip!=null&&!tooltip.isEmpty())
			{
				ClientUtils.drawHoveringText(tooltip, mx, my, gui.getManual().fontRenderer, gui.width, -1);
				RenderHelper.enableGUIStandardItemLighting();
			}
		}


	}
}