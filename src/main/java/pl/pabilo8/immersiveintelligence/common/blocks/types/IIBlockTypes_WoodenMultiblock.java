package pl.pabilo8.immersiveintelligence.common.blocks.types;

import blusunrize.immersiveengineering.common.blocks.BlockIEBase;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

/**
 * @author Pabilo8
 * @since 2019-06-04
 */
public enum IIBlockTypes_WoodenMultiblock implements IStringSerializable, BlockIEBase.IBlockEnum
{
	SKYCRATE_POST(false),
	SKYCRATE_STATION(false),
	SAWMILL(true),
	SKYCART_STATION(false);

	private final boolean needsCustomState;

	IIBlockTypes_WoodenMultiblock(boolean needsCustomState)
	{
		this.needsCustomState = needsCustomState;
	}

	@Override
	public String getName()
	{
		return this.toString().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public int getMeta()
	{
		return ordinal();
	}

	@Override
	public boolean listForCreative()
	{
		return false;
	}

	public boolean needsCustomState()
	{
		return this.needsCustomState;
	}

	public String getCustomState()
	{
		return getName().toLowerCase();
	}
}
