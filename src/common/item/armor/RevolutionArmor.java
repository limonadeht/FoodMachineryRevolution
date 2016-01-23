package common.item.armor;

import java.util.Collection;
import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import common.FoodMachineryRevolution;
import common.MaterialRegister;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import util.ItemNBTHelper;

public class RevolutionArmor extends ItemArmor implements ISpecialArmor, IEnergyContainerItem{

	@SideOnly(Side.CLIENT)
	private IIcon helmetIcon;
	@SideOnly(Side.CLIENT)
	private IIcon chestPlateIcon;
	@SideOnly(Side.CLIENT)
	private IIcon legginsIcon;
	@SideOnly(Side.CLIENT)
	private IIcon bootsIcon;

	private double totalAbsorption = 2; // 1=100%
	private int maxTransfer = 100000;
	private int maxEnergy = 100000000;
	private int energyPerDamage = 80;

	public RevolutionArmor(ArmorMaterial material, int armorType, String name){
		super(material, 0, armorType);
		this.setUnlocalizedName(name);
		this.setCreativeTab(FoodMachineryRevolution.tabFMR);

		GameRegistry.registerItem(this, name);
	}

	@Override
	public boolean isItemTool(ItemStack itemstack) {
		return true;
	}

	@Override
	public String getUnlocalizedName() {

		return String.format("item.%s%s", "fmr:", super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf(".") + 1));
	}

	@Override
	public String getUnlocalizedName(final ItemStack itemStack) {
		return getUnlocalizedName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		helmetIcon = iconRegister.registerIcon("draconic_helmet");
		chestPlateIcon = iconRegister.registerIcon("draconic_chestplate");
		legginsIcon = iconRegister.registerIcon("draconic_leggings");
		bootsIcon = iconRegister.registerIcon("draconic_boots");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (stack.getItem() == MaterialRegister.revolutionHelmet) return helmetIcon;
		else if (stack.getItem() == MaterialRegister.revolutionChestPlate) return chestPlateIcon;
		else if (stack.getItem() == MaterialRegister.revolutionBoots) return legginsIcon;
		else return bootsIcon;
	}

	@Override
	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.epic;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double)ItemNBTHelper.getInteger(stack, "Energy", 0) / (double)maxEnergy;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getEnergyStored(stack) < maxEnergy;
	}

	protected double getAbsorptionPercent() {
		switch (armorType) {
			case 0:
				return 0.15D;
			case 1:
				return 0.40D;
			case 2:
				return 0.30D;
			case 3:
				return 0.15D;
		}
		return 0;
	}

	/* ISpecialArmor */

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		int maxAbsorption = 25 * getEnergyStored(armor) / energyPerDamage;
		if (source.damageType.equals(DamageSource.fall.damageType) && armor.getItem() == MaterialRegister.revolutionBoots) return new ArmorProperties(0, 1D, maxAbsorption);
		if (source.isUnblockable()) return new ArmorProperties(0, (getAbsorptionPercent()*totalAbsorption)/2, maxAbsorption);
		return new ArmorProperties(0, getAbsorptionPercent()*totalAbsorption, maxAbsorption);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return this.getEnergyStored(armor) > 10000 ? (int)(getAbsorptionPercent() * 20D) : (int) ((float)this.getEnergyStored(armor) / 10000F * (float)(getAbsorptionPercent() * 20D));
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		extractEnergy(stack, damage * energyPerDamage, false);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (stack == null) return;
		if (stack.getItem() == MaterialRegister.revolutionHelmet) {
			if (world.isRemote) return;
			if (this.getEnergyStored(stack) >= 5000 && clearNegativeEffects(player)) this.extractEnergy(stack, 5000, false);
			if (player.worldObj.getBlockLightValue((int)Math.floor(player.posX), (int) player.posY, (int)Math.floor(player.posZ)) < 5 && ItemNBTHelper.getBoolean(stack, "ArmorNVActive", false))
			{
				player.addPotionEffect(new PotionEffect(16, 419, 0, true));
			}
			else if ( ItemNBTHelper.getBoolean(stack, "ArmorNVActive", false) && ItemNBTHelper.getBoolean(stack, "ArmorNVLock", true)) player.addPotionEffect(new PotionEffect(16, 419, 0, true));
			else if (player.isPotionActive(16)) player.removePotionEffect(16);

		}
		if (stack.getItem() == MaterialRegister.revolutionLeggins && player.isSprinting() && !player.capabilities.isCreativeMode) {
			this.extractEnergy(stack, player.capabilities.isFlying ? 160 : 80, false);
		}
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		if(FoodMachineryRevolution.serverproxy.isShiftKeyDown()){
			list.add(EnumChatFormatting.LIGHT_PURPLE + "Energy: " + this.getEnergyStored(stack) + " / " + this.getMaxEnergyStored(stack));
		}else{
			list.add("Hold " + EnumChatFormatting.AQUA + EnumChatFormatting.ITALIC + "LShift " + EnumChatFormatting.GRAY + "for Info");
		}
	}

	@SuppressWarnings("unchecked")
	public boolean clearNegativeEffects(Entity par3Entity) {
		boolean flag = false;
		if (par3Entity.ticksExisted % 20 == 0) {
			if (par3Entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) par3Entity;

				Collection<PotionEffect> potions = player.getActivePotionEffects();

				/*if (player.isBurning()) {
					player.extinguish();
					flag = true;
				} else for (PotionEffect potion : potions) {
					int id = potion.getPotionID();
					if (ReflectionHelper.getPrivateValue(Potion.class, Potion.potionTypes[id], new String[]{"isBadEffect", "field_76418_K", "J"})) {
						if (potion.getPotionID() == Potion.digSlowdown.id && ModHelper.isHoldingCleaver(player)) break;
						if ((player.getHeldItem() == null || (player.getHeldItem().getItem() != ModItems.wyvernBow && player.getHeldItem().getItem() != ModItems.draconicBow)) || id != 2) {
							player.removePotionEffect(id);
							flag = true;
						}
						break;
					}
				}*/
			}
		}
		return flag;
	}

	/* IEnergyContainerItem */
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int stored = ItemNBTHelper.getInteger(container, "Energy", 0);
		int receive = Math.min(maxReceive, Math.min(maxEnergy - stored, maxTransfer));

		if (!simulate) {
			stored += receive;
			ItemNBTHelper.setInteger(container, "Energy", stored);
		}
		return receive;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		int stored = ItemNBTHelper.getInteger(container, "Energy", 0);
		int extract = Math.min(maxExtract, stored);

		if (!simulate) {
			stored -= extract;
			ItemNBTHelper.setInteger(container, "Energy", stored);
		}
		return extract;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return ItemNBTHelper.getInteger(container, "Energy", 0);
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {

		return maxEnergy;
	}
}
