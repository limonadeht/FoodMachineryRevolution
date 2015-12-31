package common.block;

import java.util.Random;

import client.tileentity.TileEntityFFurnace;
import common.FoodMachineryRevolution;
import common.MaterialRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class FoodFurnace extends BlockContainer{

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
	
	public boolean isMultiBlock(World world, int x, int y, int z){
		boolean north = false;
		boolean south = false;
		boolean east = false;
		boolean west = false;
		
		for(int direction = 0; direction <= 3; direction++){
			if(direction == 0){
				PARENT:
				for(int height = y - 1; height <= y + 1; height++){
					for(int widthX = x; widthX <= x + 2; widthX++){
						for(int widthZ = z - 1; widthZ <= z + 1; widthZ++){
							if(height == y && widthX == x && widthZ == z){}
							else if(world.getBlock(widthX, height, widthZ) != MaterialRegister.blockOfSteel){west = false; break PARENT;}
							west = true;
						}
					}
				}
			}else if(direction == 1){
				PARENT:
				for(int height = y - 1; height <= y + 1; height++){
					for(int widthX = x - 2; widthX <= x; widthX++){
						for(int widthZ = z - 1; widthZ <= z + 1; widthZ++){
							if(height == y && widthX == x && widthZ == z){}
							else if(world.getBlock(widthX, height, widthZ) != MaterialRegister.blockOfSteel){east = false; break PARENT;}
							east = true;
						}
					}
				}
			}else if(direction == 2){
				PARENT:
				for(int height = y - 1; height <= y + 1; height++){
					for(int widthX = x - 1; widthX <= x + 1; widthX++){
						for(int widthZ = z; widthZ <= z + 2; widthZ++){
							if(height == y && widthX == x && widthZ == z){}
							else if(world.getBlock(widthX, height, widthZ) != MaterialRegister.blockOfSteel){north = false; break PARENT;}
							north = true;
						}
					}
				}
			}else{
				PARENT:
				for(int height = y - 1; height <= y + 1; height++){
					for(int widthX = x - 1; widthX <= x + 1; widthX++){
						for(int widthZ = z - 2; widthZ <= z; widthZ++){
							if(height == y && widthX == x && widthZ == z){}
							else if(world.getBlock(widthX, height, widthZ) != MaterialRegister.blockOfSteel){south = false; break PARENT;}
							south = true;
						}
					}
				}
			}
		}
		if(south)return true;
		else if(north)return true;
		else if(east)return true;
		else if(west)return true;
		return false;
	}

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ){
		ItemStack itemstack = player.inventory.getCurrentItem();

		if(isMultiBlock(world, x, y, z)){
			//if(itemstack == null){
				player.openGui(FoodMachineryRevolution.Instance, 0, world, x, y, z);
				return true;
			//}
		}
        return true;
    }

	public TileEntity createNewTileEntity(World world, int par2){
		return new TileEntityFFurnace();
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random){
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

		if(itemstack.hasDisplayName()){
			((TileEntityFFurnace)world.getTileEntity(x, y, z)).setGuiDisplayName(itemstack.getDisplayName());
		}
	}

	public static void updateFoodFurnaceState(boolean active, World worldObj, int xCoord, int yCoord, int zCoord) {
		int i= worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

		TileEntity tileentity = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		keepInventory = true;

		if(active){
			worldObj.setBlock(xCoord, yCoord, zCoord, MaterialRegister.FoodFurnace_Active);
		}else{
			worldObj.setBlock(xCoord, yCoord, zCoord, MaterialRegister.FoodFurnace_Idle);
		}

		keepInventory = false;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i, 2);

		if(tileentity != null){
			tileentity.validate();
			worldObj.setTileEntity(xCoord, yCoord, zCoord, tileentity);
		}
	}
}
