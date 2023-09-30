package pl.pabilo8.immersiveintelligence.common.math;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * A *very complex* class that is used to create something I call VVS (Voxel Volumetric Shape).<br>
 * Basically a shape that has a volume, is made of voxels and can fill spaces
 *
 * @author GabrielV
 */
public class VVS
{
	// NOTE: Directions are not made from facing direction.
	public static final Vec3i UP = new Vec3i(0, 1, 0);
	public static final Vec3i DOWN = new Vec3i(0, 0, 0);
	public static final Vec3i RIGHT = new Vec3i(0, 0, 1);
	public static final Vec3i LEFT = new Vec3i(0, 0, -1);
	public static final Vec3i FORWARD = new Vec3i(1, 0, 0);
	public static final Vec3i BACKWARD = new Vec3i(-1, 0, 0);

	public static List<Vec3i> createSphere(Vec3i center, int radius)
	{
		List<Vec3i> sphere = new ArrayList<>();

		int ix = center.getX(), iy = center.getY(), iz = center.getZ();

		for(int x = ix-radius; x <= ix+radius; x++)
		{
			for(int y = iy-radius; y <= iy+radius; y++)
			{
				for(int z = iz-radius; z <= iz+radius; z++)
				{
					double dist = center.getDistance(x, y, z);
					if((dist < radius*radius)&&(dist < ((radius-1)*(radius-1))))
						sphere.add(new Vec3i(x, y, z));
				}
			}
		}

		return sphere;
	}

	public static List<Vec3i> createHemisphere(World world, Vec3i center, int radius)
	{
		Vec3i dirs[] = {UP, DOWN, RIGHT, LEFT, FORWARD, BACKWARD};
		List<Vec3i> sphere = new ArrayList<>();

		int ix = center.getX(), iy = center.getY(), iz = center.getZ();

		for(int x = ix-radius; x <= ix+radius; x++)
		{
			for(int y = iy; y <= iy+radius; y++)
			{
				for(int z = iz-radius; z <= iz+radius; z++)
				{
					double dist = center.getDistance(x, y, z);
					if((dist < radius*radius)&&(dist < ((radius-1)*(radius-1))))
					{
						Vec3i pos = new Vec3i(x, y, z);
						if (world.isBlockFullCube(new BlockPos(pos)))
						{
							for (Vec3i dir : dirs)
							{
								Vec3i newPos = VectorMath.addVec3i(pos, dir);
								if (world.isBlockFullCube(new BlockPos(newPos))) continue;
								pos = newPos;
							}
						}
						sphere.add(pos);
					}
				}
			}
		}

		return sphere;
	}
}
