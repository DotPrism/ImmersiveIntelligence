package pl.pabilo8.immersiveintelligence.common.network;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.client.ClientUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pl.pabilo8.immersiveintelligence.api.bullets.DamageBlockPos;
import pl.pabilo8.immersiveintelligence.api.bullets.PenetrationRegistry;

/**
 * @author Pabilo8
 * @since 2020-01-11
 */
public class MessageBlockDamageSync implements IMessage
{
	public float damage;
	public int dim, x, y, z;

	public MessageBlockDamageSync(DamageBlockPos pos)
	{
		this.damage = pos.damage;

		this.dim = pos.dimension;
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}

	public MessageBlockDamageSync()
	{
		this.damage = 0;

		this.dim = -1;
		this.x = Integer.MAX_VALUE;
		this.y = Integer.MAX_VALUE;
		this.z = Integer.MAX_VALUE;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeFloat(this.damage);
		buf.writeInt(this.dim);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.damage = buf.readFloat();
		this.dim = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();

	}

	public static class HandlerClient implements IMessageHandler<MessageBlockDamageSync, IMessage>
	{
		@Override
		public IMessage onMessage(MessageBlockDamageSync message, MessageContext ctx)
		{
			ClientUtils.mc().addScheduledTask(() ->
			{
				World world = ImmersiveEngineering.proxy.getClientWorld();

				if(world!=null) // This can happen if the task is scheduled right before leaving the world
				{
					DamageBlockPos bpos = new DamageBlockPos(message.x, message.y, message.z, message.dim, message.damage);

					if(message.damage > 0)
					{
						if(PenetrationRegistry.blockDamageClient.stream().anyMatch(damageBlockPos -> damageBlockPos.equals(bpos)))
						{
							PenetrationRegistry.blockDamageClient.forEach(damageBlockPos -> {
								if(damageBlockPos.equals(bpos))
									damageBlockPos.damage = bpos.damage;
							});
						}
						else
							PenetrationRegistry.blockDamageClient.add(bpos);
					}
					else
					{
						PenetrationRegistry.blockDamageClient.removeIf(damageBlockPos -> damageBlockPos.equals(bpos));
					}

				}
			});
			return null;
		}
	}

}