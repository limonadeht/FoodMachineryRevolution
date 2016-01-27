package common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import handler.GuiHandler;
import net.minecraft.creativetab.CreativeTabs;
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
		MaterialRegister.instance.registOreDic(); //一部登録漏れが出るため、一番最初の方で優先的にLoad
		System.out.println("OreDictionaly Loaded Succeccfully.");
		System.out.println("*****************************************");
		System.out.println("* Food Machinery Revolution [Alpha-1.0] *");
		System.out.println("*          LOADED SUCCESSFULLY          *");
		System.out.println("*        Presents for you Have fun!     *");
		System.out.println("*****************************************");
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void Init(FMLPreInitializationEvent e){
		MaterialRegister.addBlocks();
		System.out.println("Loaded Blocks");

		MaterialRegister.addFoods();
		System.out.println("Loaded Foods");

		MaterialRegister.addFluid();
		System.out.println("Loaded Fluids.");

		MaterialRegister.addMachines();
		System.out.println("Loaded Machines");

		MaterialRegister.addItem();
		System.out.println("Loaded Items");

		MaterialRegister.addMaterials();
		System.out.println("Loaded Materials");

		MaterialRegister.addPlants();
		System.out.println("Loaded Plants");

		MaterialRegister.addTools();
		System.out.println("Loaded Tools");

		MaterialRegister.instance.load();

		serverproxy.registerRenderers();
		serverproxy.registerTileEntitys();

		NetworkRegistry.INSTANCE.registerGuiHandler(this.Instance, new GuiHandler());
	}

	public static CreativeTabs tabFMR = new CreativeTabs("FoodMachineryRevolution"){

	public Item getTabIconItem(){
		return MaterialRegister.upgrade_speed;
		}
	};
}
