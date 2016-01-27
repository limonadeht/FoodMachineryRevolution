package client.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import common.MaterialRegister;
import common.block.DieselTank;
import common.energy.EnergyStorage;
import common.item.ItemDieselGenerator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityDiselGenerator extends TileEntityBase implements IEnergyHandler, IFluidHandler{

	private EnergyStorage energyStorage;
	private int energyGeneration;

	public DieselTank productTank = new DieselTank(8000);

	public TileEntityDiselGenerator(int energyGeneration, int energyTransfer, int energyCapacity){
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
	    if(this.productTank.getFluidAmount() > 0 && this.productTank.getFluidType() == MaterialRegister.fluidBioDiesel){
	      return energyGeneration;
	    }
	    return 0;
	}

	protected void generateEnergy(){
		int energyGeneration = getEnergyGeneration();

		int CurrentEnergyStorage = this.getEnergyStored();
		if (energyGeneration > 0 && CurrentEnergyStorage < this.getMaxEnergyStored()){
			this.productTank.setAmount(this.productTank.getFluidAmount() - 1);
			this.energyStorage.receiveEnergy(energyGeneration, false);
		}
	}

	public void transferEnergy(){
	    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
	    {
	      TileEntity tileEntity = getWorldObj().getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);
	      if (!(tileEntity instanceof TileEntityDiselGenerator)) {
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

		this.productTank = new DieselTank(8000);
		if(nbt.hasKey("productTank")){
		    this.productTank.readFromNBT(nbt.getCompoundTag("productTank"));
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket){
		nbt.setInteger("EnergyGeneration", this.energyGeneration);
		this.energyStorage.writeToNBT(nbt);

		NBTTagCompound tank = new NBTTagCompound();
		this.productTank.writeToNBT(tank);
		nbt.setTag("productTank", tank);
	}

	public static DieselTank loadTank(NBTTagCompound nbtRoot) {
	    int tankType = nbtRoot.getInteger("tankType");
	    tankType = MathHelper.clamp_int(tankType, 0, 1);
	    DieselTank ret;

	    ret = new DieselTank(8000);

	    if(nbtRoot.hasKey("productTank")) {
	        FluidStack fl = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbtRoot.getTag("productTank"));
	        ret.setFluid(fl);
	      }else{
	        ret.setFluid(null);
	      }
	      return ret;
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

	public NBTTagCompound getItemNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		productTank.writeToNBT(nbt);
		return nbt;
	}

	public IFluidTank getTank() {
		return productTank;
	}

	public void onBlockPlacedBy(EntityLivingBase placer, ItemStack stack) {
		NBTTagCompound itemTag = stack.getTagCompound();

		if (itemTag != null && itemTag.hasKey(ItemDieselGenerator.DISEL_TAG)) {
			productTank.readFromNBT(itemTag.getCompoundTag(ItemDieselGenerator.DISEL_TAG));
		}
	}

	/*IFluidHandler*/

	public static int getTankCapacity() {
		return FluidContainerRegistry.BUCKET_VOLUME * 1;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(resource == null){
			return null;
		}
		if(productTank.getFluidType() == resource.getFluid()){
			return productTank.drain(resource.amount, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.productTank.drain(maxDrain, doDrain);
	}

	//
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource == null || resource.getFluid() == null){
			return 0;
		}

		FluidStack current = this.productTank.getFluid();
		FluidStack resourceCopy = resource.copy();
		if (current != null && current.amount > 0 && !current.isFluidEqual(resourceCopy)){
			return 0;
		}

		int i = 0;
		int used = this.productTank.fill(resourceCopy, doFill);
		resourceCopy.amount -= used;
		i += used;

		return i;
	}


	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid != null && this.productTank.isEmpty() && this.productTank.getFluidType() == MaterialRegister.fluidBioDiesel;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{productTank.getInfo()};
	}
}
