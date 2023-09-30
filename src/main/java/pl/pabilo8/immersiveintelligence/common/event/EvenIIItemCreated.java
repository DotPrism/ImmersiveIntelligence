package pl.pabilo8.immersiveintelligence.common.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EvenIIItemCreated extends Event
{
	private EntityPlayer entityPlayer;
	private ItemStack itemStack;

	public EvenIIItemCreated(EntityPlayer entityPlayer, ItemStack itemStack)
	{
		this.itemStack = itemStack;
		this.entityPlayer = entityPlayer;
	}

	public EntityPlayer getEntityPlayer()
	{
		return this.entityPlayer;
	}
	public ItemStack getItemStack()
	{
		return this.itemStack;
	}

	/**
	 * Fire {@link EvenIIItemCreated} event.
	 * @param entityPlayer Player that created the item
	 * @param itemStack Item that was created
	 * @return New instance of {@link EvenIIItemCreated}
	 */
	public static EvenIIItemCreated fireItemCreatedEvent(EntityPlayer entityPlayer, ItemStack itemStack)
	{
		EvenIIItemCreated event = new EvenIIItemCreated(entityPlayer, itemStack);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}
}
