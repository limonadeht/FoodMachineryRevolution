package common.block;

import client.tileentity.TileEntityFFurnace;
import client.tileentity.TileEntityHydroelectricGenerator;
import client.tileentity.TileEntitySolarPanel;
import common.FoodMachineryRevolution;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import util.Utils;

public class HydroelectricGenerator extends BlockContainer{

	public final int energyGeneration;
	public final int energyTransfer;
	public final int energyCapacity;

	public HydroelectricGenerator(String name, int energyGeneration){
		super(Material.iron);
		this.setBlockName(name);
		this.setResistance(100.0F);
		this.setHardness(10.0F);
		this.setCreativeTab(FoodMachineryRevolution.tabFMR);

		this.energyGeneration = energyGeneration;
		this.energyTransfer = (energyGeneration * 2);
		this.energyCapacity = (energyGeneration * 1000);
		this.setStepSound(soundTypeMetal);
	}

	public int getEnergyGeneration(){
		return this.energyGeneration;
	}

	public int getEnergyTransfer(){
		return this.energyTransfer;
	}

	public int getEnergyCapacity(){
		return this.energyCapacity;
	}

	public int getEnergyStored(ItemStack itemStack){
	    if (itemStack.stackTagCompound != null) {
	      return itemStack.stackTagCompound.getInteger("Energy");
	    }
	    return 0;
	}

	public TileEntity createNewTileEntity(World world, int meta){
		return new TileEntityHydroelectricGenerator(this.energyGeneration, this.energyTransfer, this.energyCapacity);
	}

	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNumber, int eventArgument){
	    super.onBlockEventReceived(world, x, y, z, eventNumber, eventArgument);

	    TileEntity tileentity = world.getTileEntity(x, y, z);
	    if (tileentity != null) {
	      return tileentity.receiveClientEvent(eventNumber, eventArgument);
	    }
	    return false;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f1, float f2, float f3){
		if(!player.isSneaking()){
			player.openGui(FoodMachineryRevolution.Instance, 5, world, x, y, z);
			return true;
		}

		  if (player.getCurrentEquippedItem() != null){
		      if (Utils.hasUsableWrench(player, x, y, z))
		      {
		        if ((!world.isRemote) && (player.isSneaking()))
		        {
		          dismantleBlock(world, x, y, z);

		          return true;
		        }
		        world.notifyBlocksOfNeighborChange(x, y, z, this);

		        return false;
		      }
		    }
		    return false;
	}

	public void dismantleBlock(World world, int x, int y, int z)
	  {
	    ItemStack itemStack = new ItemStack(this);
	    float motion = 0.7F;
	    double motionX = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionY = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionZ = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    EntityItem entityItem = new EntityItem(world, x + motionX, y + motionY, z + motionZ, itemStack);
	    TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);
	    int energyStored = tileEntity.getEnergyStored();
	    if (energyStored >= 1)
	    {
	      if (itemStack.getTagCompound() == null) {
	        itemStack.setTagCompound(new NBTTagCompound());
	      }
	      itemStack.getTagCompound().setInteger("Energy", energyStored);
	    }
	    world.setBlockToAir(x, y, z);
	    world.spawnEntityInWorld(entityItem);
	  }

	@SuppressWarnings("unused")
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

	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer){
	    return true;
	}

	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer){
	    return true;
	}

	public boolean isOpaqueCube(){
		return false;
	}

	public boolean renderAsNormalBlock(){
		 return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side){
        return false;
    }

	public static class Advanced extends HydroelectricGenerator{

		public Advanced(String name, int energyGeneration){
			super(name, energyGeneration);
		}

		public TileEntity createNewTileEntity(World world, int meta){
			return new TileEntityHydroelectricGenerator.Advanced(energyGeneration, energyTransfer, energyCapacity);
		}

		public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack){
	      if (itemStack.stackTagCompound != null){
	        TileEntityHydroelectricGenerator tileEntity = (TileEntityHydroelectricGenerator)world.getTileEntity(x, y, z);

	        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
	      }
		}
	}
}
