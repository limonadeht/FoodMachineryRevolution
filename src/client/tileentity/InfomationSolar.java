package client.tileentity;

import java.util.List;

import common.FoodMachineryRevolution;
import common.block.SolarPanel;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import util.Utils;

public class InfomationSolar extends ItemBlock{

	public InfomationSolar(Block block){
		super(block);
	}

	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool)
	  {
	    SolarPanel solarPanel = (SolarPanel)this.field_150939_a;
	    if (!FoodMachineryRevolution.serverproxy.isShiftKeyDown())
	    {
	      list.add(Utils.shiftForDetails());
	    }
	    else
	    {
	      list.add(String.format(Utils.addEnergyGenerationTooltip() + EnumChatFormatting.DARK_GREEN + " %,d" + Utils.addRFtTooltip(), new Object[] { Integer.valueOf(solarPanel.getEnergyGeneration()) }));
	      list.add(String.format(Utils.addEnergyTransferTooltip() + EnumChatFormatting.DARK_GREEN + " %,d" + Utils.addRFtTooltip(), new Object[] { Integer.valueOf(solarPanel.getEnergyTransfer()) }));
	      list.add(String.format(Utils.addEnergyCapacityTooltip() + EnumChatFormatting.DARK_GREEN + " %,d" + Utils.addRFTooltip(), new Object[] { Integer.valueOf(solarPanel.getEnergyCapacity()) }));
	    }
	    if (!Utils.isControlKeyDown())
	    {
	      list.add(Utils.controlForDetails());
	    }
	    else
	    {
	      int energy = solarPanel.getEnergyStored(itemStack);
	      int maxEnergy = solarPanel.getMaxEnergyStored(itemStack);
	      if (energy > maxEnergy / 4 * 3) {
	        list.add(String.format(new StringBuilder().append(EnumChatFormatting.DARK_GREEN).append("%,d / %,d").toString(), new Object[] { Integer.valueOf(energy), Integer.valueOf(maxEnergy) }) + Utils.addRFTooltip());
	      }
	      if ((energy <= maxEnergy / 4 * 3) && (energy > maxEnergy / 4)) {
	        list.add(String.format(new StringBuilder().append(EnumChatFormatting.YELLOW).append("%,d / %,d").toString(), new Object[] { Integer.valueOf(energy), Integer.valueOf(maxEnergy) }) + Utils.addRFTooltip());
	      }
	      if ((energy <= maxEnergy / 4) && (energy > 0)) {
	        list.add(String.format(new StringBuilder().append(EnumChatFormatting.RED).append("%,d / %,d").toString(), new Object[] { Integer.valueOf(energy), Integer.valueOf(maxEnergy) }) + Utils.addRFTooltip());
	      }
	      if (energy == 0) {
	        list.add(String.format("%,d / %,d", new Object[] { Integer.valueOf(energy), Integer.valueOf(maxEnergy) }) + Utils.addRFTooltip());
	      }
	    }
	  }

	  public boolean showDurabilityBar(ItemStack itemStack)
	  {
	    return true;
	  }

	  public double getDurabilityForDisplay(ItemStack itemStack)
	  {
	    SolarPanel solarPanel = (SolarPanel)this.field_150939_a;

	    int energy = solarPanel.getEnergyStored(itemStack);
	    int maxEnergy = solarPanel.getMaxEnergyStored(itemStack);

	    return 1.0D - energy / maxEnergy;
	  }
}
