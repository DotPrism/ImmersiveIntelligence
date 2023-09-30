package pl.pabilo8.immersiveintelligence.common.item.crafting.material;

import pl.pabilo8.immersiveintelligence.common.item.crafting.material.ItemIIMaterialPlate.MaterialsPlate;
import pl.pabilo8.immersiveintelligence.common.util.IBatchOredictRegister;
import pl.pabilo8.immersiveintelligence.common.util.item.IIItemEnum;
import pl.pabilo8.immersiveintelligence.common.util.item.ItemIISubItemsBase;

/**
 * @author Pabilo8
 * @since 2019-05-11
 */
@IBatchOredictRegister(oreDict = "plate")
public class ItemIIMaterialPlate extends ItemIISubItemsBase<MaterialsPlate>
{
	public ItemIIMaterialPlate()
	{
		super("material_plate", 64, MaterialsPlate.values());
	}

	public enum MaterialsPlate implements IIItemEnum
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
		RUBBER_RAW,
		@IIItemProperties(category = IICategory.RESOURCE)
		DURALUMINIUM
	}
}
