package common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import server.ServerProxy;

@Mod(modid = FoodMachineryRevolution.MOD_ID, version = FoodMachineryRevolution.VERSION)
public class FoodMachineryRevolution {

	@Mod.Instance("Food Machinery Revolution")
	public static FoodMachineryRevolution Instance;
	public static final String MOD_ID = "Food Machinery Revolution";
	public static final String VERSION = "Alpha-1.0";

	@SidedProxy(clientSide = "client.ClientProxy", serverSide = "server.ServerProxy")
	public static ServerProxy serverproxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
	}

	@EventHandler
	public void Init(FMLPreInitializationEvent e){
	}

	public static CreativeTabs tabFMR = new CreativeTabs("FoodMachineryRevolution"){

	public Item getTabIconItem(){
		return Item.getItemFromBlock(Blocks.dragon_egg);
		}
	};
}
