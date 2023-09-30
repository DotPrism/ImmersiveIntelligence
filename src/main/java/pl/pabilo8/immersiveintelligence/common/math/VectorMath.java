package pl.pabilo8.immersiveintelligence.common.math;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3i;

public class VectorMath
{
	public static float dotProduct(Vec2f a, Vec2f b)
	{
		return (a.x*b.x)+(a.y*b.y);
	}

	public static float distanceTo(Vec2f a, Vec2f b)
	{
		float d0 = a.x-b.x, d1 = a.y-b.y;
		return MathHelper.sqrt(d0*d0+d1*d1);
	}

	public static float squareDistanceTo(Vec2f a, Vec2f b)
	{
		float d0 = a.x-b.x, d1 = a.y-b.y;
		return d0*d0+d1*d1;
	}

	public static float length(Vec2f v)
	{
		return MathHelper.sqrt(v.x*v.x+v.y*v.y);
	}

	public static float lengthSquared(Vec2f v)
	{
		return v.x*v.x+v.y*v.y;
	}

	public static Vec3i addVec3i(Vec3i a, Vec3i b)
	{
		return new Vec3i(a.getX()+b.getX(), a.getY()+b.getY(), a.getZ()+b.getZ());
	}
}
