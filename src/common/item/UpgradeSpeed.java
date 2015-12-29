package common.item;

import java.util.List;

import common.FoodMachineryRevolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UpgradeSpeed extends Item{

	public UpgradeSpeed(){
		this.setUnlocalizedName("fmr.upgrade_speed");
		this.setTextureName("fmr:upgrade_speed.png");
	}



	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced){
		if(FoodMachineryRevolution.serverproxy.isShiftKeyDown()){
			list.add("§oThis item is that it is set in a particular machine,");
			list.add("§oyou get the effect of a speed increase!");
		}else{
			list.add("§oPress §bLShift §7§ofor Info");
		}
		super.addInformation(itemstack, player, list, advanced);
	}



	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int p7, float p8, float p9, float p10){
		return super.onItemUse(itemstack, player, world, x, y, z, p7, p8, p9, p10);
	}


}
