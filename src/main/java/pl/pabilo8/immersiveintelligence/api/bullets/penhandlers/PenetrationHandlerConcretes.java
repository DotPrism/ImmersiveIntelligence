package pl.pabilo8.immersiveintelligence.api.bullets.penhandlers;

import pl.pabilo8.immersiveintelligence.api.bullets.BulletRegistry.PenMaterialTypes;
import pl.pabilo8.immersiveintelligence.api.bullets.PenetrationRegistry.IPenetrationHandler;

/**
 * @author Pabilo8
 * @since 06-03-2020
 */
public class PenetrationHandlerConcretes
{
	public static class PenetrationHandlerConcrete implements IPenetrationHandler
	{
		@Override
		public float getIntegrity()
		{
			return 345f;
		}

		@Override
		public float getDensity()
		{
			return 1f;
		}

		@Override
		public PenMaterialTypes getPenetrationType()
		{
			return PenMaterialTypes.SOLID;
		}
	}

	public static class PenetrationHandlerLeadedConcrete implements IPenetrationHandler
	{
		@Override
		public float getIntegrity()
		{
			return 540f;
		}

		@Override
		public float getDensity()
		{
			return 1.55f;
		}

		@Override
		public PenMaterialTypes getPenetrationType()
		{
			return PenMaterialTypes.SOLID;
		}
	}
}
