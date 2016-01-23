package client.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityFLuidPipe extends TileEntity implements IFluidHandler, IFluidTank{

	@SuppressWarnings("unused")
	private ForgeDirection lastRecevedDirection = ForgeDirection.UNKNOWN;

	public void updateTileEntity(){

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

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public FluidStack getFluid() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getFluidAmount() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getCapacity() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public FluidTankInfo getInfo() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
