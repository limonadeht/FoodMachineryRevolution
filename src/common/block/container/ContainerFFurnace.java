package common.block.container;

import client.tileentity.TileEntityFFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerFFurnace extends Container{

	private TileEntityFFurnace tileentity;

	public int lastburnTime;
	public int lastcurrentItemBurnTime;
	public int lastcookTime;


	public ContainerFFurnace(InventoryPlayer inventory,TileEntityFFurnace tileentity){
		this.tileentity = tileentity;

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
		int i;

		for(i = 0;i < 3;i++){
			for(int j = 0;j < 9;j++){
				this.addSlotToContainer(new Slot(inventory,j+i*9+9,8+j*18,84+i*18));
			}
		}

		for(i=0; i< 9;i++){
			this.addSlotToContainer(new Slot(inventory,i,8+i*18,142));
		}
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
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int par2) {
		ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (par2 != 1 && par2 != 0)
            {
                if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 3 && par2 < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(entityplayer, itemstack1);
        }

        return itemstack;
	}
}
