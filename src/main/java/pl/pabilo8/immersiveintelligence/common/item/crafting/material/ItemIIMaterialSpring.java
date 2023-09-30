package pl.pabilo8.immersiveintelligence.common.item.crafting.material;

import pl.pabilo8.immersiveintelligence.common.item.crafting.material.ItemIIMaterialSpring.MaterialsSpring;
import pl.pabilo8.immersiveintelligence.common.util.IBatchOredictRegister;
import pl.pabilo8.immersiveintelligence.common.util.item.IIItemEnum;
import pl.pabilo8.immersiveintelligence.common.util.item.ItemIISubItemsBase;

/**
 * @author Pabilo8
 * @since 2019-05-11
 */
@IBatchOredictRegister(oreDict = "spring")
public class ItemIIMaterialSpring extends ItemIISubItemsBase<MaterialsSpring>
{
	public ItemIIMaterialSpring()
	{
		super("material_spring", 64, MaterialsSpring.values());
	}

	public enum MaterialsSpring implements IIItemEnum
	{
		@IIItemProperties(category = IICategory.RESOURCE)
		BRASS,
		@IIItemProperties(category = IICategory.RESOURCE)
		IRON,
		@IIItemProperties(category = IICategory.RESOURCE)
		STEEL
	}
}
