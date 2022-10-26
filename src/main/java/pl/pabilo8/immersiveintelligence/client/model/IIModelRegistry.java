package pl.pabilo8.immersiveintelligence.client.model;

import blusunrize.immersiveengineering.client.ImmersiveModelRegistry;
import blusunrize.immersiveengineering.client.models.ModelItemDynamicOverride;
import blusunrize.immersiveengineering.common.items.ItemIEBase;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.client.render.IReloadableModelContainer;
import pl.pabilo8.immersiveintelligence.common.util.item.IIItemEnum;
import pl.pabilo8.immersiveintelligence.common.util.item.ItemIIBase;
import pl.pabilo8.immersiveintelligence.common.util.item.ItemIISubItemsBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pabilo8 on 14-09-2019.
 * The bestest of best!
 * Blu, please make variables public (and available to addon makers)
 */
@SideOnly(Side.CLIENT)
public class IIModelRegistry extends ImmersiveModelRegistry
{
	public static IIModelRegistry instance = new IIModelRegistry();
	//Yes
	public final HashMap<ModelResourceLocation, ItemModelReplacement> itemModelReplacements = new HashMap<>();
	private final Map<ResourceLocation, IReloadableModelContainer<?>> reloadableModels = new HashMap<>();

	@Override
	@SubscribeEvent
	public void onModelBakeEvent(ModelBakeEvent event)
	{
		for(Map.Entry<ModelResourceLocation, ItemModelReplacement> entry : itemModelReplacements.entrySet())
		{
			IBakedModel object = event.getModelRegistry().getObject(entry.getKey());
			if(object!=null)
			{
				try
				{
					event.getModelRegistry().putObject(entry.getKey(), entry.getValue().createBakedModel(object));
				} catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	//This
	public void registerCustomItemModel(ItemIIBase item, String modID, ItemModelReplacement replacement)
	{
		if(item instanceof ItemIISubItemsBase)
		{
			ItemIISubItemsBase<?> itemSub = (ItemIISubItemsBase<?>)item;
			for(IIItemEnum subItem : itemSub.getSubItems())
				itemModelReplacements.put(new ModelResourceLocation(
								new ResourceLocation(modID, itemSub.itemName+"/"+subItem.getName()), "inventory"),
						replacement);
		}
		else
		{
			itemModelReplacements.put(new ModelResourceLocation(
							new ResourceLocation(modID, item.itemName), "inventory"),
					replacement);
		}
	}

	public void registerCustomItemModel(ItemIEBase item, String modID, int... IDs)
	{
		for(int id : IDs)
			itemModelReplacements.put(new ModelResourceLocation(
							new ResourceLocation(modID, item.itemName+"/"+item.getSubNames()[id]), "inventory"),
					new ImmersiveModelRegistry.ItemModelReplacement()
					{
						@Override
						public IBakedModel createBakedModel(IBakedModel existingModel)
						{
							return new ModelItemDynamicOverride(existingModel, null);
						}
					});
	}

	/**
	 * Overloaded method
	 */
	public void registerCustomItemModel(ItemIIBase item)
	{
		registerCustomItemModel(item, ImmersiveIntelligence.MODID, new ImmersiveModelRegistry.ItemModelReplacement()
		{
			@Override
			public IBakedModel createBakedModel(IBakedModel existingModel)
			{
				return new ModelItemDynamicOverride(existingModel, null);
			}
		});
	}

	public void addReloadableModel(IReloadableModelContainer<?> model, ResourceLocation modelName)
	{
		reloadableModels.put(modelName, model);
	}

	public void removeReloadableModel(IReloadableModelContainer<?> model)
	{
		reloadableModels.remove(model);
	}

	public void reloadRegisteredModels()
	{
		reloadableModels.values().forEach(IReloadableModelContainer::reloadModels);
	}

	public boolean reloadModel(ResourceLocation modelName)
	{
		reloadableModels.forEach((s, iReloadableModelContainer) ->
		{
			if(s.equals(modelName))
				iReloadableModelContainer.reloadModels();
		});
		return reloadableModels.keySet().stream().anyMatch(resourceLocation -> resourceLocation.equals(modelName));
	}

	public Set<ResourceLocation> getReloadableModels()
	{
		return reloadableModels.keySet();
	}
}