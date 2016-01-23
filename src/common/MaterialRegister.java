package common;

import client.tileentity.InfomationSolar;
import client.tileentity.InfomationTeleporter;
import client.tileentity.TileEntitySolarPanel;
import client.tileentity.TileEntityTeleporter;
import common.block.BlockOfSteel;
import common.block.Cable;
import common.block.FluidPipe;
import common.block.FoodCraftTable;
import common.block.FoodFurnace;
import common.block.ResourcePortal;
import common.block.SolarPanel;
import common.block.Teleporter;
import common.block.ThermalGenerator;
import common.item.DishEmpty;
import common.item.ItemBase;
import common.item.UpgradeFuel;
import common.item.UpgradeSpeed;
import common.item.VoidBucketSteel;
import common.item.Wrench;
import common.item.armor.RevolutionArmor;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class MaterialRegister {

	public static MaterialRegister instance = new MaterialRegister();

	public static ArmorMaterial REVOLUTIONARMOR = EnumHelper.addArmorMaterial("REVOLUTIONARMOR", -1, new int[] { 3, 8, 6, 3 }, 30);

	public static final Block solarPanelBasic = new SolarPanel.Basic("solarBasic", 8);
	public static final Block solarPanelBasicI = new SolarPanel.BasicI("solarBasicI", 16);
	public static final Block solarPanelBasicII = new SolarPanel.BasicII("solarBasicII", 32);
	public static final Block solarPanelBasicIII = new SolarPanel.BasicIII("solarBasicIII", 64);

	public static final Block solarPanelAdvanced = new SolarPanel.Advanced("solarAdvanced", 128);
	public static final Block solarPanelAdvancedI = new SolarPanel.AdvancedI("solarAdvancedI", 256);
	public static final Block solarPanelAdvancedII = new SolarPanel.AdvancedII("solarAdvancedII", 512);
	public static final Block solarPanelAdvancedIII = new SolarPanel.AdvancedIII("solarAdvancedIII", 1024);

	public static final Block solarPanelEnchanted = new SolarPanel.Enchanted("solarEnchanted", 2048);
	public static final Block solarPanelEnchantedI = new SolarPanel.EnchantedI("solarEnchantedI", 4096);
	public static final Block solarPanelEnchantedII = new SolarPanel.EnchantedII("solarEnchantedII", 8192);
	public static final Block solarPanelEnchantedIII = new SolarPanel.EnchantedIII("solarEnchantedIII", 16384);

	public static ItemArmor revolutionHelmet;
	public static ItemArmor revolutionChestPlate;
	public static ItemArmor revolutionLeggins;
	public static ItemArmor revolutionBoots;

	public static Item itemMetal;
	public static Item wrench;

	public static Item voidBucketSteel;
	public static Item dishEmpty;

	public static Item upgrade_speed;
	public static Item upgrade_fuel;

	public static Block FoodFurnace_Active;
	public static Block FoodFurnace_Idle;
	public static Block ThermalGenerator_Active;
	public static Block ThermalGenerator_Idle;
	public static Block foodCraftTable;
	public static Block resourcePortal;
	public static Block teleporter;
	public static Block cableEnergy;
	public static Block fluidPipe;

	public static Block blockOfSteel;

	private MaterialRegister(){
	}

	public void registOreDic(){
		OreDictionary.registerOre("fmrUpgrades", new ItemStack(upgrade_fuel));
		OreDictionary.registerOre("fmrUpgrades", new ItemStack(upgrade_speed));

		OreDictionary.registerOre("dishEmpty", new ItemStack(dishEmpty));

		OreDictionary.registerOre("fmrFoodFurnace", FoodFurnace_Active);
		OreDictionary.registerOre("fmrFoodFurnace", FoodFurnace_Idle);
	}

	public void load(){
		//アイテム
		GameRegistry.registerItem(voidBucketSteel, "fmr.voidBucSteel");
		GameRegistry.registerItem(dishEmpty, "fmr.dishEmpty");
		GameRegistry.registerItem(wrench, "fmr.wrench");

		//防具

		//食べ物素材

		//インゴット

		//機械
		GameRegistry.registerBlock(FoodFurnace_Idle, "fmr.FoodFurnace_Idle");
		GameRegistry.registerBlock(FoodFurnace_Active, "fmr.FoodFurnace_Active");
		GameRegistry.registerBlock(ThermalGenerator_Active, "fmr.tGenerator_Active");
		GameRegistry.registerBlock(ThermalGenerator_Idle, "fmr.tGenerator_Idle");
		GameRegistry.registerBlock(resourcePortal, "fmr.resourcePortal");
		GameRegistry.registerBlock(teleporter, InfomationTeleporter.class, "fmr.teleporter");

		GameRegistry.registerBlock(cableEnergy, "fmr.blockCable");
		GameRegistry.registerBlock(fluidPipe, "fmr.blockCable.1");

		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "fmr.teleporter");

		GameRegistry.registerBlock(solarPanelBasic, InfomationSolar.class, "solarBasic");
		GameRegistry.registerBlock(solarPanelBasicI, InfomationSolar.class, "solarBasicI");
		GameRegistry.registerBlock(solarPanelBasicII, InfomationSolar.class, "solarBasicII");
		GameRegistry.registerBlock(solarPanelBasicIII, InfomationSolar.class, "solarBasicIII");


		GameRegistry.registerBlock(solarPanelAdvanced, InfomationSolar.class, "solarAdvanced");
		GameRegistry.registerBlock(solarPanelAdvancedI, InfomationSolar.class, "solarAdvancedI");
		GameRegistry.registerBlock(solarPanelAdvancedII, InfomationSolar.class, "solarAdvancedII");
		GameRegistry.registerBlock(solarPanelAdvancedIII, InfomationSolar.class, "solarAdvancedIII");

		GameRegistry.registerBlock(solarPanelEnchanted, InfomationSolar.class, "solarEnchanted");
		GameRegistry.registerBlock(solarPanelEnchantedI, InfomationSolar.class, "solarEnchantedI");
		GameRegistry.registerBlock(solarPanelEnchantedII, InfomationSolar.class, "solarEnchantedII");
		GameRegistry.registerBlock(solarPanelEnchantedIII, InfomationSolar.class, "solarEnchantedIII");

		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "tileSolarPanel");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.Basic.class, "tileSolarBasic");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.BasicI.class, "tileSolarBasicI");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.BasicII.class, "tileSolarBasicII");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.BasicIII.class, "tileSolarBasicIII");

		GameRegistry.registerTileEntity(TileEntitySolarPanel.Advanced.class, "tileSolarAdvanced");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.AdvancedI.class, "tileSolarAdvancedI");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.AdvancedII.class, "tileSolarAdvancedII");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.AdvancedIII.class, "tileSolarAdvancedIII");

		GameRegistry.registerTileEntity(TileEntitySolarPanel.Enchanted.class, "tileSolarEnchanted");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.EnchantedI.class, "tileSolarEnchantedI");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.EnchantedII.class, "tileSolarEnchantedII");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.EnchantedIII.class, "tileSolarEnchantedIII");

		//ブロック
		GameRegistry.registerBlock(blockOfSteel, "fmr.blockOfSteel");
		GameRegistry.registerBlock(foodCraftTable, "fmr.fCrafting");

		//植物 作物系

		//モンスター

		//機械アップグレード
		GameRegistry.registerItem(upgrade_speed, "fmr.upgrade_speed");
		GameRegistry.registerItem(upgrade_fuel, "fmr.upgrade_fuel");
	}

	public void addFluid(){
	}

	static void addItem(){
		upgrade_speed = new UpgradeSpeed().setCreativeTab(FoodMachineryRevolution.tabFMR);
		upgrade_fuel = new UpgradeFuel().setCreativeTab(FoodMachineryRevolution.tabFMR);
		voidBucketSteel = new VoidBucketSteel().setCreativeTab(FoodMachineryRevolution.tabFMR);
		dishEmpty = new DishEmpty().setCreativeTab(FoodMachineryRevolution.tabFMR);
		wrench = new Wrench().setCreativeTab(FoodMachineryRevolution.tabFMR);

		itemMetal = new ItemBase("metalIngot", 64,
												/*Ingot*/
												"ingotSteel", "ingotCopper", "ingotTin", "ingotLead", "ingotSilver",
												"ingotElectrum", "ingotNickel", "ingotAluminium",

												/*Dust*/
												"dustSteel", "dustCopper", "dustTin", "dustLead", "dustSilver",
												"dustElectrum", "dustNickel", "dustAluminium",
												"dustIron", "dustGold", "dustCoal", "dustQuartz",

												/*Nugget*/
												"nuggetSteel", "nuggetCopper", "nuggetTin", "nuggetLead", "nuggetSilver",
												"nuggetElectrum", "nuggetNickel", "nuggetAluminium",
												"nuggetIron", "nuggetGold");

		revolutionHelmet = new RevolutionArmor(REVOLUTIONARMOR, 0, "revHelm");
		revolutionChestPlate = new RevolutionArmor(REVOLUTIONARMOR, 1, "revChest");
		revolutionLeggins = new RevolutionArmor(REVOLUTIONARMOR, 2, "revLeg");
		revolutionBoots = new RevolutionArmor(REVOLUTIONARMOR, 3, "revBoots");
	}

	static void addTools(){
	}

	static void addFoods(){
	}

	static void addBlocks(){
		FoodFurnace_Idle = new FoodFurnace(false).setBlockName("fmr.FoodFurnace_Idle").setCreativeTab(FoodMachineryRevolution.tabFMR);
		FoodFurnace_Active = new FoodFurnace(true).setBlockName("fmr.FoodFurnace_Active").setCreativeTab(FoodMachineryRevolution.tabFMR);
		blockOfSteel = new BlockOfSteel().setCreativeTab(FoodMachineryRevolution.tabFMR);
		foodCraftTable = new FoodCraftTable().setCreativeTab(FoodMachineryRevolution.tabFMR);
		ThermalGenerator_Active = new ThermalGenerator(true).setBlockName("fmr.tGenerator_Active").setCreativeTab(FoodMachineryRevolution.tabFMR);
		ThermalGenerator_Idle = new ThermalGenerator(false).setBlockName("fmr.tGenerator_Idle").setCreativeTab(FoodMachineryRevolution.tabFMR);
		resourcePortal = new ResourcePortal(100000).setCreativeTab(FoodMachineryRevolution.tabFMR);
		teleporter = new Teleporter().setCreativeTab(FoodMachineryRevolution.tabFMR);

		cableEnergy = new Cable().setCreativeTab(FoodMachineryRevolution.tabFMR);
		fluidPipe = new FluidPipe().setCreativeTab(FoodMachineryRevolution.tabFMR);
	}

	static void addPlants(){
	}

	static void addMaterials(){
	}

	static void addMachines(){
	}
}
