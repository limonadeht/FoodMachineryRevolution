package common.block.container;

import client.tileentity.TileEntityHydroelectricGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerHydroGenerator extends Container{

	private TileEntityHydroelectricGenerator tileentity;

	public ContainerHydroGenerator(InventoryPlayer invPlayer, TileEntityHydroelectricGenerator tileentity){
		this.tileentity = tileentity;

		for (int i = 0; i < 3; i++) {
			for(int k = 0; k < 9; k++) {
				this.addSlotToContainer(new Slot(invPlayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_){
		return true;
	}
}
