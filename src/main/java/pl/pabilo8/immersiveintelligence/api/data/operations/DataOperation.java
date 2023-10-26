package pl.pabilo8.immersiveintelligence.api.data.operations;

import pl.pabilo8.immersiveintelligence.api.data.DataPacket;
import pl.pabilo8.immersiveintelligence.api.data.types.DataTypeExpression;
import pl.pabilo8.immersiveintelligence.api.data.types.IDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Template class for all <code>DataOperation</code> subclasses.
 * @ii-certified
 * @author Pabilo8
 * @since 05-07-2019
 */
public abstract class DataOperation
{
	@Nonnull
	public String name;
	@Nullable
	public String expression;
	@Nonnull
	public Class<? extends IDataType>[] allowedTypes;
	@Nullable
	public String[] params;
	public Class<? extends IDataType> expectedResult;

	/**
	 * Main function body of the data operation.
	 * @ii-certified
	 * @param packet {@link DataPacket} containing input data
	 * @param data {@link DataTypeExpression} containing input expression
	 * @return {@link IDataType} of desired type by the circuit
	 */
	@Nonnull
	public abstract IDataType execute(DataPacket packet, DataTypeExpression data);

}
