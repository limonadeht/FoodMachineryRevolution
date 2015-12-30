package common.item;

import java.util.List;

import common.FoodMachineryRevolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UpgradeFuel extends Item{

	public UpgradeFuel(){
		this.setUnlocalizedName("fmr.upgrade_fuel");
		this.setTextureName("fmr:upgrade");
		this.setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced){
		if(FoodMachineryRevolution.serverproxy.isShiftKeyDown()){
			list.add("§oThis item allows you to even better");
			list.add("§othe fuel efficiency of the machine!");
			list.add("--------------------------");
			list.add("§bFuel Efficiency§7: §62.0x");
		}else{
			list.add("§oPress §bLShift §7§ofor Info");
		}
		super.addInformation(itemstack, player, list, advanced);
	}
}
