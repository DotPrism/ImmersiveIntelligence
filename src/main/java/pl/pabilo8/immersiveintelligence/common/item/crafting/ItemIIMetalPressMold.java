package pl.pabilo8.immersiveintelligence.common.item.crafting;

import pl.pabilo8.immersiveintelligence.common.item.crafting.ItemIIMetalPressMold.PressMolds;
import pl.pabilo8.immersiveintelligence.common.util.item.IIItemEnum;
import pl.pabilo8.immersiveintelligence.common.util.item.ItemIISubItemsBase;

/**
 * @author Pabilo8
 * @since 2019-05-11
 */
public class ItemIIMetalPressMold extends ItemIISubItemsBase<PressMolds>
{
	public ItemIIMetalPressMold()
	{
		super("press_mold", 1, PressMolds.values());
	}

	public enum PressMolds implements IIItemEnum
	{
		@IIItemProperties(category = IICategory.RESOURCE)
		HOWITZER,
		@IIItemProperties(category = IICategory.RESOURCE)
		LIGHT_HOWITZER,
		@IIItemProperties(category = IICategory.RESOURCE)
		MORTAR,
		@IIItemProperties(category = IICategory.RESOURCE)
		AUTOCANNON,
		@IIItemProperties(category = IICategory.RESOURCE)
		MACHINEGUN,
		@IIItemProperties(category = IICategory.RESOURCE)
		ASSAULT_RIFLE,
		@IIItemProperties(category = IICategory.RESOURCE)
		SUBMACHINEGUN,
		@IIItemProperties(category = IICategory.RESOURCE)
		NAVAL_MINE,
		@IIItemProperties(category = IICategory.RESOURCE)
		TRIPMINE,
		@IIItemProperties(category = IICategory.RESOURCE)
		TELLERMINE
	}
}
