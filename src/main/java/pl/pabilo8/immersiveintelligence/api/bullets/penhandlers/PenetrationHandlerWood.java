package pl.pabilo8.immersiveintelligence.api.bullets.penhandlers;

import pl.pabilo8.immersiveintelligence.api.bullets.BulletRegistry.PenMaterialTypes;
import pl.pabilo8.immersiveintelligence.api.bullets.PenetrationRegistry.IPenetrationHandler;

/**
 * @author Pabilo8
 * @since 06-03-2020
 */
public class PenetrationHandlerWood
{
	public static class PenetrationHandlerLog implements IPenetrationHandler
	{
		@Override
		public float getIntegrity()
		{
			return 165f;
		}

		@Override
		public float getDensity()
		{
			return 0.95f;
		}

		@Override
		public PenMaterialTypes getPenetrationType()
		{
			return PenMaterialTypes.GROUND;
		}
	}

	public static class PenetrationHandlerPlanks implements IPenetrationHandler
	{
		@Override
		public float getIntegrity()
		{
			return 75f;
		}

		@Override
		public float getDensity()
		{
			return 0.65f;
		}

		@Override
		public PenMaterialTypes getPenetrationType()
		{
			return PenMaterialTypes.GROUND;
		}
	}
}
