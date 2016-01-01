package common.block;

import client.tileentity.TileEntitySolarPanel;
import common.FoodMachineryRevolution;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import util.Utils;

public class SolarPanel extends BlockContainer{

	@SideOnly(Side.CLIENT)
	private IIcon top;
	@SideOnly(Side.CLIENT)
	private IIcon bottom;

	public final int energyGeneration;
	public final int energyTransfer;
	public final int energyCapacity;

	public SolarPanel(String name, int energyGeneration){
		super(Material.iron);
		this.setBlockName(name);
		this.setResistance(100.0F);
		this.setHardness(10.0F);
		this.setCreativeTab(FoodMachineryRevolution.tabFMR);
		this.energyGeneration = energyGeneration;
		this.energyTransfer = (energyGeneration * 2);
		this.energyCapacity = (energyGeneration * 1000);
		this.setStepSound(soundTypeMetal);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
		//this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
		//this.setLightOpacity(255);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister){
		this.blockIcon = iconregister.registerIcon("fmr:solarSide");
		this.bottom = iconregister.registerIcon("fmr:solarBottom");
		this.top = iconregister.registerIcon("fmr:solarTop");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return side == 1 ? this.top : (side == 0 ? this.bottom/*底*/ : (side != meta ? this.blockIcon : this.blockIcon));
	}

	public int getEnergyGeneration()
	  {
	    return this.energyGeneration;
	  }

	  public int getEnergyTransfer()
	  {
	    return this.energyTransfer;
	  }

	  public int getEnergyCapacity()
	  {
	    return this.energyCapacity;
	  }

	  public int getEnergyStored(ItemStack itemStack)
	  {
	    if (itemStack.stackTagCompound != null) {
	      return itemStack.stackTagCompound.getInteger("Energy");
	    }
	    return 0;
	  }

	  public int getMaxEnergyStored(ItemStack itemStack)
	  {
	    return this.energyCapacity;
	  }

	  public TileEntity createNewTileEntity(World world, int meta)
	  {
	    return new TileEntitySolarPanel(this.energyGeneration, this.energyTransfer, this.energyCapacity);
	  }

	  public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNumber, int eventArgument)
	  {
	    super.onBlockEventReceived(world, x, y, z, eventNumber, eventArgument);

	    TileEntity tileentity = world.getTileEntity(x, y, z);
	    if (tileentity != null) {
	      return tileentity.receiveClientEvent(eventNumber, eventArgument);
	    }
	    return false;
	  }

	  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f1, float f2, float f3)
	  {
		  if (player.getCurrentEquippedItem() != null) {
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

	  public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	  {
	    return true;
	  }

	  public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	  {
	    return true;
	  }

	  public String getUnlocalizedName()
	  {
	    return String.format("tile.%s%s", new Object[] { "fmr:", unwrapUnlocalizedName(super.getUnlocalizedName()) });
	  }

	  protected String getTextureName()
	  {
	    return unwrapUnlocalizedName(getUnlocalizedName());
	  }

	  protected String unwrapUnlocalizedName(String pUnlocalizedName)
	  {
	    return pUnlocalizedName.substring(pUnlocalizedName.indexOf(".") + 1);
	  }

	  	public static class Basic extends SolarPanel{

		  public Basic(String name, int energyGeneration){
			  super(name, energyGeneration);
		  }

		  public TileEntity createNewTileEntity(World world, int meta){
		      return new TileEntitySolarPanel.Basic(this.energyGeneration, this.energyTransfer, this.energyCapacity);
		  }

		  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		    {
		      if (itemStack.stackTagCompound != null)
		      {
		        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

		        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
		      }
		      EntityPlayer player = (EntityPlayer)entity;
		    }
	  }

	  	public static class BasicI extends SolarPanel{

			  public BasicI(String name, int energyGeneration){
				  super(name, energyGeneration);
			  }

			  public TileEntity createNewTileEntity(World world, int meta){
			      return new TileEntitySolarPanel.BasicI(this.energyGeneration, this.energyTransfer, this.energyCapacity);
			  }

			  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
			    {
			      if (itemStack.stackTagCompound != null)
			      {
			        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

			        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
			      }
			      EntityPlayer player = (EntityPlayer)entity;
			    }
		  }

	  	public static class BasicII extends SolarPanel{

			  public BasicII(String name, int energyGeneration){
				  super(name, energyGeneration);
			  }

			  public TileEntity createNewTileEntity(World world, int meta){
			      return new TileEntitySolarPanel.BasicII(this.energyGeneration, this.energyTransfer, this.energyCapacity);
			  }

			  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
			    {
			      if (itemStack.stackTagCompound != null)
			      {
			        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

			        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
			      }
			      EntityPlayer player = (EntityPlayer)entity;
			    }
		  }

	  	public static class BasicIII extends SolarPanel{

			  public BasicIII(String name, int energyGeneration){
				  super(name, energyGeneration);
			  }

			  public TileEntity createNewTileEntity(World world, int meta){
			      return new TileEntitySolarPanel.BasicIII(this.energyGeneration, this.energyTransfer, this.energyCapacity);
			  }

			  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
			    {
			      if (itemStack.stackTagCompound != null)
			      {
			        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

			        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
			      }
			      EntityPlayer player = (EntityPlayer)entity;
			    }
		  }

	  public static class Advanced extends SolarPanel{

		  public Advanced(String name, int energyGeneration){
			  super(name, energyGeneration);
		  }

		  public TileEntity createNewTileEntity(World world, int meta){
		      return new TileEntitySolarPanel.Advanced(this.energyGeneration, this.energyTransfer, this.energyCapacity);
		  }

		  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		    {
		      if (itemStack.stackTagCompound != null)
		      {
		        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

		        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
		      }
		      EntityPlayer player = (EntityPlayer)entity;
		    }
	  }

	  public static class AdvancedI extends SolarPanel{

		  public AdvancedI(String name, int energyGeneration){
			  super(name, energyGeneration);
		  }

		  public TileEntity createNewTileEntity(World world, int meta){
		      return new TileEntitySolarPanel.AdvancedI(this.energyGeneration, this.energyTransfer, this.energyCapacity);
		  }

		  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		    {
		      if (itemStack.stackTagCompound != null)
		      {
		        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

		        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
		      }
		      EntityPlayer player = (EntityPlayer)entity;
		    }
	  }

	  public static class AdvancedII extends SolarPanel{

		  public AdvancedII(String name, int energyGeneration){
			  super(name, energyGeneration);
		  }

		  public TileEntity createNewTileEntity(World world, int meta){
		      return new TileEntitySolarPanel.AdvancedII(this.energyGeneration, this.energyTransfer, this.energyCapacity);
		  }

		  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		    {
		      if (itemStack.stackTagCompound != null)
		      {
		        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

		        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
		      }
		      EntityPlayer player = (EntityPlayer)entity;
		    }
	  }

	  public static class AdvancedIII extends SolarPanel{

		  public AdvancedIII(String name, int energyGeneration){
			  super(name, energyGeneration);
		  }

		  public TileEntity createNewTileEntity(World world, int meta){
		      return new TileEntitySolarPanel.AdvancedIII(this.energyGeneration, this.energyTransfer, this.energyCapacity);
		  }

		  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		    {
		      if (itemStack.stackTagCompound != null)
		      {
		        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

		        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
		      }
		      EntityPlayer player = (EntityPlayer)entity;
		    }
	  }

	  public static class Enchanted extends SolarPanel{

		  public Enchanted(String name, int energyGeneration){
			  super(name, energyGeneration);
		  }

		  public TileEntity createNewTileEntity(World world, int meta)
		    {
		      return new TileEntitySolarPanel.Enchanted(this.energyGeneration, this.energyTransfer, this.energyCapacity);
		    }

		  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		    {
		      if (itemStack.stackTagCompound != null)
		      {
		        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

		        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
		      }
		      EntityPlayer player = (EntityPlayer)entity;
		    }
	  }

	  public static class EnchantedI extends SolarPanel{

		  public EnchantedI(String name, int energyGeneration){
			  super(name, energyGeneration);
		  }

		  public TileEntity createNewTileEntity(World world, int meta)
		    {
		      return new TileEntitySolarPanel.EnchantedI(this.energyGeneration, this.energyTransfer, this.energyCapacity);
		    }

		  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		    {
		      if (itemStack.stackTagCompound != null)
		      {
		        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

		        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
		      }
		      EntityPlayer player = (EntityPlayer)entity;
		    }
	  }

	  public static class EnchantedII extends SolarPanel{

		  public EnchantedII(String name, int energyGeneration){
			  super(name, energyGeneration);
		  }

		  public TileEntity createNewTileEntity(World world, int meta)
		    {
		      return new TileEntitySolarPanel.EnchantedII(this.energyGeneration, this.energyTransfer, this.energyCapacity);
		    }

		  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		    {
		      if (itemStack.stackTagCompound != null)
		      {
		        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

		        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
		      }
		      EntityPlayer player = (EntityPlayer)entity;
		    }
	  }

	  public static class EnchantedIII extends SolarPanel{

		  public EnchantedIII(String name, int energyGeneration){
			  super(name, energyGeneration);
		  }

		  public TileEntity createNewTileEntity(World world, int meta)
		    {
		      return new TileEntitySolarPanel.EnchantedIII(this.energyGeneration, this.energyTransfer, this.energyCapacity);
		    }

		  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
		    {
		      if (itemStack.stackTagCompound != null)
		      {
		        TileEntitySolarPanel tileEntity = (TileEntitySolarPanel)world.getTileEntity(x, y, z);

		        tileEntity.setEnergyStored(itemStack.stackTagCompound.getInteger("Energy"));
		      }
		      EntityPlayer player = (EntityPlayer)entity;
		    }
	  }
}
