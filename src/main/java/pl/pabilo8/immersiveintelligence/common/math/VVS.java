package pl.pabilo8.immersiveintelligence.common.math;

import net.minecraft.util.math.Vec3i;

/**
 * A "very complex" class that is used to create something I call VVS (Voxel Volumetric Shape).<br>
 * Basically a shape that has a volume, is made of voxels and can fill spaces
 *
 * @author GabrielV
 */
public class VVS
{
	// NOTE: Directions are not made from facing direction.
	public static final Vec3i UP = new Vec3i(0, 1, 0);
	public static final Vec3i DOWN = new Vec3i(0, -1, 0);
	public static final Vec3i RIGHT = new Vec3i(0, 0, 1);
	public static final Vec3i LEFT = new Vec3i(0, 0, -1);
	public static final Vec3i FORWARD = new Vec3i(1, 0, 0);
	public static final Vec3i BACKWARD = new Vec3i(-1, 0, 0);
}
