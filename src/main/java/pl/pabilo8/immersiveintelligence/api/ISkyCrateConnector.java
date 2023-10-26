package pl.pabilo8.immersiveintelligence.api;

import blusunrize.immersiveengineering.api.energy.wires.IImmersiveConnectable;
import pl.pabilo8.immersiveintelligence.common.entity.EntitySkyCrate;

/**
 * @ii-certified
 * @author Pabilo8
 * @since 26-12-2019
 */
public interface ISkyCrateConnector extends IImmersiveConnectable
{
	boolean onSkycrateMeeting(EntitySkyCrate skyCrate);
}
