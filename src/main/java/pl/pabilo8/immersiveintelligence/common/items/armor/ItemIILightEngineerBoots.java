package pl.pabilo8.immersiveintelligence.common.items.armor;

import blusunrize.immersiveengineering.api.tool.IElectricEquipment;
import blusunrize.immersiveengineering.common.util.IEDamageSources.ElectricDamageSource;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.pabilo8.immersiveintelligence.client.model.armor.ModelLightEngineerArmor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * @author Pabilo8
 * @since 13.09.2020
 */
public class ItemIILightEngineerBoots extends ItemIILightEngineerArmorBase implements IElectricEquipment
{
	public ItemIILightEngineerBoots()
	{
		super(EntityEquipmentSlot.FEET, "LIGHT_ENGINEER_BOOTS");
	}

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default)
	{
		return ModelLightEngineerArmor.getModel(armorSlot, itemStack);
	}

	@Override
	String getMaterialName(ArmorMaterial material)
	{
		return "light_engineer_armor";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(@Nonnull ItemStack stack, @Nullable World world, List<String> list, @Nonnull ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);
	}

	@Override
	public float getXpRepairRatio(ItemStack stack)
	{
		return 0.1f;
	}

	@Nonnull
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot equipmentSlot, @Nonnull ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

		if(equipmentSlot==this.armorType)
		{
			if(ItemNBTHelper.hasKey(stack, "flippin"))
			{
				multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Flippers", 4, 2));
			}
			if(ItemNBTHelper.hasKey(stack, "rackets"))
			{
				multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Rackets", 0.5, 1));
			}
			//if(getUpgrades(stack).hasKey(""))
			//multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Power Armor Movement Speed Debuff", -.03, 1));
			//multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Power Armor Movement Speed Debuff", -.03, 1));
		}
		return multimap;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
	{
		super.onArmorTick(world, player, stack);
		if(getUpgrades(stack).hasKey("flippers")&&(player.isInWater())&&player.isSprinting())
			ItemNBTHelper.setBoolean(stack, "flippin", true);
		else if(ItemNBTHelper.hasKey(stack, "flippin"))
			ItemNBTHelper.remove(stack, "flippin");

		Material mat = world.getBlockState(player.getPosition()).getMaterial();
		Material matDown = world.getBlockState(player.getPosition().down()).getMaterial();

		boolean rackets = getUpgrades(stack).hasKey("snow_rackets");
		if(rackets&&(mat==Material.SNOW||mat==Material.CRAFTED_SNOW))
		{
			ItemNBTHelper.setBoolean(stack, "rackets", true);
		}
		else if(rackets&&(matDown==Material.ICE||matDown==Material.PACKED_ICE))
		{
			player.move(MoverType.SELF, player.motionX*5, player.motionY, player.motionZ*5);
			player.motionX *= 0.5;
			player.motionZ *= 0.5;
		}
		else if(ItemNBTHelper.hasKey(stack, "rackets"))
			ItemNBTHelper.remove(stack, "rackets");


	}

	@Override
	public void onStrike(ItemStack s, EntityEquipmentSlot eqSlot, EntityLivingBase p, Map<String, Object> cache,
						 @Nullable DamageSource dSource, ElectricSource eSource)
	{
		if(!(dSource instanceof ElectricDamageSource))
		{

		}
	}

	@Override
	public int getSlotCount()
	{
		return 3;
	}
}
