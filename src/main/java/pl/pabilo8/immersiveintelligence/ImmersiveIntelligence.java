package pl.pabilo8.immersiveintelligence;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import pl.pabilo8.immersiveintelligence.api.data.radio.RadioNetwork;
import pl.pabilo8.immersiveintelligence.common.CommonProxy;
import pl.pabilo8.immersiveintelligence.common.IILogger;
import pl.pabilo8.immersiveintelligence.common.IISaveData;
import pl.pabilo8.immersiveintelligence.common.IISounds;
import pl.pabilo8.immersiveintelligence.common.commands.CommandII;
import pl.pabilo8.immersiveintelligence.common.compat.IICompatModule;
import pl.pabilo8.immersiveintelligence.common.event.IEOverrideEventHandler;
import pl.pabilo8.immersiveintelligence.common.event.IIItemCreatedEventHandler;
import pl.pabilo8.immersiveintelligence.common.util.IISkinHandler;
import pl.pabilo8.immersiveintelligence.common.util.Reflector;

import static pl.pabilo8.immersiveintelligence.ImmersiveIntelligence.MODID;
import static pl.pabilo8.immersiveintelligence.ImmersiveIntelligence.VERSION;

@Mod(modid = MODID, version = VERSION,
		//xaxaxa, trick! yuo can't steal mod if mod is steal-proof
		certificateFingerprint = "770570c49a2652e64a9b29b9b9d9919ca68b7065",
		dependencies = "required-after:forge@[14.23.5.2820,);required-after:immersiveengineering@[0.12,);after:immersiveengineering@[0.12,);after:immersiveposts@[0.2,);before:buildcraftlib")
public class ImmersiveIntelligence
{
	public static final String MODID = "immersiveintelligence";
	public static final String VERSION = "@VERSION@";
	public static boolean loicense = false;

	public enum VersionType
	{
		RETAIL("RETAIL", TextFormatting.GOLD),
		BETA("BETA", TextFormatting.AQUA),
		DEV("DEVELOPMENT", TextFormatting.DARK_PURPLE);


		final String name;
		final TextFormatting formatting;
		VersionType(String name, TextFormatting formatting)
		{
			this.name = name;
			this.formatting = formatting;
		}

		@Override
		public String toString()
		{
			return this.formatting+this.name+TextFormatting.RESET;
		}
	}

	/**
	 * Use this to define the version
	 */
	public static final VersionType VERSION_TYPE = Enum.valueOf(VersionType.class, "@VERSION_TYPE@");

	@SidedProxy(clientSide = "pl.pabilo8.immersiveintelligence.client.ClientProxy", serverSide = "pl.pabilo8.immersiveintelligence.common.CommonProxy")
	public static CommonProxy proxy;

	@Instance(MODID)
	public static ImmersiveIntelligence INSTANCE;

	@Mod.EventHandler
	public void modIDMapping(FMLModIdMappingEvent event)
	{

	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		IILogger.logger = event.getModLog();
		proxy.preInit();
		ForgeChunkManager.setForcedChunkLoadingCallback(this, proxy);
		new IISkinHandler.ThreadContributorSpecialsDownloader();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//MinecraftForge.EVENT_BUS.unregister(new blusunrize.immersiveengineering.common.EventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, proxy);

		IIItemCreatedEventHandler iiItemCreatedEventHandler = new IIItemCreatedEventHandler();
		iiItemCreatedEventHandler.registerEventHandler();

		proxy.init();

		IISounds.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();

		/* Here you can override any registered event handlers */
		Reflector.getForgeEventListeners();
		Reflector.overrideEventHandler(blusunrize.immersiveengineering.common.EventHandler.class, new IEOverrideEventHandler());
	}

	@Mod.EventHandler
	public void loadComplete(FMLLoadCompleteEvent event)
	{
		IICompatModule.doModulesLoadComplete();
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER)
		{
			RadioNetwork.INSTANCE.clearDevices();
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
			if(!world.isRemote)
			{
				IISaveData worldData = (IISaveData)world.loadData(IISaveData.class, IISaveData.dataName);
				if(worldData==null)
				{
					worldData = new IISaveData(IISaveData.dataName);
					world.setData(IISaveData.dataName, worldData);
				}
				IISaveData.setInstance(world.provider.getDimension(), worldData);
			}
		}

		//CommonProxy.refreshFluidReferences();
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandII());
	}

	//If anyone wants to acquire a righteously certified loicense:tm:, ask @Pabilo8, it is probable he can grant you one
	private static final String[] alternativeCerts = {
			"4e1045a33d925770c5393a384c1a60f63f8f50e5"
	};

	@Mod.EventHandler
	public void wrongSignature(FMLFingerprintViolationEvent event)
	{
		for(String altCert : alternativeCerts)
			if(event.getFingerprints().contains(altCert))
			{
				System.out.println("[Immersive Intelligence/Error] "+altCert+" is considered a righteously loicensed certificate. "+
						"The build may not be stable, thou shall be ware of the bugs lurking from the shadows.");
				loicense = true;
				break;
			}
		if(!loicense)
		{
			System.out.println("[Immersive Intelligence/Error]IT IS VERY PROBABLE, "+
					"THE ENTITY WHO RELEASED THIS BUILD DOES NOT POSSESS A PROPER IMMERSIVE INTELLIGENCE LOICENSE! ");
			System.out.println("[Immersive Intelligence/Error]"+
					"CONTACT THE MINISTRY OF INTELLIGENCE IMMEDIATELLY! ");
			System.out.println("[Immersive Intelligence/Error]"+
					"FOUND THESE ILLEGAL FINGERPRINTS: "+event.getFingerprints());
		}
	}
}
