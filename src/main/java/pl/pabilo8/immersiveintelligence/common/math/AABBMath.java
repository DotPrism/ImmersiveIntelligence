package pl.pabilo8.immersiveintelligence.common.math;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class AABBMath
{
	public static List<BlockPos> CreateSphere(Vec3i center, int radius)
	{
		List<BlockPos> sphere = new ArrayList<>();

		int ix = center.getX(), iy = center.getY(), iz = center.getZ();

		for(int x = ix-radius; x <= ix+radius; x++)
		{
			for(int y = iy-radius; y <= iy+radius; y++)
			{
				for(int z = iz-radius; z <= iz+radius; z++)
				{
					double dist = center.getDistance(x, y, z);
					if((dist < radius*radius)&&(dist < ((radius - 1) * (radius - 1))))
					{
						sphere.add(new BlockPos(x, y, z));
					}
				}
			}
		}

		return sphere;
	}
}
