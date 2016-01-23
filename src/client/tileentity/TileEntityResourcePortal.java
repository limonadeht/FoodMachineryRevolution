package client.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import common.energy.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityResourcePortal extends TileEntity implements IEnergyHandler, IEnergyReceiver{

	private EnergyStorage energyStorage;
	private int receveEnergyAmount = 1000;

	public TileEntityResourcePortal(int energyTransfer, int energyCapacity){
		this.energyStorage = new EnergyStorage(energyCapacity, energyTransfer);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from){
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
		return receveEnergyAmount;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from){
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from){
		return energyStorage.getMaxEnergyStored();
	}

	//独自Method
	public int getEnergyStored(){
		return getEnergyStored(ForgeDirection.DOWN);
	}

	public int getMaxEnergyStored(){
		return getMaxEnergyStored(ForgeDirection.DOWN);
	}

	public Packet getDesciptionPacket(){
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
	}

	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet){
		readFromNBT(packet.func_148857_g());
	}

	public void wrightToMBT(NBTTagCompound tag){
		super.writeToNBT(tag);
	}

	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
	}
}
