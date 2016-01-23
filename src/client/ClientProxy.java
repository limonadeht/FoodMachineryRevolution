package client;

import org.lwjgl.input.Keyboard;

import client.render.ItemCableRenderer;
import client.render.ItemFluidPipeRenderer;
import client.render.ItemTeleporterRenderer;
import client.render.TileEntityCableRenderer;
import client.render.TileEntityFluidPipeRenderer;
import client.render.TileEntityTeleporterRenderer;
import client.tileentity.TileEntityCable;
import client.tileentity.TileEntityFLuidPipe;
import client.tileentity.TileEntityTeleporter;
import common.MaterialRegister;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import server.ServerProxy;

public class ClientProxy extends ServerProxy{

	@Override
	public boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}

	@Override
	public void registerRenderers(){
		ClientRegistry.registerTileEntity(TileEntityCable.class, "fmr.blockCable", TileEntityCableRenderer.instance);
		ClientRegistry.registerTileEntity(TileEntityFLuidPipe.class, "fmr.blockCable.1", TileEntityFluidPipeRenderer.instance);
		ClientRegistry.registerTileEntity(TileEntityTeleporter.class, "fmr.resourcePortal", TileEntityTeleporterRenderer.instance);

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MaterialRegister.cableEnergy), ItemCableRenderer.instance);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MaterialRegister.fluidPipe), ItemFluidPipeRenderer.instance);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MaterialRegister.teleporter), ItemTeleporterRenderer.instance);
	}
}
