package handler;

import client.gui.GuiFCraftTable;
import client.gui.GuiFFurnace;
import client.tileentity.TileEntityFFurnace;
import common.MaterialRegister;
import common.block.container.ContainerFCraftingTable;
import common.block.container.ContainerFFurnace;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		if(entity instanceof TileEntityFFurnace){
			return new ContainerFFurnace(player.inventory, (TileEntityFFurnace)entity);
		}

		if(ID == 1){
			return ID == 1 && world.getBlock(x, y, z) == MaterialRegister.foodCraftTable ? new ContainerFCraftingTable(player.inventory, world, x, y, z) : null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		if(entity instanceof TileEntityFFurnace){
			return new GuiFFurnace(player.inventory,(TileEntityFFurnace)entity);
		}

		if(ID == 1){
			return ID == 1 && world.getBlock(x, y, z) == MaterialRegister.foodCraftTable ? new GuiFCraftTable(player.inventory, world, x, y, z) : null;
		}
		return null;
	}
}
