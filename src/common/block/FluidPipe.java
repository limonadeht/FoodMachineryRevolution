package common.block;

import java.util.List;

import client.tileentity.TileEntityFLuidPipe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FluidPipe extends Block{

	public FluidPipe(){
		super(Material.iron);
		this.setBlockName("fmr.blockCable.1");
	}

	public boolean isOpaqueCube(){
		 return false;
		 }

	 public boolean renderAsNormalBlock(){
		 return false;
		 }

	 public TileEntity createTileEntity(World world, int meta){
		 switch(meta){
		 case 0:
			 return new TileEntityFLuidPipe();
		 }
		 return null;
	 }

	 public boolean hasTileEntity(int meta){
		 return meta <= 1;
	 }

	 public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	    {
	        return false;
	    }

	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list){
		 list.add(new ItemStack(this, 1, 0));
	 }

	 @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	 public void addInfomation(ItemStack itemstack, EntityPlayer player, List list, boolean bool){
		 list.add("Fluid Pipes.");
	 }
}
