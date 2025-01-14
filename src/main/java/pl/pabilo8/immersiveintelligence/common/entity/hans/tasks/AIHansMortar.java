package pl.pabilo8.immersiveintelligence.common.entity.hans.tasks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pl.pabilo8.immersiveintelligence.common.IIUtils;
import pl.pabilo8.immersiveintelligence.common.IIContent;
import pl.pabilo8.immersiveintelligence.common.entity.EntityMortar;
import pl.pabilo8.immersiveintelligence.common.entity.bullet.EntityBullet;

import java.util.Optional;

/**
 * @author Pabilo8
 * @since 05.04.2021
 */
public class AIHansMortar extends EntityAIBase
{
	// TODO: 31.08.2021 yaw
	private final EntityLiving hans;
	private EntityMortar mortar = null;

	private Optional<Entity> target = Optional.empty();

	public AIHansMortar(EntityLiving hans)
	{
		this.hans = hans;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(hans.getRidingEntity() instanceof EntityMortar)
		{
			mortar = ((EntityMortar)hans.getRidingEntity());
		}
		else
		{
			hans.tasks.removeTask(this);
			return false;
		}

		if(mortar==null||mortar.isDead)
		{
			hans.tasks.removeTask(this);
			return false;
		}

		target = Optional.ofNullable(hans.getAttackTarget());
		return true;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask()
	{
		if(target.isPresent())
		{
			Entity t = target.get();
			this.hans.faceEntity(t, 10, 10);

			float pp = getAnglePrediction(mortar.getPositionVector().addVector(0, 1, 0), t.getPositionVector(), new Vec3d(t.motionX, t.motionY, t.motionZ))[1];

			mortar.gunPitchUp = (90+mortar.rotationPitch)-pp < 0;
			mortar.gunPitchDown = (90+mortar.rotationPitch)-pp > 0;
			if(Math.abs((90+mortar.rotationPitch)-pp) <= 5)
			{
				if(hans.getHeldItemMainhand().isEmpty())
				{
					ItemStack shell = IIContent.itemAmmoMortar.getBulletWithParams("core_brass", "canister", "white_phosphorus", "tracer_powder");
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("colour", 0xff0000);
					hans.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, shell);
				}

				if(mortar.shootingProgress==0)
					mortar.fireKeyPress = true;

				if(mortar.fireKeyPress)
					mortar.getEntityWorld().getEntitiesWithinAABB(EntityItem.class, mortar.getEntityBoundingBox()).forEach(Entity::setDead);
			}
		}
	}

	public boolean isAimedAt(float yaw, float pitch)
	{
		return pitch==mortar.rotationPitch;
	}

	public float[] getAnglePrediction(Vec3d posTurret, Vec3d posTarget, Vec3d motion)
	{
		Vec3d dist = posTurret.subtract(posTarget.add(motion));
		Vec3d norm = dist.normalize();

		float yy = (float)((Math.atan2(norm.x, norm.z)*180D)/3.1415927410125732D);

		float pp = Math.round(IIUtils.calculateBallisticAngle(
				new Vec3d(dist.x, 0, dist.z).distanceTo(Vec3d.ZERO)
				, dist.y,
				IIContent.itemAmmoMortar.getDefaultVelocity(),
				EntityBullet.GRAVITY*3.1875f,
				1f-EntityBullet.DRAG, 0.01));

		return new float[]{MathHelper.wrapDegrees(180-yy), 90-pp};
	}
}
