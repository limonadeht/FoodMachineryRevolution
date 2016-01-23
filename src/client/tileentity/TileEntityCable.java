package client.tileentity;

import common.energy.EnergyBar;
import common.energy.EnergyNetwork;
import common.energy.IEnergy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCable extends TileEntity implements IEnergy{

	@SuppressWarnings("unused")
	private ForgeDirection lastRecevedDirection = ForgeDirection.UNKNOWN;
	private EnergyBar energyBar = new EnergyBar(100);

	public void updateTileEntity(){
		EnergyNetwork.disttributeEnergyToSurrounding(worldObj, xCoord, yCoord, zCoord, energyBar);
	}

	@Override
	public boolean canAddEnergyOnSide(ForgeDirection direction){
		return true;
	}

	@Override
	public boolean canConnect(ForgeDirection direction){
		return true;
	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared(){
		return Double.MAX_VALUE;
	}

	public AxisAlignedBB getRenderBoundingBox(){
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public EnergyBar getEnergyBar(){
		return energyBar;
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
		energyBar.writeToNBT(tag);
	}

	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		energyBar.readFromNBT(tag);
	}

	@Override
	public int getEnergyTransferRate()
	{
		return 100;
	}

	@Override
	public void SetLeastRecivedDirection(ForgeDirection direction)
	{
		this.lastRecevedDirection = direction;
	}
}
