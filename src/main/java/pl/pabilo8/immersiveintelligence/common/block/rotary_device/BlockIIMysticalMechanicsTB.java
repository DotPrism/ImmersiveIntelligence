package pl.pabilo8.immersiveintelligence.common.block.rotary_device;

import blusunrize.immersiveengineering.api.IEProperties;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.BlockRenderLayer;
import pl.pabilo8.immersiveintelligence.common.block.rotary_device.BlockIIMysticalMechanicsTB.IIMysticalMechanicalTransmissionBox;
import pl.pabilo8.immersiveintelligence.common.block.rotary_device.tileentity.TileEntityMysticalMechanicsTB;
import pl.pabilo8.immersiveintelligence.common.util.IILib;
import pl.pabilo8.immersiveintelligence.common.util.block.BlockIITileProvider;
import pl.pabilo8.immersiveintelligence.common.util.block.IIBlockInterfaces.EnumTileProvider;
import pl.pabilo8.immersiveintelligence.common.util.block.IIBlockInterfaces.IIBlockProperties;
import pl.pabilo8.immersiveintelligence.common.util.block.IIBlockInterfaces.IITileProviderEnum;
import pl.pabilo8.immersiveintelligence.common.util.block.ItemBlockIIBase;
import pl.pabilo8.immersiveintelligence.common.util.item.IIItemEnum.IICategory;

public class BlockIIMysticalMechanicsTB extends BlockIITileProvider<IIMysticalMechanicalTransmissionBox>
{
	public enum IIMysticalMechanicalTransmissionBox implements IITileProviderEnum
	{
		@EnumTileProvider(tile = TileEntityMysticalMechanicsTB.class)
		@IIBlockProperties(category = IICategory.ELECTRONICS)
		MAIN
	}

	public BlockIIMysticalMechanicsTB()
	{
		super("mystical_transmission_box", Material.WOOD, PropertyEnum.create("type", IIMysticalMechanicalTransmissionBox.class), ItemBlockIIBase::new, IEProperties.FACING_ALL);
		setHardness(3.0F);
		setResistance(15.0F);
		setFullCube(true);
		setBlockLayer(BlockRenderLayer.CUTOUT_MIPPED);
		setToolTypes(IILib.TOOL_HAMMER);
	}
}
