package pl.pabilo8.immersiveintelligence.common.commands.ii.item;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.ArrayUtils;
import pl.pabilo8.immersiveintelligence.api.bullets.AmmoRegistry;
import pl.pabilo8.immersiveintelligence.api.bullets.AmmoRegistry.EnumCoreTypes;
import pl.pabilo8.immersiveintelligence.api.bullets.IAmmo;
import pl.pabilo8.immersiveintelligence.common.IIContent;
import pl.pabilo8.immersiveintelligence.common.item.ammo.ItemIIBulletMagazine.Magazines;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pabilo8
 * @since 23-06-2020
 */
public class CommandIIGiveMagazine extends CommandBase
{
	/**
	 * Gets the name of the command
	 */
	@Nonnull
	@Override
	public String getName()
	{
		return "magazine";
	}

	/**
	 * Gets the usage string for the command.
	 */
	@Nonnull
	@Override
	public String getUsage(@Nonnull ICommandSender sender)
	{
		return "Gives a bullet, usage: ii magazine <receiver> <name> <core> <coreType> [comps]";
	}

	/**
	 * Callback for when the command is executed
	 */
	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException
	{
		if(args.length > 3)
		{
			EntityPlayerMP player = CommandBase.getPlayer(server, sender, args[0]);
			Magazines magazines = IIContent.itemBulletMagazine.nameToSub(args[1]);

			ItemStack bullet = magazines.ammo.getBulletWithParams(
					args[2], args[3],
					Arrays.copyOfRange(args, 4, args.length)
			);
			ItemStack magazine = IIContent.itemBulletMagazine.getMagazine(magazines, bullet);
			player.addItemStackToInventory(magazine);
			sender.sendMessage(new TextComponentString("Magazine given!"));
		}
		else
			throw new WrongUsageException(getUsage(sender));
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel()
	{
		return 4;
	}

	/**
	 * Get a list of options for when the user presses the TAB key
	 */
	@Override
	@Nonnull
	@ParametersAreNonnullByDefault
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		if(args.length==1)
		{
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}
		else if(args.length==2)
		{
			return getListOfStringsMatchingLastWord(args, IIContent.itemBulletMagazine.getSubNames());
		}
		else if(args.length==3)
		{
			return getListOfStringsMatchingLastWord(args, AmmoRegistry.INSTANCE.registeredBulletCores.keySet());
		}
		else if(args.length==4)
		{
			if(!ArrayUtils.contains(IIContent.itemBulletMagazine.getSubNames(), args[1]))
				return Collections.emptyList();

			IAmmo matchingType = IIContent.itemBulletMagazine.nameToSub(args[1]).ammo;
			return getListOfStringsMatchingLastWord(args,
					Arrays.stream(matchingType.getAllowedCoreTypes())
							.map(EnumCoreTypes::getName)
							.collect(Collectors.toList()));
		}
		else if(args.length > 4)
		{
			return getListOfStringsMatchingLastWord(args, AmmoRegistry.INSTANCE.registeredComponents.keySet());
		}
		else
			return Collections.emptyList();
	}

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	@Override
	public boolean isUsernameIndex(@Nonnull String[] args, int index)
	{
		return index==0;
	}
}
