package pl.pabilo8.immersiveintelligence.common.compat;

import pl.pabilo8.immersiveintelligence.common.block.rotary_device.BlockIIMysticalMechanicsTB;

public class MysticalMechanicsAPIHelper  extends IICompatModule
{
	private BlockIIMysticalMechanicsTB mysticalMechanicsTB;

	@Override
	public void preInit()
	{
		mysticalMechanicsTB = new BlockIIMysticalMechanicsTB();
	}

	@Override
	public void registerRecipes()
	{
	}

	@Override
	public void init() {}

	@Override
	public void postInit() {}
}