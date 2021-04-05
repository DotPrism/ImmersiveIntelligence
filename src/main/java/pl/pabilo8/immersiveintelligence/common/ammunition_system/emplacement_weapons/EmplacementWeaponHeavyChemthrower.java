package pl.pabilo8.immersiveintelligence.common.ammunition_system.emplacement_weapons;

import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.entities.EntityChemthrowerShot;
import blusunrize.immersiveengineering.common.util.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.pabilo8.immersiveintelligence.Config.IIConfig.Machines.Emplacement;
import pl.pabilo8.immersiveintelligence.Config.IIConfig.Weapons.EmplacementWeapons.Autocannon;
import pl.pabilo8.immersiveintelligence.Config.IIConfig.Weapons.EmplacementWeapons.HeavyChemthrower;
import pl.pabilo8.immersiveintelligence.client.render.multiblock.metal.EmplacementRenderer;
import pl.pabilo8.immersiveintelligence.client.tmt.ModelRendererTurbo;
import pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.metal.tileentities.second.TileEntityEmplacement;
import pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.metal.tileentities.second.TileEntityEmplacement.EmplacementWeapon;

public class EmplacementWeaponHeavyChemthrower extends EmplacementWeapon
{
	int setupDelay = 0;
	float shootDelay = 0;
	int reloadDelay = 0;
	int bulletsShot = 0;
	boolean shouldIgnite = false;
	FluidTank tank = new FluidTank(8000);
	private Vec3d vv;

	private static final Runnable INSERTER_ANIM_LENS = () -> {
		ClientUtils.bindTexture(EmplacementRenderer.textureInfraredObserver);
		GlStateManager.translate(-0.3125, 0.4225, 1.375);
		GlStateManager.rotate(180, 1, 0, 0);
		for(ModelRendererTurbo mod : EmplacementRenderer.modelInfraredObserver.lensModel)
			mod.render();
	};

	@SideOnly(Side.CLIENT)
	private static final Runnable INSERTER_ANIM_NONE = () -> {
	};

	@Override
	public String getName()
	{
		return "heavy_chemthrower";
	}

	@Override
	public float getYawTurnSpeed()
	{
		return HeavyChemthrower.yawRotateSpeed;
	}

	@Override
	public float getPitchTurnSpeed()
	{
		return HeavyChemthrower.pitchRotateSpeed;
	}

	@Override
	public void aimAt(float yaw, float pitch)
	{
		super.aimAt(yaw,pitch);
	}

	public boolean isSetUp(boolean door)
	{
		return setupDelay==(door?HeavyChemthrower.setupTime: 0);
	}

	@Override
	public void doSetUp(boolean door)
	{
		if(door)
		{
			if(setupDelay < HeavyChemthrower.setupTime)
				setupDelay += 1;
		}
		else
		{
			if(!isAimedAt(0, -90))
			{
				aimAt(0, -90);
				return;
			}
			if(setupDelay > 0)
				setupDelay -= 1;
		}
	}

	@Override
	public float[] getAnglePrediction(Vec3d posTurret, Vec3d posTarget, Vec3d motion)
	{
		// TODO: 08.03.2021 make it work 
		vv = posTurret.subtract(posTarget);
		float motionXZ = MathHelper.sqrt(vv.x*vv.x+vv.z*vv.z);
		Vec3d motionVec = new Vec3d(motion.x, motion.y, motion.z);
		float motionTime = (float)Math.abs(motionVec.lengthSquared());
		motionVec = motionVec.scale(motionTime);

		vv = vv.add(motionVec).subtract(0, getStackMass()*motionXZ, 0).normalize();
		float yy = (float)((Math.atan2(vv.x, vv.z)*180D)/3.1415927410125732D);
		float pp = (float)((Math.atan2(vv.y, motionXZ)*180D));
		pp = MathHelper.clamp(pp, -90, 75);
		return new float[]{yy, pp};
	}

	@Override
	public void tick()
	{
		if(bulletsShot >= 64)
		{
			reloadDelay++;
			if(reloadDelay >= Autocannon.reloadTime)
			{
				bulletsShot = 0;
				reloadDelay = 0;
			}
		}

		if(shootDelay > 0)
			shootDelay--;
	}

	@Override
	public void shoot(TileEntityEmplacement te)
	{
		Vec3d gun = new Vec3d(te.getBlockPosForPos(49).up()).addVector(0.5, 0.5, 0.5).add(vv.scale(-3));
		super.shoot(te);
		float range = 5;
		float scatter = 0.025f;
		//4mB per shot
		int split = Math.min(tank.getFluidAmount()/4,6);

		if(tank.getFluid()==null)
		{
			tank.fill(new FluidStack(IEContent.fluidCreosote, 1000),true);
		}
		else if(!te.getWorld().isRemote)
		{
			for(int i = 0; i < split; i++)
			{
				Vec3d vecDir = vv.scale(-1f).normalize().scale(2).add(new Vec3d(Utils.RAND.nextGaussian()*scatter, Utils.RAND.nextGaussian()*scatter, Utils.RAND.nextGaussian()*scatter));

				Vec3d g1 = gun.add(vv.rotateYaw(90).scale(0.25f));
				Vec3d g2 = gun.add(vv.rotateYaw(-90).scale(0.25f));

				EntityChemthrowerShot chem = new EntityChemthrowerShot(te.getWorld(), g1.x, g1.y, g1.z, vecDir.x, vecDir.y, vecDir.z, tank.getFluid());
				EntityChemthrowerShot chem2 = new EntityChemthrowerShot(te.getWorld(), g2.x, g2.y, g2.z, vecDir.x, vecDir.y, vecDir.z, tank.getFluid());

				// Apply momentum from the player.
				chem.motionX = vecDir.x;
				chem.motionY = vecDir.y;
				chem.motionZ = vecDir.z;
				chem2.motionX = vecDir.x;
				chem2.motionY = vecDir.y;
				chem2.motionZ = vecDir.z;

				tank.drain(4,true);

				if(shouldIgnite)
				{
					chem.setFire(10);
					chem2.setFire(10);
				}
				te.getWorld().spawnEntity(chem);
				te.getWorld().spawnEntity(chem2);
			}
		}
		shootDelay = Autocannon.bulletFireTime;
		//bulletsShot++;
	}

	@Override
	public NBTTagCompound saveToNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("yaw", yaw);
		tag.setFloat("pitch", pitch);
		tag.setInteger("setupDelay", setupDelay);
		return tag;
	}

	@Override
	public boolean canShoot(TileEntityEmplacement te)
	{
		return te.isDoorOpened&&tank.getFluidAmount()>0;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		yaw = tagCompound.getFloat("yaw");
		pitch = tagCompound.getFloat("pitch");
		setupDelay = tagCompound.getInteger("setupDelay");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void render(TileEntityEmplacement te, float partialTicks)
	{
		GlStateManager.pushMatrix();
		float p, pp, y, yy;
		p = this.nextPitch-this.pitch;
		y = this.nextYaw-this.yaw;
		pp = pitch+Math.signum(p)*MathHelper.clamp(Math.abs(p), 0, 1)*partialTicks*getPitchTurnSpeed();
		yy = yaw+Math.signum(y)*MathHelper.clamp(Math.abs(y), 0, 1)*partialTicks*getYawTurnSpeed();
		float setupProgress = (MathHelper.clamp(setupDelay+(pitch==-90?(te.isDoorOpened?(te.progress==Emplacement.lidTime?partialTicks: 0): -partialTicks): 0), 0, HeavyChemthrower.setupTime)/(float)HeavyChemthrower.setupTime);

		ClientUtils.bindTexture(EmplacementRenderer.textureHeavyChemthrower);

		for(ModelRendererTurbo mod : EmplacementRenderer.modelHeavyChemthrower.baseModel)
			mod.render();


		GlStateManager.rotate(yy, 0, 1, 0);
		for(ModelRendererTurbo mod : EmplacementRenderer.modelHeavyChemthrower.turretModel)
			mod.render();
		GlStateManager.translate(4.5f/16f, 14/16f, 4/16f);
		GlStateManager.rotate(pp, 1, 0, 0);
		for(ModelRendererTurbo mod : EmplacementRenderer.modelHeavyChemthrower.barrelStartModel)
			mod.render();
		for(ModelRendererTurbo mod : EmplacementRenderer.modelHeavyChemthrower.barrelMidModel)
			mod.render();

		GlStateManager.disableCull();
		for(ModelRendererTurbo mod : EmplacementRenderer.modelHeavyChemthrower.barrelEndModel)
			mod.render();
		GlStateManager.enableCull();

		GlStateManager.popMatrix();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderUpgradeProgress(int clientProgress, int serverProgress, float partialTicks)
	{

	}

	private double getStackMass()
	{
		if(tank.getFluid()!=null)
		return (tank.getFluid().getFluid().isGaseous()?0.025F: 0.05F)*(float)(tank.getFluid().getFluid().getDensity(tank.getFluid()) < 0?-1: 1);
		else
			return 0;
	}
}
