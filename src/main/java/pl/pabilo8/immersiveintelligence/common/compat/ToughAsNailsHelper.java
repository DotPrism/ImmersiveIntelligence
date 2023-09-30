package pl.pabilo8.immersiveintelligence.common.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import pl.pabilo8.immersiveintelligence.common.IILogger;
import pl.pabilo8.immersiveintelligence.common.item.armor.ItemIILightEngineerChestplate;
import toughasnails.api.TANCapabilities;
import toughasnails.api.stat.capability.ITemperature;
import toughasnails.api.temperature.*;
import toughasnails.temperature.modifier.TemperatureModifier;

import java.lang.reflect.Field;

public class ToughAsNailsHelper extends IICompatModule
{
	@Override
	public void preInit()
	{

	}

	@Override
	public void registerRecipes()
	{

	}

	@Override
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void postInit()
	{
		try
		{
			TemperatureHelper.registerTemperatureModifier((ITemperatureModifier)new IIArmorModifier());
		} catch (Exception e)
		{
			IILogger.error(e);
		}
	}

	public class IIArmorModifier extends TemperatureModifier
	{
		public IIArmorModifier()
		{
			super("IIArmor");
		}

		@Override
		public boolean isPlayerSpecific()
		{
			return true;
		}

		@Override
		public Temperature applyPlayerModifiers(EntityPlayer player, Temperature initialTemperature, IModifierMonitor monitor)
		{
			Iterable<ItemStack> armor = player.getArmorInventoryList();
			for (ItemStack stack : armor)
			{
				if(stack.getItem() instanceof ItemIILightEngineerChestplate)
				{
					ItemIILightEngineerChestplate item = (ItemIILightEngineerChestplate)stack.getItem();
					if (item.protectsFromHeat(stack))
					{
						ITemperature temperature = player.getCapability(TANCapabilities.TEMPERATURE, null);
						// Temperature value of 18 is the max value we will allow since it is close to overheating start which is 20.
						if (temperature.getTemperature().getRawValue() > 18) temperature.setTemperature(new Temperature(18)); // Reset to our maximal temperature
					}
				}
			}

			return initialTemperature; // We do not want to alter the target temperature since we alter player's temperature directly few lines up
		}
	}
}
