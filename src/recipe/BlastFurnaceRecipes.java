package recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlastFurnaceRecipes {

	private static final BlastFurnaceRecipes smeltingBase = new BlastFurnaceRecipes();
	  private Map smeltingList = new HashMap();
	  private Map experienceList = new HashMap();

	  public static BlastFurnaceRecipes smelting()
	  {
	    return smeltingBase;
	  }

	  private BlastFurnaceRecipes()
	  {
	    func_151393_a(Blocks.iron_ore, new ItemStack(Items.iron_ingot, 2), 0.7F);
	    func_151393_a(Blocks.gold_ore, new ItemStack(Items.gold_ingot, 2), 1.0F);
	    func_151393_a(Blocks.diamond_ore, new ItemStack(Items.diamond, 2), 1.0F);
	    func_151393_a(Blocks.emerald_ore, new ItemStack(Items.emerald, 2), 1.0F);
	    func_151393_a(Blocks.coal_ore, new ItemStack(Items.coal, 2), 0.1F);
	    func_151393_a(Blocks.redstone_ore, new ItemStack(Items.redstone, 2), 0.7F);
	    func_151393_a(Blocks.lapis_ore, new ItemStack(Items.dye, 2, 4), 0.5F);
	    func_151393_a(Blocks.quartz_ore, new ItemStack(Items.quartz, 2), 0.5F);

	    func_151393_a(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1F);
	    func_151393_a(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1F);
	    func_151393_a(Blocks.sand, new ItemStack(Blocks.glass), 0.1F);
	    func_151396_a(Items.clay_ball, new ItemStack(Items.brick), 0.3F);
	    func_151393_a(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35F);
	    func_151396_a(Items.bucket, new ItemStack(Items.lava_bucket), 0.3F);
	  }

	  public void func_151393_a(Block p_151393_1_, ItemStack p_151393_2_, float p_151393_3_)
	  {
	    func_151396_a(Item.getItemFromBlock(p_151393_1_), p_151393_2_, p_151393_3_);
	  }

	  public void func_151396_a(Item p_151396_1_, ItemStack p_151396_2_, float p_151396_3_)
	  {
	    func_151394_a(new ItemStack(p_151396_1_, 1, 32767), p_151396_2_, p_151396_3_);
	  }

	  @SuppressWarnings("unchecked")
	public void func_151394_a(ItemStack p_151394_1_, ItemStack p_151394_2_, float p_151394_3_)
	  {
	    this.smeltingList.put(p_151394_1_, p_151394_2_);
	    this.experienceList.put(p_151394_2_, Float.valueOf(p_151394_3_));
	  }

	  @SuppressWarnings("rawtypes")
	public ItemStack getSmeltingResult(ItemStack p_151395_1_)
	  {
	    Iterator iterator = this.smeltingList.entrySet().iterator();
	    Map.Entry entry;
	    do
	    {
	      if (!iterator.hasNext()) {
	        return null;
	      }
	      entry = (Map.Entry)iterator.next();
	    } while (!func_151397_a(p_151395_1_, (ItemStack)entry.getKey()));
	    return (ItemStack)entry.getValue();
	  }

	  private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_)
	  {
	    return (p_151397_2_.getItem() == p_151397_1_.getItem()) && ((p_151397_2_.getItemDamage() == 32767) || (p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage()));
	  }

	  @SuppressWarnings("rawtypes")
	public Map getSmeltingList()
	  {
	    return this.smeltingList;
	  }

	  @SuppressWarnings("rawtypes")
	public float func_151398_b(ItemStack p_151398_1_)
	  {
	    float ret = p_151398_1_.getItem().getSmeltingExperience(p_151398_1_);
	    if (ret != -1.0F) {
	      return ret;
	    }
	    Iterator iterator = this.experienceList.entrySet().iterator();
	    Map.Entry entry;
	    do
	    {
	      if (!iterator.hasNext()) {
	        return 0.0F;
	      }
	      entry = (Map.Entry)iterator.next();
	    } while (!func_151397_a(p_151398_1_, (ItemStack)entry.getKey()));
	    return ((Float)entry.getValue()).floatValue();
	  }
}
