package common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import handler.GuiHandler;
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
		System.out.println("*****************************************");
		System.out.println("* Food Machinery Revolution [Alpha-1.0] *");
		System.out.println("*          LOADED SUCCESSFULLY          *");
		System.out.println("*        Presents for you Have fun!     *");
		System.out.println("*****************************************");
	}

	@EventHandler
	public void Init(FMLPreInitializationEvent e){
		MaterialRegister.addBlocks();
		MaterialRegister.addItem();
		MaterialRegister.instance.load();

		NetworkRegistry.INSTANCE.registerGuiHandler(this.Instance, new GuiHandler());
	}

	public static CreativeTabs tabFMR = new CreativeTabs("FoodMachineryRevolution"){

	public Item getTabIconItem(){
		return Item.getItemFromBlock(Blocks.dragon_egg);
		}
	};
}
