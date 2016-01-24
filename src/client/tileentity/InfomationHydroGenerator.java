package client.tileentity;

import java.util.List;

import common.block.HydroelectricGenerator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class InfomationHydroGenerator extends ItemBlock{

	public InfomationHydroGenerator(Block block){
		super(block);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool){
		HydroelectricGenerator generator = (HydroelectricGenerator)this.field_150939_a;

		list.add("Water Power!");
		list.add(generator.getEnergyStored(itemStack) + " / " + generator.getEnergyCapacity() + " RF");
	}
}
