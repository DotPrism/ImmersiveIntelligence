package pl.pabilo8.immersiveintelligence.common.block.multiblocks.wooden;

import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.api.MultiblockHandler.IMultiblock;
import blusunrize.immersiveengineering.api.crafting.IngredientStack;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.wooden.BlockTypes_WoodenDecoration;
import blusunrize.immersiveengineering.common.util.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.common.IIContent;
import pl.pabilo8.immersiveintelligence.common.block.types.IIBlockTypes_MetalDecoration;
import pl.pabilo8.immersiveintelligence.common.block.types.IIBlockTypes_WoodenMultiblock;

/**
 * @author Pabilo8
 * @since 13-04-2020
 */
public class MultiblockSawmill implements IMultiblock
{
	static final IngredientStack[] materials = new IngredientStack[]{
			new IngredientStack(new ItemStack(IIContent.blockMetalDecoration, 6, IIBlockTypes_MetalDecoration.MECHANICAL_ENGINEERING.getMeta())),
			new IngredientStack(new ItemStack(IEContent.blockWoodenDecoration, 3, BlockTypes_WoodenDecoration.SCAFFOLDING.getMeta()))
	};
	public static MultiblockSawmill instance = new MultiblockSawmill();
	static ItemStack[][][] structure = new ItemStack[2][2][4];

	static
	{
		for(int h = 0; h < 2; h++)
		{
			for(int l = 0; l < 2; l++)
			{
				for(int w = 0; w < 4; w++)
				{
					if(h==0)
					{
						if(w==0)
						{
							if(l==1)
								structure[h][l][w] = new ItemStack(IEContent.blockWoodenDecoration, 1, BlockTypes_WoodenDecoration.SCAFFOLDING.getMeta());
						}
						else if(w < 3&&l==0)
							structure[h][l][w] = new ItemStack(IEContent.blockWoodenDecoration, 1, BlockTypes_WoodenDecoration.SCAFFOLDING.getMeta());
						else
							structure[h][l][w] = new ItemStack(IIContent.blockMetalDecoration, 1, IIBlockTypes_MetalDecoration.MECHANICAL_ENGINEERING.getMeta());
					}
					else
					{
						if(w==3)
							structure[h][l][w] = new ItemStack(IIContent.blockMetalDecoration, 1, IIBlockTypes_MetalDecoration.MECHANICAL_ENGINEERING.getMeta());
					}
				}
			}
		}
	}

	TileEntitySawmill te;

	@Override
	public String getUniqueName()
	{
		return "II:Sawmill";
	}

	@Override
	public boolean isBlockTrigger(IBlockState state)
	{
		return state.getBlock()==IEContent.blockWoodenDecoration&&
				(state.getBlock().getMetaFromState(state)==BlockTypes_WoodenDecoration.SCAFFOLDING.getMeta());
	}

	@Override
	public boolean createStructure(World world, BlockPos pos, EnumFacing side, EntityPlayer player)
	{

		side = side.getOpposite();
		if(side==EnumFacing.UP||side==EnumFacing.DOWN)
		{
			side = EnumFacing.fromAngle(player.rotationYaw);
		}

		boolean mirrored = false;
		boolean b = structureCheck(world, pos, side, false);
		if(!b)
		{
			mirrored = true;
			b = structureCheck(world, pos, side, true);
		}
		if(!b)
			return false;

		ItemStack hammer = player.getHeldItemMainhand().getItem().getToolClasses(player.getHeldItemMainhand()).contains(Lib.TOOL_HAMMER)?player.getHeldItemMainhand(): player.getHeldItemOffhand();
		if(MultiblockHandler.fireMultiblockFormationEventPost(player, this, pos, hammer).isCanceled())
			return false;

		for(int h = 0; h < 2; h++)
			for(int l = 0; l < 2; l++)
				for(int w = -2; w < 2; w++)
				{
					if(h==1&&w < 1)
						continue;
					if(h==0&&w==-2&&l==0)
						continue;

					int ww = mirrored?-w: w;
					BlockPos pos2 = pos.offset(side, l).offset(side.rotateY(), ww).add(0, h, 0);

					world.setBlockState(pos2, IIContent.blockWoodenMultiblock.getStateFromMeta(IIBlockTypes_WoodenMultiblock.SAWMILL.getMeta()));
					TileEntity curr = world.getTileEntity(pos2);
					if(curr instanceof TileEntitySawmill)
					{
						TileEntitySawmill tile = (TileEntitySawmill)curr;
						tile.facing = side;
						tile.mirrored = mirrored;
						tile.formed = true;
						tile.pos = (h)*8+(l)*4+(w+2);
						tile.offset = new int[]{(side==EnumFacing.WEST?-l: side==EnumFacing.EAST?l: side==EnumFacing.NORTH?ww: -ww), h, (side==EnumFacing.NORTH?-l: side==EnumFacing.SOUTH?l: side==EnumFacing.EAST?ww: -ww)};
						tile.markDirty();
						world.addBlockEvent(pos2, IIContent.blockWoodenMultiblock, 255, 0);
					}
				}
		return true;
	}

	boolean structureCheck(World world, BlockPos startPos, EnumFacing dir, boolean mirror)
	{
		for(int h = 0; h < 2; h++)
			for(int l = 0; l < 2; l++)
				for(int w = -2; w < 2; w++)
				{
					if(h==0&&w==-2&&l==0)
						continue;

					if(h==1&&w < 1)
					{
						continue;
					}

					int ww = mirror?-w: w;
					BlockPos pos = startPos.offset(dir, l).offset(dir.rotateY(), ww).add(0, h, 0);

					if(h==0)
					{
						if(w==-2)
						{
							if(!Utils.isBlockAt(world, pos, IEContent.blockWoodenDecoration, BlockTypes_WoodenDecoration.SCAFFOLDING.getMeta()))
								return false;
						}
						else if(w < 1)
						{
							if(l==0)
							{
								if(!Utils.isBlockAt(world, pos, IEContent.blockWoodenDecoration, BlockTypes_WoodenDecoration.SCAFFOLDING.getMeta()))
									return false;
							}
							else if(!Utils.isBlockAt(world, pos, IIContent.blockMetalDecoration, IIBlockTypes_MetalDecoration.MECHANICAL_ENGINEERING.getMeta()))
								return false;
						}

					}
					else
					{
						if(!Utils.isBlockAt(world, pos, IIContent.blockMetalDecoration, IIBlockTypes_MetalDecoration.MECHANICAL_ENGINEERING.getMeta()))
							return false;
					}
				}
		return true;
	}

	@Override
	public ItemStack[][][] getStructureManual()
	{
		return structure;
	}

	@Override
	public IngredientStack[] getTotalMaterials()
	{
		return materials;
	}

	@Override
	public boolean overwriteBlockRender(ItemStack stack, int iterator)
	{
		return false;
	}

	@Override
	public float getManualScale()
	{
		return 12;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderFormedStructure()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderFormedStructure()
	{
		if(te==null)
		{
			te = new TileEntitySawmill();
			te.facing = EnumFacing.NORTH;
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(2, 1, 2);
		ImmersiveIntelligence.proxy.renderTile(te);
		GlStateManager.popMatrix();
	}
}
