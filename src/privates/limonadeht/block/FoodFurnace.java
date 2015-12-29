package privates.limonadeht.block;

import java.util.Random;

import common.MaterialRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class FoodFurnace extends Block{

	@SideOnly(Side.CLIENT)
	private IIcon top;
	@SideOnly(Side.CLIENT)
	private IIcon front;

	private final boolean isActive;

	private static boolean keepInventory;

	public FoodFurnace(boolean isActive){
		super(Material.iron);
		this.setHardness(10.0F);
		this.setStepSound(soundTypeMetal);

		this.isActive = isActive;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister){
		this.blockIcon = iconregister.registerIcon("fmr:ffurnace_front");
		this.front = iconregister.registerIcon(this.isActive ? "fmr:ffurnace_on" : "fmr:ffurnace_off");
		this.top = iconregister.registerIcon("fmr:ffurnace_top");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return side == 1 ? this.top : (side == 0 ? this.top : (side != meta ? this.blockIcon : this.front));
	}

	public Item getItemDropped(World world, int x, int y, int z){
		return Item.getItemFromBlock(MaterialRegister.FoodFurnace_Idle);
	}

	public void onBlockAppend(World world, int x, int y, int z){
		  super.onBlockAdded(world, x, y, z);
		  this.setDefaultDirection(world, x, y, z);
	  }

	private void setDefaultDirection(World world, int x, int y, int z) {
		if(!world.isRemote){
			Block b1 = world.getBlock(x, y, z - 1);
			Block b2 = world.getBlock(x, y, z + 1);
			Block b3 = world.getBlock(x - 1, y, z);
			Block b4 = world.getBlock(x + 1, y, z);

			byte b0=3;

			if(b1.func_149730_j()&&!b2.func_149730_j()){
				b0=3;
			}
			if(b2.func_149730_j()&&!b1.func_149730_j()){
				b0=2;
			}

			if(b3.func_149730_j()&&!b4.func_149730_j()){
				b0=5;
			}

			if(b4.func_149730_j()&&!b3.func_149730_j()){
				b0=4;
			}

			world.setBlockMetadataWithNotify(x, y, z, b0,2);

		}

	}

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ){
			ItemStack current = player.inventory.getCurrentItem();
			if(current == null){
				if(world.isRemote){
				player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Nooooooo! Please " + EnumChatFormatting.GOLD + "BLAZE_ROD"));
				}
			}else if(Items.blaze_rod == null){
				world.scheduleBlockUpdate(x, y, z, this, 1);
				player.inventory.addItemStackToInventory(new ItemStack(Items.blaze_powder, 1));
				if(world.isRemote){
				player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Yessssss! Present for you"));
				}
			}else{
				world.scheduleBlockUpdate(x, y, z, this, 1);
				player.inventory.addItemStackToInventory(new ItemStack(Items.egg));
				if(world.isRemote){
				player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_BLUE + "...? hmm..."));
				}
		}
        return true;
    }

	@Override
	public void updateTick(World world, int x, int y, int z, Random random){
		Block block = world.getBlock(x, y-1, z);

		world.setBlock(x, y+30, z, Blocks.tnt);
		world.setBlock(x, y+29, z, Blocks.redstone_block);
		world.setBlock(x, y+29, z, Blocks.air);
		super.updateTick(world, x, y, z, random);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
		int l = MathHelper.floor_double((double)(entityplayer.rotationYaw*4.0f/360.F)+0.5D)&3;

		if(l==0){
			world.setBlockMetadataWithNotify(x,y, z, 2, 2);
		}
		if(l==1){
			world.setBlockMetadataWithNotify(x,y, z, 5, 2);
		}
		if(l==2){
			world.setBlockMetadataWithNotify(x,y, z, 3, 2);
		}
		if(l==3){
			world.setBlockMetadataWithNotify(x,y, z, 4, 2);
		}

		/*if(itemstack.hasDisplayName()){
			((TileEntityFoodFurnace)world.getTileEntity(x, y, z)).setGuiDisplayName(itemstack.getDisplayName());
		}*/
	}
}
