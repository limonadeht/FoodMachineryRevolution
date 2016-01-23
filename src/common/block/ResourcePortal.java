package common.block;

import java.util.List;

import client.tileentity.TileEntityResourcePortal;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ResourcePortal extends BlockContainer{

	public final int energyTransfer;
	public final int energyCapacity;

	public ResourcePortal(int energyCapacity){
		super(Material.iron);
		this.setBlockName("fmr.resourcePortal");
		this.setHardness(100.0F);
		this.setResistance(100.0F);

		this.energyCapacity = energyCapacity;
		this.energyTransfer = (energyCapacity * 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9){
		TileEntityResourcePortal tileentity = new TileEntityResourcePortal(energyTransfer, energyCapacity);
			if(world.isRemote){
				entityplayer.addChatComponentMessage(new ChatComponentText(
						EnumChatFormatting.AQUA + "EnergyStorage: " + tileentity.getEnergyStored() + "/" + tileentity.getMaxEnergyStored()));
			}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_){
		return new TileEntityResourcePortal(energyTransfer, energyCapacity);
	}

	public int getRenderType(){
		return -1;
	}

	public boolean isOpaqueCube(){
		 return false;
		 }

	 public boolean renderAsNormalBlock(){
		 return false;
		 }

	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list){
		list.add(new ItemStack(this, 1, 0));
		}
}
