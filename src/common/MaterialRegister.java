package common;

import client.tileentity.InfomationSolar;
import client.tileentity.TileEntitySolarPanel;
import common.block.BlockOfSteel;
import common.block.FoodCraftTable;
import common.block.FoodFurnace;
import common.block.SolarPanel;
import common.food.Cabbage;
import common.food.Meat;
import common.food.RawMeat;
import common.food.Tomato;
import common.item.DishEmpty;
import common.item.IngotCopper;
import common.item.IngotSteel;
import common.item.IngotTin;
import common.item.UpgradeFuel;
import common.item.UpgradeSpeed;
import common.item.VoidBucketSteel;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/*
 * https://github.com/defeatedcrow/AppleMilkTea2_1.7.10/blob/master/java/mods/defeatedcrow/common/MaterialRegister.java#L942
 * 参考までに、出来るだけスッキリ綺麗なコードに
 */
public class MaterialRegister {

	public static MaterialRegister instance = new MaterialRegister();

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

	public static Item ingotSteel;
	public static Item ingotCopper;
	public static Item ingotTin;

	public static Item cabbage;
	public static Item tomato;
	public static Item rawMeat;
	public static Item meat;

	public static Item voidBucketSteel;
	public static Item dishEmpty;

	public static Item upgrade_speed;
	public static Item upgrade_fuel;

	public static Block FoodFurnace_Active;
	public static Block FoodFurnace_Idle;
	public static Block foodCraftTable;

	public static Block blockOfSteel;

	private MaterialRegister(){
	}

	public void registOreDic(){
		OreDictionary.registerOre("fmrUpgrades", new ItemStack(upgrade_fuel));
		OreDictionary.registerOre("fmrUpgrades", new ItemStack(upgrade_speed));

		OreDictionary.registerOre("dishEmpty", new ItemStack(dishEmpty));

		OreDictionary.registerOre("ingotSteel", new ItemStack(ingotSteel));
		OreDictionary.registerOre("ingotCopper", new ItemStack(ingotCopper));
		OreDictionary.registerOre("ingotTin", new ItemStack(ingotTin));

		OreDictionary.registerOre("fmrFoodFurnace", FoodFurnace_Active);
		OreDictionary.registerOre("fmrFoodFurnace", FoodFurnace_Idle);
	}

	public void load(){
		//アイテム
		GameRegistry.registerItem(voidBucketSteel, "fmr.voidBucSteel");
		GameRegistry.registerItem(dishEmpty, "fmr.dishEmpty");

		//食べ物素材
		GameRegistry.registerItem(cabbage, "fmr.cabbage");
		GameRegistry.registerItem(tomato, "fmr.tomato");
		GameRegistry.registerItem(rawMeat, "fmr.rawMeat");
		GameRegistry.registerItem(meat, "fmr.meat");

		//インゴット
		GameRegistry.registerItem(ingotSteel, "fmr.ingotSteel");
		GameRegistry.registerItem(ingotCopper, "fmr.ingotCopper");
		GameRegistry.registerItem(ingotTin, "fmr.ingotTin");

		//機械
		GameRegistry.registerBlock(FoodFurnace_Idle, "fmr.FoodFurnace_Idle");
		GameRegistry.registerBlock(FoodFurnace_Active, "fmr.FoodFurnace_Active");

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
		ingotSteel = new IngotSteel().setCreativeTab(FoodMachineryRevolution.tabFMR);
		ingotCopper = new IngotCopper().setCreativeTab(FoodMachineryRevolution.tabFMR);
		ingotTin = new IngotTin().setCreativeTab(FoodMachineryRevolution.tabFMR);
		cabbage = new Cabbage().setCreativeTab(FoodMachineryRevolution.tabFMR);
		tomato = new Tomato().setCreativeTab(FoodMachineryRevolution.tabFMR);
		rawMeat = new RawMeat().setCreativeTab(FoodMachineryRevolution.tabFMR);
		meat = new Meat().setCreativeTab(FoodMachineryRevolution.tabFMR);
		dishEmpty = new DishEmpty().setCreativeTab(FoodMachineryRevolution.tabFMR);
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
	}

	static void addPlants(){
	}

	static void addMaterials(){
	}

	static void addMachines(){
	}
}
