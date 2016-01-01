package client.tileentity;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import common.block.ThermalGenerator;
import common.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitytGenerator extends TileEntity implements ISidedInventory, IEnergyProvider{

	private String TGenerator;

	private EnergyStorage energyStorage;
	private int energyGeneration;

	public int burnTime = 1;
	public int burnTimeRemaining = 0;
	private int burnSpeed = 6;
	public boolean isBurning = false;
	public boolean isBurningCach = false;
	private int EPBT = 14;
	private int tick;

	private static final int[] slots_center = new int[] {0};

	public ItemStack[] itemstacks = new ItemStack[1];

	public TileEntitytGenerator(){
		this(0,0,0);
	}

	public TileEntitytGenerator(int energyGeneration, int energyTransfer, int energyCapacity){
		this.energyGeneration = energyGeneration;
		this.energyStorage = new EnergyStorage(energyCapacity, energyTransfer);
	}

	public void updateEntity()
	{
		if(worldObj.isRemote) return;

		this.isBurning = this.burnTimeRemaining > 0 && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored();

		if(this.burnTimeRemaining > 0 && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()){
			this.burnTimeRemaining -= burnSpeed;
			ThermalGenerator.updateFoodFurnaceState(this.burnTimeRemaining > 0, worldObj, burnTime, burnSpeed, EPBT);
			energyStorage.setEnergyStored(energyStorage.getEnergyStored() + Math.min(burnSpeed * EPBT, energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored()));
		}else if(this.burnTimeRemaining <= 0) tryRefuel();

		if ((energyStorage.getEnergyStored() > 0)) {
			for (int i = 0; i < 6; i++){
				TileEntity tile = worldObj.getTileEntity(xCoord + ForgeDirection.getOrientation(i).offsetX, yCoord + ForgeDirection.getOrientation(i).offsetY, zCoord + ForgeDirection.getOrientation(i).offsetZ);
				if (tile != null && tile instanceof IEnergyReceiver) {
					energyStorage.extractEnergy(((IEnergyReceiver)tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), energyStorage.extractEnergy(energyStorage.getMaxExtract(), true), false), false);
				}
			}
		}
		tick++;
	}

	public void tryRefuel() {
		if (burnTimeRemaining > 0 || energyStorage.getEnergyStored() >= energyStorage.getMaxEnergyStored()) return;
		if (itemstacks[0] != null && itemstacks[0].stackSize > 0) {
			int itemBurnTime = getItemBurnTime(itemstacks[0]);

			if (itemBurnTime > 0) {
				--itemstacks[0].stackSize;
				if (this.itemstacks[0].stackSize == 0)
				{
					this.itemstacks[0] = itemstacks[0].getItem().getContainerItem(itemstacks[0]);
				}
				burnTime = itemBurnTime;
				burnTimeRemaining = itemBurnTime;
			}
		}
	}

	public static int getItemBurnTime(ItemStack stack) {
		if (stack == null) {
			return 0;
		} else {
			Item item = stack.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.wooden_slab) {
					return 150;
				}

				if (block.getMaterial() == Material.wood) {
					return 300;
				}

				if (block == Blocks.coal_block) {
					return 16000;
				}
			}

			if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item == Items.stick) return 100;
			if (item == Items.coal) return 1600;
			if (item == Items.lava_bucket) return 20000;
			if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
			if (item == Items.blaze_rod) return 2400;
			return GameRegistry.getFuelValue(stack);
		}
	}

	@Override
	public int getSizeInventory() {
		return itemstacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return itemstacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int count) {
		ItemStack itemstack = getStackInSlot(i);

		if (itemstack != null) {
			if (itemstack.stackSize <= count) {
				setInventorySlotContents(i, null);
			} else {
				itemstack = itemstack.splitStack(count);
				if (itemstack.stackSize == 0) {
					setInventorySlotContents(i, null);
				}
			}
		}
		return itemstack;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		return this.burnSpeed * par1 / this.burnTimeRemaining;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack item = getStackInSlot(i);
		if (item != null) setInventorySlotContents(i, null);
		return item;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		itemstacks[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return "";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		if (worldObj == null)
		{
			return true;
		}
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.4) < 64;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[1];
	}

	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) {
		return true;
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) {
		return getItemBurnTime(var2) == 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagCompound[] tag = new NBTTagCompound[itemstacks.length];

		for (int i = 0; i < itemstacks.length; i++) {
			tag[i] = new NBTTagCompound();

			if (itemstacks[i] != null) {
				tag[i] = itemstacks[i].writeToNBT(tag[i]);
			}

			compound.setTag("Item" + i, tag[i]);
		}

		compound.setInteger("BurnTime", burnTime);
		compound.setInteger("BurnTimeRemaining", burnTimeRemaining);
		energyStorage.writeToNBT(compound);

		//super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagCompound[] tag = new NBTTagCompound[itemstacks.length];

		for (int i = 0; i < itemstacks.length; i++) {
			tag[i] = compound.getCompoundTag("Item" + i);
			itemstacks[i] = ItemStack.loadItemStackFromNBT(tag[i]);
		}

		burnTime = compound.getInteger("BurnTime");
		burnTimeRemaining = compound.getInteger("BurnTimeRemaining");
		energyStorage.readFromNBT(compound);

		super.readFromNBT(compound);
	}

	/*private void detectAndSentChanges(boolean sendAnyway){
		if (isBurning != isBurningCach || sendAnyway) isBurningCach = (Boolean) sendObjectToClient(6, 0, isBurning);
	}*/

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeToNBT(tagCompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energyStorage.getMaxEnergyStored();
	}
}
