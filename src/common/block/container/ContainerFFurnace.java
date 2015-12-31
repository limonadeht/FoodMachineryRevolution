package common.block.container;

import client.tileentity.TileEntityFFurnace;
import common.MaterialRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerFFurnace extends Container{

	private TileEntityFFurnace tileentity;

	public int lastburnTime;
	public int lastcurrentItemBurnTime;
	public int lastcookTime;
	
	static final int sourceSize       = 4;
	static final int productSize      = 1;
	static final int lavaBucketSize   = 1;
	static final int waterBucketSize  = 1;
	static final int emptyBucketSize  = 1;
	static final int sUpgradeSize     = 1;
	static final int fUpgradeSize     = 1;
	static final int inventorySize    = 27;
	static final int hotbarSize       = 9;
 
	static final int sourceIndex      = 0;
	static final int productIndex     = sourceIndex      + sourceSize;
	static final int lavaBucketIndex  = productIndex     + productSize;
	static final int waterBucketIndex = lavaBucketIndex  + lavaBucketSize;
	static final int emptyBucketIndex = waterBucketIndex + waterBucketSize;
	static final int sUpgradeIndex    = emptyBucketIndex + emptyBucketSize;
	static final int fUpgradeIndex    = sUpgradeIndex    + sUpgradeSize;
	static final int inventoryIndex   = fUpgradeIndex    + fUpgradeSize;
	static final int hotbarIndex      = inventoryIndex   + inventorySize;


	public ContainerFFurnace(InventoryPlayer inventory,TileEntityFFurnace tileentity){
		this.tileentity = tileentity;

		//Add source slots
		for(int i = 0; i < sourceSize; ++i) {
			this.addSlotToContainer(new Slot(tileentity, sourceIndex + i, 18 + (23 * i), 21));
		}
		
		//Add product slot
		this.addSlotToContainer(new Slot(tileentity, productIndex, 53,54));
		
		//Add lavaBucket slot
		this.addSlotToContainer(new Slot(tileentity, lavaBucketIndex, 119, 8));
		
		//Add waterBucket slot
		this.addSlotToContainer(new Slot(tileentity, waterBucketIndex, 119, 27));
		
		//Add emptyBucket slot
		this.addSlotToContainer(new Slot(tileentity, emptyBucketIndex, 119, 59));
		
		//Add speedUpgrade slot
		this.addSlotToContainer(new Slot(tileentity, sUpgradeIndex, 136,59));
		
		//Add fuelUpgrade slot
		this.addSlotToContainer(new Slot(tileentity, fUpgradeIndex, 153,59));

		//Add inventory slots
		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + inventoryIndex, 8 + j * 18, 84 + i * 18));
			}
		}
		
		//Add hotBer slots
		for(int i = 0; i < 9; ++i){
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
		
		/*
		//材料スロット
		this.addSlotToContainer(new Slot(this.tileentity,0, 18,21));
		this.addSlotToContainer(new Slot(this.tileentity,5, 41,21));
		this.addSlotToContainer(new Slot(this.tileentity,6, 64,21));
		this.addSlotToContainer(new Slot(this.tileentity,7, 87,21));
		
		//アップグレードスロット
		this.addSlotToContainer(new Slot(this.tileentity,8, 136,59));
		this.addSlotToContainer(new Slot(this.tileentity,9, 153,59));
		
		//完成品スロット
		this.addSlotToContainer(new Slot(this.tileentity,1, 86,56));
		this.addSlotToContainer(new SlotFurnace(inventory.player,this.tileentity ,2, 53,54));
		*/
	}

	public void addCrafttingToCrafters(ICrafting icrafting){
		super.addCraftingToCrafters(icrafting);
		icrafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime);
		icrafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime);
		icrafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
	}

	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0;i < this.crafters.size();++i){
			ICrafting icrafting = (ICrafting)this.crafters.get(i);

			if(this.lastcookTime != this.tileentity.cookTime){
				icrafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime);
			}
			if(this.lastburnTime != this.tileentity.cookTime){
				icrafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime);
			}
			if(this.lastcurrentItemBurnTime != this.tileentity.cookTime){
				icrafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
			}
		}

		this.lastcookTime = this.tileentity.cookTime;
		this.lastburnTime = this.tileentity.burnTime;
		this.lastcurrentItemBurnTime = this.tileentity.currentItemBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2){
		if (par1 == 0)
		{
			this.tileentity.cookTime = par2;
		}

		if (par1 == 1)
		{
			this.tileentity.burnTime = par2;
		}

		if (par1 == 2)
		{
			this.tileentity.currentItemBurnTime = par2;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.tileentity.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int clickedIndex) {
		
		Slot slot = (Slot)this.inventorySlots.get(clickedIndex);
		if(slot == null) {
			return null;
		}
 
		if(slot.getHasStack() == false) {
			return null;
		}
		
		ItemStack itemStack = slot.getStack();
		ItemStack itemStackOrg = slot.getStack().copy();
		
		//source slots items transfer to inventory or hotBer
		if(sourceIndex <= clickedIndex && clickedIndex <= sourceIndex + sourceSize) {
			if (!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize + hotbarSize, false)) {
				return null;
			}
			slot.onSlotChange(itemStack, itemStackOrg);
		}
		//product slots items transfer to inventory or hotBer
		else if(productIndex <= clickedIndex && clickedIndex < productIndex + productSize) {
			if (!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize + hotbarSize, false)) {
				return null;
			}
			slot.onSlotChange(itemStack, itemStackOrg);
		}
		//lavaBucket slots items transfer to inventory or hotBer
		else if(lavaBucketIndex <= clickedIndex && clickedIndex < lavaBucketIndex + lavaBucketSize) {
			if (!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize + hotbarSize, false)) {
				return null;
			}
			slot.onSlotChange(itemStack, itemStackOrg);
		}
		//waterBucket slots items transfer to inventory or hotBer
		else if(waterBucketIndex <= clickedIndex && clickedIndex < waterBucketIndex + waterBucketSize) {
			if (!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize + hotbarSize, false)) {
				return null;
			}
			slot.onSlotChange(itemStack, itemStackOrg);
		}
		//emptyBucket slots items transfer to inventory or hotBer
		else if(emptyBucketIndex <= clickedIndex && clickedIndex < emptyBucketIndex + emptyBucketSize) {
			if (!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize + hotbarSize, false)) {
				return null;
			}
			slot.onSlotChange(itemStack, itemStackOrg);
		}
		//speedUpgrade slots items transfer to inventory or hotBer
		else if(sUpgradeIndex <= clickedIndex && clickedIndex < sUpgradeIndex + sUpgradeSize) {
			if (!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize + hotbarSize, false)) {
				return null;
			}
			slot.onSlotChange(itemStack, itemStackOrg);
		}
		//fuelUpgrade slots items transfer to inventory or hotBer
		else if(fUpgradeIndex <= clickedIndex && clickedIndex < fUpgradeIndex + fUpgradeSize) {
			if (!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize + hotbarSize, false)) {
				return null;
			}
			slot.onSlotChange(itemStack, itemStackOrg);
		}
		//inventory slots items transfer to match any slots
		else if(inventoryIndex <= clickedIndex && clickedIndex < inventoryIndex + inventorySize) {
			if(isSourceItem(itemStack)) {
				if (!this.mergeItemStack(itemStack, sourceIndex, sourceIndex + sourceSize, false)) {
					return null;
				}
			}
			else if(isLavaBucket(itemStack)){
				if (!this.mergeItemStack(itemStack, lavaBucketIndex, lavaBucketIndex + lavaBucketSize, false)) {
					return null;
				}
			}
			else if(isWaterBucket(itemStack)){
				if (!this.mergeItemStack(itemStack, waterBucketIndex, waterBucketIndex + waterBucketSize, false)) {
					return null;
				}
			}
			else if(isSpeedUpgrade(itemStack)){
				if (!this.mergeItemStack(itemStack, sUpgradeIndex, sUpgradeIndex + sUpgradeSize, false)) {
					return null;
				}
			}
			else if(isFuelUpgrade(itemStack)){
				if (!this.mergeItemStack(itemStack, fUpgradeIndex, fUpgradeIndex + fUpgradeSize, false)) {
					return null;
				}
			}
			else {
				if (!this.mergeItemStack(itemStack, hotbarIndex, hotbarIndex + hotbarSize, false)) {
					return null;
				}
			}
		}
		else if(hotbarIndex <= clickedIndex && clickedIndex < hotbarIndex + hotbarSize) {
			if(isSourceItem(itemStack)) {
				if (!this.mergeItemStack(itemStack, sourceIndex, sourceIndex + sourceSize, false)) {
					return null;
				}
			}
			else if(isLavaBucket(itemStack)){
				if (!this.mergeItemStack(itemStack, lavaBucketIndex, lavaBucketIndex + lavaBucketSize, false)) {
					return null;
				}
			}
			else if(isWaterBucket(itemStack)){
				if (!this.mergeItemStack(itemStack, waterBucketIndex, waterBucketIndex + waterBucketSize, false)) {
					return null;
				}
			}
			else if(isSpeedUpgrade(itemStack)){
				if (!this.mergeItemStack(itemStack, sUpgradeIndex, sUpgradeIndex + sUpgradeSize, false)) {
					return null;
				}
			}
			else if(isFuelUpgrade(itemStack)){
				if (!this.mergeItemStack(itemStack, fUpgradeIndex, fUpgradeIndex + fUpgradeSize, false)) {
					return null;
				}
			}
			else {
				if (!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize, false)) {
					return null;
				}
			}
		}
		
		if (itemStack.stackSize == 0) {
			slot.putStack((ItemStack)null);
		}
		else {
			slot.onSlotChanged();
		}
		
		if (itemStack.stackSize == itemStackOrg.stackSize) {
			return null;
		}
		
		slot.onPickupFromSlot(entityplayer, itemStack);
		
		return itemStackOrg; 
	}
	
	public boolean isSourceItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if(item == Items.beef)return true;
		return false;
	}
	
	public boolean isLavaBucket(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if(item == Items.lava_bucket)return true;
		return false;
	}
	
	public boolean isWaterBucket(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if(item == Items.water_bucket)return true;
		return false;
	}
	
	public boolean isSpeedUpgrade(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if(item == MaterialRegister.upgrade_speed)return true;
		return false;
	}
	
	public boolean isFuelUpgrade(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if(item == MaterialRegister.upgrade_fuel)return true;
		return false;
	}
}
