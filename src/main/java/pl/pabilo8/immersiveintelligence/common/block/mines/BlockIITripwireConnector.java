package pl.pabilo8.immersiveintelligence.common.block.mines;

import blusunrize.immersiveengineering.api.IEProperties;
import blusunrize.immersiveengineering.api.energy.wires.TileEntityImmersiveConnectable;
import blusunrize.immersiveengineering.client.models.IOBJModelCallback;
import blusunrize.immersiveengineering.common.blocks.BlockIEBase;
import blusunrize.immersiveengineering.common.blocks.ItemBlockIEBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import pl.pabilo8.immersiveintelligence.common.block.mines.tileentity.TileEntityTripwireConnector;
import pl.pabilo8.immersiveintelligence.common.util.block.BlockIITileProvider;
import pl.pabilo8.immersiveintelligence.common.block.mines.BlockIITripwireConnector.IIBlockTypes_Dummy;
import pl.pabilo8.immersiveintelligence.common.util.block.IIBlockInterfaces.EnumTileProvider;
import pl.pabilo8.immersiveintelligence.common.util.block.IIBlockInterfaces.IITileProviderEnum;
import pl.pabilo8.immersiveintelligence.common.util.block.ItemBlockIIBase;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author Pabilo8
 * @since 05.02.2021
 */
public class BlockIITripwireConnector extends BlockIITileProvider<IIBlockTypes_Dummy>
{
	public enum IIBlockTypes_Dummy implements IITileProviderEnum
	{
		@EnumTileProvider(tile = TileEntityTripwireConnector.class)
		MAIN
	}

	public BlockIITripwireConnector()
	{
		super("tripwire_connector", Material.WOOD, PropertyEnum.create("dummy", IIBlockTypes_Dummy.class), ItemBlockIIBase::new,
				IOBJModelCallback.PROPERTY, IEProperties.BOOLEANS[0], IEProperties.BOOLEANS[1], IEProperties.CONNECTIONS);
		setLightOpacity(0);
	}
}