package common.block;

import java.util.Random;

import client.tileentity.TileEntitySolarPanel;
import client.tileentity.TileEntityWindRotor;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import util.Utils;

public class WindRotor extends BlockContainer{

	public final int energyTransfer;
	public final int energyCapacity;

	public WindRotor(String name, Material material){
		super(material);
		this.setBlockName(name);
		this.setBlockTextureName("fmr:blockOfSteel");

		this.energyTransfer = 1000;
		this.energyCapacity = 100000;
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
		return new TileEntityWindRotor(energyTransfer, energyCapacity);
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
		TileEntityWindRotor tile = (TileEntityWindRotor)world.getTileEntity(x, y, z);

		if(world.isRemote){
			player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Energy Storage: " + tile.getEnergyStored() + " / " + tile.getMaxEnergyStored()));
		}

		if(player.getCurrentEquippedItem() != null){
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

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
		world.scheduleBlockUpdate(x, y, z, this, 72000);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random){
		world.setBlockToAir(x, y, z);
		super.updateTick(world, x, y, z, random);
	}
}
