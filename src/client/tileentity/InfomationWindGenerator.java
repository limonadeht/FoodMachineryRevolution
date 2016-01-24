package client.tileentity;

import java.util.List;

import common.block.WindGenerator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class InfomationWindGenerator extends ItemBlock{

	public InfomationWindGenerator(Block block){
		super(block);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool){
		WindGenerator generator = (WindGenerator)this.field_150939_a;

		list.add("Wind Power!");
		list.add(generator.getEnergyStored(itemStack) + " / " + generator.getEnergyCapacity() + " RF");
	}
}
