package common.item;

import java.util.List;

import common.FoodMachineryRevolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

public class VoidBucketSteel extends Item{

	public VoidBucketSteel(){
		this.setUnlocalizedName("fmr.voidBucSteel");
		this.setTextureName("fmr:bucket_steel_empty");
		this.setMaxDamage(16);
		this.setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean advanced){
		if(FoodMachineryRevolution.serverproxy.isShiftKeyDown()){
			int dmg = this.getDamage(itemstack);
			int dmgfor = 16 - dmg;

			list.add("§oBlock Remover.");
			list.add("--------------------------");
			list.add("§aDurability§7: " + dmgfor + "/" + this.getMaxDamage(itemstack));
		}else{
			list.add("§oPress §bLShift §7§ofor Info");
		}
		super.addInformation(itemstack, player, list, advanced);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int p7, float p8, float p9, float p10){
		if(p7 != 1){
			return false;
		}else{
			if(player.canPlayerEdit(x, y, z, p7, itemstack) || world.getBlock(x, y, z) instanceof IFluidBlock){
				world.setBlockToAir(x, y, z);
				itemstack.damageItem(1, player);
				if(world.isRemote){
					player.addChatComponentMessage(new ChatComponentText("§aRemoved Successfull."));
				}
			}else{
				if(world.isRemote){
					player.addChatComponentMessage(new ChatComponentText("§4Remove Failed"));
				}
				return false;
			}
		}
		return super.onItemUse(itemstack, player, world, x, y, z, p7, p8, p9, p10);
	}
}
