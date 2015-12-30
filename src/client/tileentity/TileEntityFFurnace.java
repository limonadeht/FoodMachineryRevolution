package client.tileentity;

import common.block.FoodFurnace;
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
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFFurnace extends TileEntity implements ISidedInventory{

	private String FFurnace;

	public int burnTime;
	public int currentItemBurnTime;
	public int cookTime;

	private static final int[] slots_top = new int[]{0, 5, 6, 7};
	private static final int[] slots_bottom = new int[] {2, 1};
	private static final int[] slots_sides = new int[] {1};
	private static final int[] slots_upgrade = new int[]{8, 9};

	public ItemStack[] itemstacks = new ItemStack[10];

	@Override
	public void readFromNBT(NBTTagCompound par1)
	{
		super.readFromNBT(par1);

		NBTTagList nbttaglist = par1.getTagList("Items", 10);
		this.itemstacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.itemstacks.length)
			{
				this.itemstacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		 /*this.waterTank = new FluidTankUtils(4000);
			if (par1.hasKey("waterTank")) {
			    this.waterTank.readFromNBT(par1.getCompoundTag("waterTank"));
			}*/

		this.burnTime = par1.getShort("BurnTime");
		this.cookTime = par1.getShort("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.itemstacks[1]);

	}

	@Override
	public void writeToNBT(NBTTagCompound par1)
	{

		par1.setShort("BurnTime", (short)this.burnTime);
		par1.setShort("CookTime", (short)this.cookTime);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.itemstacks.length; ++i)
		{
			if (this.itemstacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.itemstacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		/*NBTTagCompound tank = new NBTTagCompound();
		this.waterTank.writeToNBT(tank);
		par1.setTag("waterTank", tank);*/

		par1.setTag("Items", nbttaglist);
		//super.writeToNBT(par1);
	}

	@Override
	public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
	}

	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.cookTime * par1 / 200;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if (this.currentItemBurnTime == 0)
		{
			this.currentItemBurnTime = 200;
		}

		return this.burnTime * par1 / this.currentItemBurnTime;
	}

	public boolean isBurning()
	{
		return this.burnTime > 0;
	}

	public void updateEntity()
	{
		boolean flag = this.burnTime > 0;
		boolean flag1 = false;

		if (this.burnTime > 0)
		{
			--this.burnTime;

			//this.waterTank.setAmount(this.waterTank.getFluidAmount() - 1);
		}

		if (!this.worldObj.isRemote)
		{
			if (this.burnTime == 0 && this.canSmelt())
			{
				this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.itemstacks[1]);

				if (this.burnTime > 0)
				{
					flag1 = true;

					if (this.itemstacks[1] != null)
					{
						--this.itemstacks[1].stackSize;

						if (this.itemstacks[1].stackSize == 0)
						{
							this.itemstacks[1] = this.itemstacks[1].getItem().getContainerItem(this.itemstacks[1]);
						}
					}
				}
			}
			/*if(flag != this.waterTank.getFluidAmount() < 0){
				flag1 = true;
			}*/

			if (this.isBurning() && this.canSmelt())
			{
				++this.cookTime;

				if (this.cookTime == 200)
				{

					this.cookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			}
			else
			{
				this.cookTime = 0;
			}

			if (flag != this.burnTime > 0)
			{
				flag1 = true;
				FoodFurnace.updateFoodFurnaceState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if (flag1)
		{
			this.markDirty();
		}
	}

	private boolean canSmelt()
	{
		if (this.itemstacks[0] == null /*|| this.waterTank.getFluidAmount() <= 0*/)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.itemstacks[0]);
			if (itemstack == null) return false;
			if (this.itemstacks[2] == null) return true;
			if (!this.itemstacks[2].isItemEqual(itemstack)) return false;
			int result = this.itemstacks[2].stackSize + itemstack.stackSize;
			return (result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
		}
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.itemstacks[0]);

			if (this.itemstacks[2] == null)
			{
				this.itemstacks[2] = itemstack.copy();
			}
			else if (this.itemstacks[2].isItemEqual(itemstack))
			{
				this.itemstacks[2].stackSize += itemstack.stackSize;
			}

			--this.itemstacks[0].stackSize;

			if (this.itemstacks[0].stackSize <= 0)
			{
				this.itemstacks[0] = null;
			}
		}
	}

	public static boolean isItemFuel(ItemStack itemstack)
	{
		return getItemBurnTime(itemstack) > 0;
	}

	public static int getItemBurnTime(ItemStack itemstack) {
		if (itemstack == null)
        {
            return 0;
        }
        else
        {
            Item item = itemstack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.wooden_slab)		 return 150;
                if (block.getMaterial() == Material.wood)return 300;
                if (block == Blocks.coal_block)			 return 16000;
            }

            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item == Items.stick) return 100;
            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
            if (item == Items.blaze_rod) return 2400;
            return GameRegistry.getFuelValue(itemstack);
        }
	}

	@Override
	public int getSizeInventory() {
		return this.itemstacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.itemstacks[par1];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (this.itemstacks[par1] != null)
		{
			ItemStack itemstack;

			if (this.itemstacks[par1].stackSize <= par2)
			{
				itemstack = this.itemstacks[par1];
				this.itemstacks[par1] = null;
				return itemstack;
			}
			else
			{
				itemstack = this.itemstacks[par1].splitStack(par2);

				if (this.itemstacks[par1].stackSize == 0)
				{
					this.itemstacks[par1] = null;
				}

				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.itemstacks[par1] != null)
		{
			ItemStack itemstack = this.itemstacks[par1];
			this.itemstacks[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack itemstack) {
		this.itemstacks[par1] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public void onInventoryChanged() {

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityplayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return par1 == 2 ? false : (par1 == 1 ? isItemFuel(itemstack) : true);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack itemstack, int par3) {
		return this.isItemValidForSlot(par1, itemstack);
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack itemstack, int par3) {
		return par3 != 0 || par1 != 1 || itemstack.getItem()== Items.bucket;
	}

	@Override
	public String getInventoryName() {
		 return this.hasCustomInventoryName() ? this.FFurnace : "Rice Cooker";
	}

	public boolean hasCustomInventoryName()
    {
        return this.FFurnace != null && this.FFurnace.length() > 0;
    }

	@Override
	public void openInventory() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void closeInventory() {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void setGuiDisplayName(String displayName) {
		this.FFurnace = displayName;

	}

	public int getFuel(ItemStack itemstack)
	{
		if(itemstack.getItem() == Items.lava_bucket)
		{
			return 1000;
		}

		return getItemBurnTime(itemstack)/2;
	}


	/*//water
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (resource == null) {
			return null;
		}
		if (waterTank.getFluidType() == resource.getFluid()) {
			return waterTank.drain(resource.amount, doDrain);
		}
		return null;
	}*/

	/*@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.waterTank.drain(maxDrain, doDrain);
	}*/

	//
	/*@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource == null || resource.getFluid() == null){
			return 0;
		}

		FluidStack current = this.waterTank.getFluid();
		FluidStack resourceCopy = resource.copy();
		if (current != null && current.amount > 0 && !current.isFluidEqual(resourceCopy)){
			return 0;
		}

		int i = 0;
		int used = this.waterTank.fill(resourceCopy, doFill);
		resourceCopy.amount -= used;
		i += used;

		return i;
	}*/


	/*@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid != null && this.waterTank.isEmpty();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{waterTank.getInfo()};
	}*/
}
