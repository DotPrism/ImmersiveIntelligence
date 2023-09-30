package pl.pabilo8.immersiveintelligence.common.block.multiblock.metal_multiblock1.multiblock;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.common.IIContent;
import pl.pabilo8.immersiveintelligence.common.block.multiblock.metal_multiblock1.tileentity.TileEntityProjectileForge;
import pl.pabilo8.immersiveintelligence.common.util.multiblock.MultiblockStuctureBase;
import pl.pabilo8.immersiveintelligence.common.block.multiblock.metal_multiblock1.BlockIIMetalMultiblock1.MetalMultiblocks1;

import javax.annotation.Nullable;

public class MultiblockProjectileForge extends MultiblockStuctureBase<TileEntityProjectileForge>
{
	public static MultiblockProjectileForge INSTANCE;

	public MultiblockProjectileForge()
	{
		super(new ResourceLocation(ImmersiveIntelligence.MODID, "multiblocks/projectile_forge"));
		offset = new Vec3i(2, 1, 0);
		INSTANCE = this;
	}

	@Override
	protected void addBlockEvent(World world, BlockPos pos)
	{
		world.addBlockEvent(pos, IIContent.blockMetalMultiblock1, 255, 0);
	}

	@Override
	@Nullable
	protected TileEntityProjectileForge placeTile(World world, BlockPos pos)
	{
		world.setBlockState(pos, IIContent.blockMetalMultiblock1.getStateFromMeta(MetalMultiblocks1.PROJECTILE_WORKSHOP.getMeta()));
		return (TileEntityProjectileForge)world.getTileEntity(pos);
	}

	@Override
	protected TileEntityProjectileForge getMBInstance()
	{
		return new TileEntityProjectileForge();
	}
}
