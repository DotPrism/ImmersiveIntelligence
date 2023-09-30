package pl.pabilo8.immersiveintelligence.common.item.crafting;

import pl.pabilo8.immersiveintelligence.common.item.crafting.ItemIIMaterial.Materials;
import pl.pabilo8.immersiveintelligence.common.util.item.IIItemEnum;
import pl.pabilo8.immersiveintelligence.common.util.item.ItemIISubItemsBase;

/**
 * @author Pabilo8
 * @since 2019-05-11
 */
public class ItemIIMaterial extends ItemIISubItemsBase<Materials>
{
	public ItemIIMaterial()
	{
		super("material", 64, Materials.values());
	}

	public enum Materials implements IIItemEnum
	{
		@IIItemProperties(oreDict = "electronTubeAdvanced", category = IICategory.RESOURCE)
		ADVANCED_ELECTRON_TUBE,

		@IIItemProperties(oreDict = {"chipBasic", "oc:circuitChip1"}, category = IICategory.RESOURCE)
		BASIC_ELECTRONIC_ELEMENT,
		@IIItemProperties(oreDict = {"circuitBasicRaw", "oc:materialCircuitBoardRaw"}, category = IICategory.RESOURCE)
		BASIC_CIRCUIT_BOARD_RAW,
		@IIItemProperties(oreDict = {"circuitBasicEtched", "oc:materialCircuitBoardPrinted"}, category = IICategory.RESOURCE)
		BASIC_CIRCUIT_BOARD_ETCHED,

		@IIItemProperties(oreDict = {"chipAdvanced", "oc:circuitChip2"}, category = IICategory.RESOURCE)
		ADVANCED_ELECTRONIC_ELEMENT,
		@IIItemProperties(oreDict = {"circuitAdvancedRaw", "oc:materialCircuitBoardRaw"}, category = IICategory.RESOURCE)
		ADVANCED_CIRCUIT_BOARD_RAW,
		@IIItemProperties(oreDict = {"circuitAdvancedEtched", "oc:materialCircuitBoardPrinted"}, category = IICategory.RESOURCE)
		ADVANCED_CIRCUIT_BOARD_ETCHED,
		@IIItemProperties(oreDict = "circuitAdvanced", category = IICategory.RESOURCE)
		ADVANCED_CIRCUIT_BOARD,

		@IIItemProperties(oreDict = {"transistor", "oc:materialTransistor"}, category = IICategory.RESOURCE)
		TRANSISTOR,
		@IIItemProperties(oreDict = {"chipProcessor", "chipElite", "oc:circuitChip3"}, category = IICategory.RESOURCE)
		PROCESSOR_ELECTRONIC_ELEMENT,
		@IIItemProperties(oreDict = {"circuitProcessorRaw", "circuitEliteRaw", "oc:materialCircuitBoardRaw"}, category = IICategory.RESOURCE)
		PROCESSOR_CIRCUIT_BOARD_RAW,
		@IIItemProperties(oreDict = {"circuitProcessorEtched", "circuitEliteEtched", "oc:materialCircuitBoardPrinted"}, category = IICategory.RESOURCE)
		PROCESSOR_CIRCUIT_BOARD_ETCHED,
		@IIItemProperties(oreDict = {"circuitProcessor", "circuitElite"}, category = IICategory.RESOURCE)
		PROCESSOR_CIRCUIT_BOARD,

		@IIItemProperties(oreDict = {"engineElectricSmall", "engineElectricCompact"}, category = IICategory.RESOURCE)
		COMPACT_ELECTRIC_ENGINE,
		@IIItemProperties(oreDict = {"engineElectricSmallAdvanced", "engineElectricCompactAdvanced"}, category = IICategory.RESOURCE)
		COMPACT_ELECTRIC_ENGINE_ADVANCED,

		@IIItemProperties(oreDict = "punchtapeEmpty", category = IICategory.INTELLIGENCE)
		PUNCHTAPE_EMPTY,

		@IIItemProperties(oreDict = {"materialRDX", "materialHexogen"}, category = IICategory.RESOURCE)
		DUST_RDX,
		@IIItemProperties(oreDict = {"materialHMX", "materialOctogen"}, category = IICategory.RESOURCE)
		DUST_HMX,
		@IIItemProperties(oreDict = {"dustWhitePhosphorus", "whitePhosphorus"}, category = IICategory.RESOURCE)
		WHITE_PHOSPHORUS,
		@IIItemProperties(oreDict = "dustSalt", category = IICategory.RESOURCE)
		DUST_SALT,

		@IIItemProperties(oreDict = "brushCarbon", category = IICategory.RESOURCE)
		CARBON_BRUSH,
		@IIItemProperties(hidden = true, category = IICategory.RESOURCE)
		GLASS_FIBRE_CABLE,

		@IIItemProperties(oreDict = "dustWood", category = IICategory.RESOURCE)
		DUST_WOOD,
		@IIItemProperties(oreDict = "dustFormaldehyde", category = IICategory.RESOURCE)
		DUST_FORMALDEHYDE,
		@IIItemProperties(oreDict = "dustHexamine", category = IICategory.RESOURCE)
		DUST_HEXAMINE,
		@IIItemProperties(oreDict = "pulpWood", category = IICategory.RESOURCE)
		PULP_WOOD,
		@IIItemProperties(oreDict = "pulpWoodTreated", category = IICategory.RESOURCE)
		PULP_WOOD_TREATED,
		@IIItemProperties(oreDict = {"leather", "leatherArtificial"}, category = IICategory.RESOURCE)
		ARTIFICIAL_LEATHER,

		@IIItemProperties(oreDict = "rubberRaw", category = IICategory.RESOURCE)
		NATURAL_RUBBER,
		@IIItemProperties(oreDict = {"itemRubber", "materialRubber"}, category = IICategory.RESOURCE)
		RUBBER_BELT,
		@IIItemProperties(oreDict = "tireRubber", category = IICategory.RESOURCE)
		RUBBER_TIRE,
		@IIItemProperties(oreDict = "dustVulcanizationCompound", category = IICategory.RESOURCE)
		RUBBER_COMPOUND,

		@IIItemProperties(oreDict = "templateCircuit", hidden = true, category = IICategory.RESOURCE)
		CIRCUIT_TEMPLATE,

		@IIItemProperties(oreDict = "circuitCryptographic", category = IICategory.RESOURCE)
		CRYPTOGRAPHIC_CIRCUIT_BOARD,
	}
}
