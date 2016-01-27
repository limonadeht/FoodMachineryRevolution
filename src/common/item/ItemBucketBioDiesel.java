package common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class ItemBucketBioDiesel extends ItemBucket{

	public ItemBucketBioDiesel(Block block){
		super(block);
		this.setContainerItem(Items.bucket);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("fmr:bucketBuiDiesel");
	}
}
