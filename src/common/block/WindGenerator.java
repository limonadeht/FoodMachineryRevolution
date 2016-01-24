package common.block;

import client.tileentity.TileEntitySolarPanel;
import client.tileentity.TileEntityWindGenerator;
import common.FoodMachineryRevolution;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import util.Utils;

public class WindGenerator extends BlockContainer{

	@SideOnly(Side.CLIENT)
	private IIcon front;
	@SideOnly(Side.CLIENT)
	private IIcon side;

	public final int energyGeneration;
	public final int energyTransfer;
	public final int energyCapacity;

	public WindGenerator(String name, int energyGeneration, Material material){
		super(material);
		this.setBlockName(name);
		this.setResistance(100.0F);
		this.setHardness(10.0F);
		this.setCreativeTab(FoodMachineryRevolution.tabFMR);

		this.energyGeneration = energyGeneration;
		this.energyTransfer = (energyGeneration * 2);
		this.energyCapacity = (energyGeneration * 1000);
		this.setStepSound(soundTypeMetal);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister){
		this.blockIcon = iconregister.registerIcon("fmr:windGeneratorBase");
		this.front = iconregister.registerIcon("fmr:windGeneratorBack");
		this.side = iconregister.registerIcon("fmr:windGeneratorSide");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return side == 1 ? this.blockIcon : (side == 0 ? this.blockIcon/*底*/ : (side != meta ? this.side : this.front/*正面*/));
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
		return new TileEntityWindGenerator(this.energyGeneration, this.energyTransfer, this.energyCapacity);
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

	public void dismantleBlock(World world, int x, int y, int z){
	    ItemStack itemStack = new ItemStack(this);
	    float motion = 0.7F;
	    double motionX = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionY = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionZ = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    EntityItem entityItem = new EntityItem(world, x + motionX, y + motionY, z + motionZ, itemStack);
	    TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);
	    int energyStored = tileEntity.getEnergyStored();
	    if (energyStored >= 1){
	      if (itemStack.getTagCompound() == null) {
	        itemStack.setTagCompound(new NBTTagCompound());
	      }
	      itemStack.getTagCompound().setInteger("Energy", energyStored);
	    }
	    world.setBlockToAir(x, y, z);
	    world.spawnEntityInWorld(entityItem);
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
	}

	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer){
	    return true;
	}

	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer){
	    return true;
	}

	public static class Wood extends WindGenerator{

		@SideOnly(Side.CLIENT)
		private IIcon front;
		@SideOnly(Side.CLIENT)
		private IIcon side;

		public Wood(String name, int energyGeneration, Material material){
			super(name, energyGeneration, material);
		}

		@SideOnly(Side.CLIENT)
		public void registerBlockIcons(IIconRegister iconregister){
			this.blockIcon = iconregister.registerIcon("fmr:windGeneratorWoodBase");
			this.front = iconregister.registerIcon("fmr:windGeneratorWoodBack");
			this.side = iconregister.registerIcon("fmr:windGeneratorWoodSide");
		}

		@SideOnly(Side.CLIENT)
		public IIcon getIcon(int side, int meta){
			return side == 1 ? this.blockIcon : (side == 0 ? this.blockIcon/*底*/ : (side != meta ? this.side : this.front/*正面*/));
		}

		public TileEntity createNewTileEntity(World world, int meta){
			return new TileEntityWindGenerator.Wood(energyGeneration, energyTransfer, energyCapacity);
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

		public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack){
			if(itemStack.stackTagCompound != null){
				TileEntityWindGenerator tileEntity = (TileEntityWindGenerator)world.getTileEntity(x, y, z);

				tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
			}

			int l = MathHelper.floor_double((double)(entity.rotationYaw*4.0f/360.F)+0.5D)&3;

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
		}
	}
}
