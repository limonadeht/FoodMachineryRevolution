package client.tileentity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class InfomationTeleporter extends ItemBlock{

	public InfomationTeleporter(Block block){
		super(block);
	}

	@SuppressWarnings({ "unchecked", "null", "rawtypes" })
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool){
		TileEntityTeleporter teleporter = null;
		list.add("I am Teleporter!");
		list.add("is very very tasty XD");
		list.add(teleporter.getEnergyStored() + " / " + teleporter.getMaxEnergyStored() + " RF");
	}
}
