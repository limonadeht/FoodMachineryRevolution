package client.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import common.energy.EnergyStorage;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityWindGenerator extends TileEntityBase implements IEnergyHandler{

	private EnergyStorage energyStorage;
	private int energyGeneration;

	public TileEntityWindGenerator(int energyGeneration, int energyTransfer, int energyCapacity){
		this.energyGeneration = energyGeneration;
		this.energyStorage = new EnergyStorage(energyCapacity, energyTransfer);
	}

	public void updateEntity(){
		if(!this.worldObj.isRemote){
			generateEnergy();
			if(this.getEnergyStored() > 0){
				transferEnergy();
			}
		}
	}

	private int getEnergyGeneration(){
	    if(worldObj.getBlock(xCoord, yCoord+1, zCoord) == Blocks.water){
	      return energyGeneration;
	    }
	    if(this.worldObj.isThundering()){
	    	return energyGeneration;
	    }
	    return 0;
	}

	protected void generateEnergy(){
		int energyGeneration = getEnergyGeneration();
		if (energyGeneration > 0) {
			this.energyStorage.receiveEnergy(energyGeneration, false);
		}
	}

	public void transferEnergy(){
	    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
	    {
	      TileEntity tileEntity = getWorldObj().getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);
	      if (!(tileEntity instanceof TileEntityWindGenerator)) {
	        if ((tileEntity instanceof IEnergyReceiver)) {
	          if (this.energyStorage.getEnergyStored() > 0)
	          {
	            IEnergyReceiver receiver = (IEnergyReceiver)tileEntity;
	            this.energyStorage.sendEnergy(receiver, direction.getOpposite());
	          }
	        }
	      }
	    }
	}

	@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket){
		this.energyGeneration = nbt.getInteger("EnergyGeneration");
		this.energyStorage.readFromNBT(nbt);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket){
		nbt.setInteger("EnergyGeneration", this.energyGeneration);
		this.energyStorage.writeToNBT(nbt);
	}

	public boolean canConnectEnergy(ForgeDirection from){
		return true;
	}

	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
		return 0;
	}

	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
		return this.energyStorage.extractEnergy(this.energyStorage.getMaxExtract(), simulate);
	}

	public int getEnergyStored(ForgeDirection from){
		return this.energyStorage.getEnergyStored();
	}

	public int getMaxEnergyStored(ForgeDirection from){
		return this.energyStorage.getMaxEnergyStored();
	}

	public void setEnergyStored(int energyStored){
		this.energyStorage.setEnergyStored(energyStored);
	}

	public int getEnergyStored(){
		return getEnergyStored(ForgeDirection.DOWN);
	}

	public int getMaxEnergyStored(){
		return getMaxEnergyStored(ForgeDirection.DOWN);
	}

	public static class Wood extends TileEntityWindGenerator{

		public Wood(int energyGeneration, int energyTransfer, int energyCapacity){
			super(energyGeneration, energyTransfer, energyCapacity);
		}
	}
}
