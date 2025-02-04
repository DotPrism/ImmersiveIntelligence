package pl.pabilo8.immersiveintelligence.client.render.metal_device;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.client.render.IITileRenderer.RegisteredTileRenderer;
import pl.pabilo8.immersiveintelligence.common.block.metal_device.tileentity.effect_crate.TileEntityMedicalCrate;

/**
 * @author Pabilo8
 * @since 2019-05-26
 */
@SideOnly(Side.CLIENT)
@RegisteredTileRenderer(name = "medical_crate", clazz = TileEntityMedicalCrate.class)
public class MedicalCrateRenderer extends EffectCrateRenderer<TileEntityMedicalCrate>
{
	private final ResourceLocation openAnimation = new ResourceLocation(ImmersiveIntelligence.MODID, "repair_crate_open");

	private final ResourceLocation inserterUpgrade = new ResourceLocation(ImmersiveIntelligence.MODID, "models/block/metal_device/effect_crate/upgrade/upgrade_syringe.obj.ie");
	private final ResourceLocation inserterUpgradeAnimation = new ResourceLocation(ImmersiveIntelligence.MODID, "inserter_upgrade_construction");

	//--- EffectCrateRenderer ---//

	@Override
	public ResourceLocation getOpenAnimationPath()
	{
		return openAnimation;
	}

	@Override
	public ResourceLocation getInserterUpgradeAnimationPath()
	{
		return inserterUpgradeAnimation;
	}

	@Override
	public ResourceLocation getInserterUpgradePath()
	{
		return inserterUpgrade;
	}
}
