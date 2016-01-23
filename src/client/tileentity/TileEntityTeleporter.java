package client.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import common.energy.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTeleporter extends TileEntityBase implements IEnergyHandler{

	EnergyStorage energyStorage = new EnergyStorage(getMaxStorage(),getMaxInput(),getMaxOutput());

	private int maxEnergyStorage = 1000000;
	public int comparatorOutput=0;

	public void updateEntity(){
		if(!worldObj.isRemote){
			for(int i=0; i<6; i++)
				this.transferEnergy(i);

			if(worldObj.getTotalWorldTime()%32==((xCoord^zCoord)&31))
			{
				int i = scaleStoredEnergyTo(15);
				if(i!=this.comparatorOutput){
					this.comparatorOutput=i;
					worldObj.func_147453_f(xCoord, yCoord, zCoord, getBlockType());
				}
			}
		}
	}

	public int scaleStoredEnergyTo(int scale){
		return (int)(scale*(energyStorage.getEnergyStored()/(float)energyStorage.getMaxEnergyStored()));
	}


	public void transferEnergy(int side){
		ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[side];
		TileEntity tileEntity = worldObj.getTileEntity(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ);
		if(tileEntity instanceof IEnergyReceiver)
			this.energyStorage.setEnergyStored(-((IEnergyReceiver)tileEntity).receiveEnergy(fd.getOpposite(), Math.min(getMaxOutput(), this.energyStorage.getEnergyStored()), false));
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket){
		energyStorage.writeToNBT(nbt);
	}

	@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket){
		energyStorage.readFromNBT(nbt);
	}

	public int getMaxStorage()
	{
		return this.maxEnergyStorage;
	}
	public int getMaxInput()
	{
		return (this.maxEnergyStorage * 2);
	}
	public int getMaxOutput()
	{
		return (this.maxEnergyStorage * 2);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from){
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
		int r = energyStorage.receiveEnergy(maxReceive, simulate);
		return r;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
		int r = energyStorage.extractEnergy(maxExtract, simulate);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return r;
	}

	@Override
	public int getEnergyStored(ForgeDirection from){
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from){
		return energyStorage.getMaxEnergyStored();
	}

	public int getEnergyStored(){
		return getEnergyStored(ForgeDirection.DOWN);
	}

	public int getMaxEnergyStored(){
		return getMaxEnergyStored(ForgeDirection.DOWN);
	}
}
