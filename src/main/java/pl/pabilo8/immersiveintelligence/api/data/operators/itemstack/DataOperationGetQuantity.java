package pl.pabilo8.immersiveintelligence.api.data.operators.itemstack;

import net.minecraft.item.ItemStack;
import pl.pabilo8.immersiveintelligence.api.data.DataPacket;
import pl.pabilo8.immersiveintelligence.api.data.operators.DataOperator;
import pl.pabilo8.immersiveintelligence.api.data.types.*;

/**
 * @author Pabilo8
 * @since 05-07-2019
 */
public class DataOperationGetQuantity extends DataOperator
{
	public DataOperationGetQuantity()
	{
		//Gets the quantity (count) of the itemstack
		name = "get_quantity";
		sign = ">Q";
		allowedType1 = DataPacketTypeItemStack.class;
		allowedType2 = DataPacketTypeNull.class;
		expectedResult = DataPacketTypeInteger.class;
	}

	@Override
	public IDataType execute(DataPacket packet, DataPacketTypeExpression data)
	{
		DataPacketTypeItemStack t1;

		t1 = getVarInType(DataPacketTypeItemStack.class, data.getType1(), packet);
		ItemStack stack = t1.value;

		//Yes
		return new DataPacketTypeInteger(stack.getCount());
	}
}
