package common.item;

import buildcraft.api.tools.IToolWrench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class Wrench extends Item implements IToolWrench{

	public Wrench(){
		super();

		this.setFull3D();
		this.setMaxStackSize(1);
		this.setHarvestLevel("wrench", 0);

		this.setUnlocalizedName("fmr.wrench");
	}

	@Override
	public boolean canWrench(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void wrenchUsed(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
