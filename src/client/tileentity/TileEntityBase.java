package client.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBase extends TileEntity{

	public void readFromNBT(NBTTagCompound nbtTagCompound)
	  {
	    super.readFromNBT(nbtTagCompound);
	    loadDataFromNBT(nbtTagCompound);
	  }

	  public void writeToNBT(NBTTagCompound nbtTagCompound)
	  {
	    super.writeToNBT(nbtTagCompound);
	    addDataToNBT(nbtTagCompound);
	  }

	  protected void addDataToNBT(NBTTagCompound nbtTagCompound) {}

	  protected void loadDataFromNBT(NBTTagCompound nbtTagCompound) {}

	  public Packet getDescriptionPacket()
	  {
	    NBTTagCompound nbtTagCompound = new NBTTagCompound();
	    addDataToNBT(nbtTagCompound);
	    return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbtTagCompound);
	  }

	  public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet)
	  {
	    NBTTagCompound nbtTagCompound = packet.func_148857_g();
	    loadDataFromNBT(nbtTagCompound);
	  }
}
