package common.block;

import client.tileentity.TileEntityDiselGenerator;
import common.FoodMachineryRevolution;
import common.item.ItemDieselGenerator;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import util.Utils;

public class DiselGenerator extends BlockContainer{

	public final int energyGeneration;
	public final int energyTransfer;
	public final int energyCapacity;

	public DiselGenerator(String name, int energyGeneration){
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

	public boolean isOpaqueCube(){
		return false;
	}

	public boolean renderAsNormalBlock(){
		 return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side){
        return false;
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

	public int getFluidStored(ItemStack itemstack){
		if(itemstack.stackTagCompound != null){
			return itemstack.stackTagCompound.getInteger("productTank");
		}
		return 0;
	}

	public TileEntity createNewTileEntity(World world, int meta){
		return new TileEntityDiselGenerator(this.energyGeneration, this.energyTransfer, this.energyCapacity);
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

		TileEntityDiselGenerator tile = (TileEntityDiselGenerator)world.getTileEntity(x, y, z);

		if(player.getCurrentEquippedItem() != null){
			if(Utils.hasUsableWrench(player, x, y, z))
		      {
				/*if(tile.productTank.getFluidAmount() > 0 && tile.productTank.getFluidType() == MaterialRegister.fluidBioDiesel){
					tile.productTank.setAmount(0);
				}else{
					return false;
				}*/
		        if ((!world.isRemote) && (player.isSneaking()))
		        {
		          dismantleBlock(world, x, y, z);

		          return true;
		        }
		        world.notifyBlocksOfNeighborChange(x, y, z, this);
		      }
		    }

//		    if(tile == null){
		    	FluidStack fluid = tile.productTank.getFluid();
//		    	if(player.inventory.getCurrentItem() == null){
		    		String s = "";
		    		String s2 = "";
		    		if(fluid != null && fluid.getFluid() != null){
		    			s = EnumChatFormatting.GREEN + "Fluid Storage: " + fluid.getFluid().getLocalizedName(fluid) + " " + fluid.amount + " mB";
		    			s2 = EnumChatFormatting.GREEN + "Energy Storage: " + tile.getEnergyStored() + " / " + tile.getMaxEnergyStored();
		    		}else{
		    			s = EnumChatFormatting.GREEN + "Not a Fluids.";
		    			s2 = EnumChatFormatting.GREEN + "Energy Storage: " + tile.getEnergyStored() + " / " + tile.getMaxEnergyStored();
		    		}
		    		if (!world.isRemote) player.addChatMessage(new ChatComponentText(s));
		    		if (!world.isRemote) player.addChatMessage(new ChatComponentText(s2));
//	        		return true;
//		    	}

		    	//Noooooooooooo! 爆発します！
		    	//world.createExplosion(player, x, y, z, 20.0F, true);
		    	tile.markDirty();
		        player.inventory.markDirty();
		        world.markBlockForUpdate(x, y, z);

		        return true;
//		    }
//		    return false;
	}

	public void dismantleBlock(World world, int x, int y, int z)
	  {
	    ItemStack itemStack = new ItemStack(this);
	    float motion = 0.7F;
	    double motionX = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionY = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionZ = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    EntityItem entityItem = new EntityItem(world, x + motionX, y + motionY, z + motionZ, itemStack);
	    TileEntityDiselGenerator tileEntity = (TileEntityDiselGenerator)world.getTileEntity(x, y, z);
	    int energyStored = tileEntity.getEnergyStored();
	    if (energyStored >= 1)
	    {
	      if (itemStack.getTagCompound() == null) {
	        itemStack.setTagCompound(new NBTTagCompound());
	      }
	      itemStack.getTagCompound().setInteger("Energy", energyStored);
	    }
	    if(tileEntity.productTank.getFluidAmount() > 0){
	    	NBTTagCompound tankTag = tileEntity.getItemNBT();
			NBTTagCompound itemTag = Utils.getItemTag(itemStack);
	    	itemTag.setTag("productTank", tankTag);
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

		if(itemstack.stackTagCompound != null)
	      {
	        TileEntityDiselGenerator tileEntity = (TileEntityDiselGenerator)world.getTileEntity(x, y, z);

	        tileEntity.setEnergyStored(itemstack.stackTagCompound.getInteger("Energy"));

	        NBTTagCompound itemTag = itemstack.getTagCompound();

			if (itemTag != null && itemTag.hasKey(ItemDieselGenerator.DISEL_TAG)) {
				tileEntity.productTank.readFromNBT(itemTag.getCompoundTag(ItemDieselGenerator.DISEL_TAG));
			}
	      }
	}

	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer){
	    return true;
	}

	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer){
	    return true;
	}
}
