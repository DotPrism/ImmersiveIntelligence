package pl.pabilo8.immersiveintelligence.common.items.bullet_casings;

import net.minecraft.item.ItemStack;
import pl.pabilo8.immersiveintelligence.api.bullets.IBulletCasingType;
import pl.pabilo8.immersiveintelligence.client.ParticleUtils;
import pl.pabilo8.immersiveintelligence.client.model.IBulletModel;
import pl.pabilo8.immersiveintelligence.client.model.misc.ModelBullet;
import pl.pabilo8.immersiveintelligence.common.CommonProxy;
import pl.pabilo8.immersiveintelligence.common.entity.bullets.EntityBullet;
import pl.pabilo8.immersiveintelligence.common.items.ItemIIBase;

/**
 * @author Pabilo8
 * @since 30-08-2019
 */
public class ItemIICasingMachinegun extends ItemIIBase implements IBulletCasingType
{
	public ItemIICasingMachinegun()
	{
		super("casing_machinegun", 24);
	}

	@Override
	public String getName()
	{
		return "machinegun_2bCal";
	}

	@Override
	public float getComponentCapacity()
	{
		return 0.125f;
	}

	@Override
	public int getGunpowderNeeded()
	{
		return 6;
	}

	@Override
	public int getCoreMaterialNeeded()
	{
		return 4;
	}

	@Override
	public float getInitialMass()
	{
		return 0.125f;
	}

	@Override
	public float getSize()
	{
		return 0.125f;
	}

	@Override
	public int getStackSize()
	{
		return 24;
	}

	@Override
	public Class<? extends IBulletModel> getModel()
	{
		return ModelBullet.class;
	}

	@Override
	public float getPenetration()
	{
		return 0.5f;
	}

	@Override
	public float getDamage()
	{
		return 10;
	}

	@Override
	public ItemStack getStack(int amount)
	{
		return new ItemStack(CommonProxy.item_casing_machinegun, amount);
	}

	@Override
	public void doPuff(EntityBullet bullet)
	{
		for(int i = 1; i < 5; i++)
			ParticleUtils.spawnGunfireFX(bullet.posX, bullet.posY, bullet.posZ, bullet.motionX/i, bullet.motionY/i, bullet.motionZ/i, 8*bullet.getSize()*(i/2.5f));
		//ImmersiveEngineering.proxy.spawnRedstoneFX(bullet.world, bullet.posX, bullet.posY, bullet.posZ, 0, 0, 0, 1.5f, 0.75f, 0.75f, 0.75f);
	}

	@Override
	public float getSupressionRadius()
	{
		return 2;
	}

	@Override
	public int getSuppressionPower()
	{
		return 2;
	}
}
