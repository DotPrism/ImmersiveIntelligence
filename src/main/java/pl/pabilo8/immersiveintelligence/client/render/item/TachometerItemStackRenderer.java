package pl.pabilo8.immersiveintelligence.client.render.item;

import blusunrize.immersiveengineering.client.ClientUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.client.model.item.ModelTachometer;
import pl.pabilo8.immersiveintelligence.client.tmt.ModelRendererTurbo;

/**
 * @author Pabilo8
 * @since 13-10-2019
 */
public class TachometerItemStackRenderer extends TileEntityItemStackRenderer
{
	public static TachometerItemStackRenderer instance = new TachometerItemStackRenderer();
	@SideOnly(Side.CLIENT)
	private static ModelTachometer model = new ModelTachometer();

	@Override
	public void renderByItem(ItemStack itemStackIn, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180, 0, 1, 0);
		GlStateManager.translate(-1f, 0f, 0f);

		String texture = ImmersiveIntelligence.MODID+":textures/items/tools/tachometer.png";
		ClientUtils.bindTexture(texture);
		for(ModelRendererTurbo mod : model.baseModel)
			mod.render(0.0625f);
		for(ModelRendererTurbo mod : model.gaugeModel)
			mod.render(0.0625f);

		GlStateManager.popMatrix();
	}
}
