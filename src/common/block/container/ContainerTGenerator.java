package common.block.container;

import client.tileentity.TileEntitytGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerTGenerator extends Container{

	private TileEntitytGenerator tileentity;

	private int energyCach = -1;
	private int burnCach = -1;
	private int burnRemainingCach = -1;

	public ContainerTGenerator(InventoryPlayer invPlayer, TileEntitytGenerator entity){
		this.tileentity = entity;

		this.addSlotToContainer(new Slot(entity, 0, 80, 46));

		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(invPlayer, j+i*9+9,8+j*18,84+i*18));
			}
		}

		for(int i = 0; i < 9; ++i){
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}

	public void addCrafttingToCrafters(ICrafting icrafting){
		super.addCraftingToCrafters(icrafting);
		icrafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTimeRemaining);
		//icrafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
	}

	public void detectAndSendChanges(){
		super.detectAndSendChanges();
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2){
		if (par1 == 1)
		{
			this.tileentity.burnTimeRemaining = par2;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.tileentity.isUseableByPlayer(entityplayer);
	}


}
