package pl.pabilo8.immersiveintelligence.common.block.simple;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.BlockRenderLayer;
import pl.pabilo8.immersiveintelligence.common.block.simple.BlockIIClothDecoration.ClothDecorations;
import pl.pabilo8.immersiveintelligence.common.util.IILib;
import pl.pabilo8.immersiveintelligence.common.util.block.BlockIIBase;
import pl.pabilo8.immersiveintelligence.common.util.block.IIBlockInterfaces.IIBlockEnum;
import pl.pabilo8.immersiveintelligence.common.util.block.IIBlockInterfaces.IIBlockProperties;
import pl.pabilo8.immersiveintelligence.common.util.block.ItemBlockIIBase;
import pl.pabilo8.immersiveintelligence.common.util.item.IIItemEnum.IICategory;

/**
 * @author Pabilo8
 * @since 2019-05-17
 */
public class BlockIIClothDecoration extends BlockIIBase<ClothDecorations>
{
	public BlockIIClothDecoration()
	{
		super("cloth_decoration", PropertyEnum.create("type", ClothDecorations.class), Material.CLOTH, ItemBlockIIBase::new);
		setHardness(3.0F);
		setResistance(15.0F);

		setToolTypes(IILib.TOOL_HAMMER, IILib.TOOL_WIRECUTTER);

		setBlockLayer(BlockRenderLayer.CUTOUT_MIPPED);
	}

	public enum ClothDecorations implements IIBlockEnum
	{
		@IIBlockProperties(category = IICategory.RESOURCE)
		COIL_ROPE,
		@IIBlockProperties(category = IICategory.RESOURCE)
		COIL_CLOTH_MOTOR_BELT,
		@IIBlockProperties(category = IICategory.RESOURCE)
		COIL_RUBBER_MOTOR_BELT
	}
}