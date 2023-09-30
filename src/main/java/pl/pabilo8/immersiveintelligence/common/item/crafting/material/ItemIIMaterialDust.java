package pl.pabilo8.immersiveintelligence.common.item.crafting.material;

import pl.pabilo8.immersiveintelligence.common.item.crafting.material.ItemIIMaterialDust.MaterialsDust;
import pl.pabilo8.immersiveintelligence.common.util.item.IIItemEnum;
import pl.pabilo8.immersiveintelligence.common.util.IBatchOredictRegister;
import pl.pabilo8.immersiveintelligence.common.util.item.ItemIISubItemsBase;

/**
 * @author Pabilo8
 * @since 2019-05-11
 */
@IBatchOredictRegister(oreDict = "dust")
public class ItemIIMaterialDust extends ItemIISubItemsBase<MaterialsDust>
{
	public ItemIIMaterialDust()
	{
		super("material_dust", 64, MaterialsDust.values());
	}

	public enum MaterialsDust implements IIItemEnum
	{
		@IIItemProperties(category = IICategory.RESOURCE)
		ADVANCED_ELECTRONIC_ALLOY,
		@IIItemProperties(category = IICategory.RESOURCE)
		BRASS,
		@IIItemProperties(category = IICategory.RESOURCE)
		PLATINUM,
		@IIItemProperties(category = IICategory.RESOURCE)
		TUNGSTEN,
		@IIItemProperties(category = IICategory.RESOURCE)
		ZINC,
		@IIItemProperties(category = IICategory.RESOURCE)
		SILICON,
		@IIItemProperties(category = IICategory.RESOURCE)
		FLUORITE,
		@IIItemProperties(category = IICategory.RESOURCE)
		QUARTZ,
		@IIItemProperties(category = IICategory.RESOURCE)
		QUARTZ_DIRTY,
		@IIItemProperties(category = IICategory.RESOURCE)
		PHOSPHORUS,
		@IIItemProperties(category = IICategory.RESOURCE)
		DURALUMINIUM
	}
}
