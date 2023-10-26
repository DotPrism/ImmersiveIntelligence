package pl.pabilo8.immersiveintelligence.api;

import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.common.IIUtils;
import pl.pabilo8.immersiveintelligence.common.util.IISkinHandler;
import pl.pabilo8.immersiveintelligence.common.util.IISkinHandler.IISpecialSkin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @ii-certified
 * @author Pabilo8
 * @author GabrielV (gabriel@iiteam.net)
 * @since 30-06-2020
 * Interface used for all skinnable entities, items, etc.
 */
public interface ISkinnable
{
	/**
	 * Get skinnable's current skin
	 * @ii-certified
	 * @param stack of a skinnable item
	 * @return skin ID or empty string
	 */
	default String getSkinnableCurrentSkin(ItemStack stack)
	{
		return ItemNBTHelper.getString(stack, IISkinHandler.NBT_ENTRY);
	}

	/**
	 * Applies a skin to the ItemStack
	 * @ii-certified
	 * @param stack {@link ItemStack} of a skinnable item
	 * @param skinName skin ID
	 */
	default void applySkinnableSkin(ItemStack stack, String skinName)
	{
		ItemNBTHelper.setString(stack, IISkinHandler.NBT_ENTRY, skinName);
	}

	/**
	 * TODO: 8/12/2023 Replace all usages of this function with <code>{@link IISkinHandler#isValidSkin(String)}</code> ~GabrielV
	 * @deprecated Replaced by <code>{@link IISkinHandler#isValidSkin(String)}</code>
	 * @param skin skin ID
	 * @return true if the skin exists
	 */
	default boolean isValidSkin(String skin)
	{
		return !skin.isEmpty()&&IISkinHandler.specialSkins.containsKey(skin);
	}

	/**
	 * Internal function used by the skinnable. Adds the skin tooltip (skin name and description)
	 * @ii-certified
	 * @param stack {@link ItemStack} of the item we want to add tooltip to
	 * @param tooltip Tooltip of the item
	 */
	default void addSkinTooltip(@Nonnull ItemStack stack, @Nonnull List<String> tooltip)
	{
		String skin = getSkinnableCurrentSkin(stack);
		if(IISkinHandler.isValidSkin(skin))
		{
			IISpecialSkin s = IISkinHandler.specialSkins.get(skin);
			tooltip.add(s.rarity.rarityColor+I18n.format(String.format("skin.%1$s.%2$s.name", ImmersiveIntelligence.MODID, skin)));
			tooltip.add(IIUtils.getItalicString(I18n.format(String.format("skin.%1$s.%2$s.desc", ImmersiveIntelligence.MODID, skin))));
		}
	}

	/**
	 * @ii-certified
	 * @param stack {@link ItemStack} to get the skin from
	 * @return Skin rarity if found, otherwise <code>null</code>
	 */
	@Nullable
	default IRarity getSkinRarity(@Nonnull ItemStack stack)
	{
		String skin = getSkinnableCurrentSkin(stack);
		if(IISkinHandler.isValidSkin(skin))
			return IISkinHandler.getSkin(skin).rarity;
		return null;
	}

	/**
	 * @ii-certified
	 * @return Name of the skinnable
	 */
	String getSkinnableName();

	/**
	 * @ii-certified
	 * @return Default texture location of the skinnable
	 */
	String getSkinnableDefaultTextureLocation();
}
