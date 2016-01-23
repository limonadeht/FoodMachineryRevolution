package common.block;

import java.util.List;

import client.tileentity.TileEntityTeleporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Teleporter extends BlockContainer{

	public Teleporter(){
		super(Material.iron);
		this.setBlockName("fmr.teleporter");
	}

	public boolean isOpaqueCube(){
		 return false;
		 }

	public boolean renderAsNormalBlock(){
		return false;
	}

	public boolean hasTileEntity(int meta){
		 return meta <= 1;
	 }

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4){
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int x, int y, int z){
		return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)y + 0.75D, (double)(z + 1));
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int x, int y, int z){
		return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)y + 0.75D, (double)(z + 1));
	}

	 public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	    {
	        return false;
	    }

	 @SuppressWarnings("unchecked")
	public void getSubBlocks(Item item, CreativeTabs tab, List list){
		 list.add(new ItemStack(this, 1, 0));
	 }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta){
		switch(meta){
		 case 0:
			 return new TileEntityTeleporter();
		 }
		 return null;
	}
}
