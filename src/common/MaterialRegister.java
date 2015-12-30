package common;

import common.block.FoodFurnace;
import common.item.UpgradeSpeed;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/*
 * https://github.com/defeatedcrow/AppleMilkTea2_1.7.10/blob/master/java/mods/defeatedcrow/common/MaterialRegister.java#L942
 * 参考までに、出来るだけスッキリ綺麗なコードに
 */
public class MaterialRegister {

	public static MaterialRegister instance = new MaterialRegister();

	public static Item upgrade_speed;

	public static Block FoodFurnace_Active;
	public static Block FoodFurnace_Idle;

	private MaterialRegister(){
	}

	public void load(){

		//機械
		GameRegistry.registerBlock(FoodFurnace_Idle, "fmr.FoodFurnace_Idle");
		GameRegistry.registerBlock(FoodFurnace_Active, "fmr.FoodFurnace_Active");

		//機械アップグレード
		GameRegistry.registerItem(upgrade_speed, "fmr.upgrade_speed");
	}

	public void addFluid(){
	}

	static void addItem(){
		upgrade_speed = new UpgradeSpeed().setCreativeTab(FoodMachineryRevolution.tabFMR);
	}

	static void addTools(){
	}

	static void addFoods(){
	}

	static void addBlocks(){
		FoodFurnace_Idle = new FoodFurnace(false).setBlockName("fmr.FoodFurnace_Idle")
				.setCreativeTab(FoodMachineryRevolution.tabFMR);

		FoodFurnace_Active = new FoodFurnace(true).setBlockName("fmr.FoodFurnace_Active")
				.setCreativeTab(FoodMachineryRevolution.tabFMR);
	}

	static void addPlants(){
	}

	static void addMaterials(){
	}

	static void addMachines(){
	}
}
