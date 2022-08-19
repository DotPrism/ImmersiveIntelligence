package pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.wooden;

import blusunrize.immersiveengineering.api.IEProperties;
import blusunrize.immersiveengineering.client.models.IOBJModelCallback;
import blusunrize.immersiveengineering.common.blocks.ItemBlockIEBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import pl.pabilo8.immersiveintelligence.common.blocks.BlockIIMultiblock;
import pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.TileEntityMultiblockConnectable;
import pl.pabilo8.immersiveintelligence.common.blocks.types.IIBlockTypes_WoodenMultiblock;

import java.util.Arrays;

/**
 * @author Pabilo8
 * @since 2019-06-05
 */
public class BlockIIWoodenMultiblock extends BlockIIMultiblock<IIBlockTypes_WoodenMultiblock>
{
	public BlockIIWoodenMultiblock()
	{
		super("wooden_multiblock", Material.WOOD, PropertyEnum.create("type", IIBlockTypes_WoodenMultiblock.class), ItemBlockIEBase.class, IEProperties.FACING_HORIZONTAL,
				IEProperties.BOOLEANS[0], IEProperties.BOOLEANS[1], IEProperties.MULTIBLOCKSLAVE, IOBJModelCallback.PROPERTY);
		setHardness(3.0F);
		setResistance(15.0F);
		lightOpacity = 0;
		this.setAllNotNormalBlock();
		this.setMetaBlockLayer(IIBlockTypes_WoodenMultiblock.SAWMILL.getMeta(), BlockRenderLayer.TRANSLUCENT);

		tesrMap.put(IIBlockTypes_WoodenMultiblock.SAWMILL.getMeta(), IIBlockTypes_WoodenMultiblock.SAWMILL.getName());

	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return getMetaFromState(state)==IIBlockTypes_WoodenMultiblock.SAWMILL.getMeta()?EnumBlockRenderType.ENTITYBLOCK_ANIMATED: super.getRenderType(state);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		BlockStateContainer base = super.createBlockState();
		IUnlistedProperty<?>[] unlisted = (base instanceof ExtendedBlockState)?((ExtendedBlockState)base).getUnlistedProperties().toArray(new IUnlistedProperty[0]): new IUnlistedProperty[0];
		unlisted = Arrays.copyOf(unlisted, unlisted.length+1);
		unlisted[unlisted.length-1] = IEProperties.CONNECTIONS;
		return new ExtendedBlockState(this, base.getProperties().toArray(new IProperty[0]), unlisted);
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		state = super.getExtendedState(state, world, pos);
		if(state instanceof IExtendedBlockState)
		{
			IExtendedBlockState ext = (IExtendedBlockState)state;
			TileEntity te = world.getTileEntity(pos);
			if(!(te instanceof TileEntityMultiblockConnectable))
				return state;
			state = ext.withProperty(IEProperties.CONNECTIONS, ((TileEntityMultiblockConnectable<?,?>)te).genConnBlockstate());
		}
		return state;
	}

	@Override
	public boolean allowHammerHarvest(IBlockState state)
	{
		return true;
	}

	@Override
	public boolean canIEBlockBePlaced(World world, BlockPos pos, IBlockState newState, EnumFacing side, float hitX, float hitY, float hitZ, EntityPlayer player, ItemStack stack)
	{
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean useCustomStateMapper()
	{
		return true;
	}

	@Override
	public String getCustomStateMapping(int meta, boolean itemBlock)
	{
		if(IIBlockTypes_WoodenMultiblock.values()[meta].needsCustomState())
			return IIBlockTypes_WoodenMultiblock.values()[meta].getCustomState();
		return null;
	}

	@Override
	public TileEntity createBasicTE(World world, IIBlockTypes_WoodenMultiblock type)
	{
		switch(type)
		{
			case SKYCRATE_POST:
			{
				return new TileEntitySkyCratePost();
			}
			case SKYCRATE_STATION:
			{
				return new TileEntitySkyCrateStation();
			}
			case SKYCART_STATION:
			{
				return new TileEntitySkyCartStation();
			}
			case SAWMILL:
			{
				return new TileEntitySawmill();
			}
		}
		return null;
	}
}
