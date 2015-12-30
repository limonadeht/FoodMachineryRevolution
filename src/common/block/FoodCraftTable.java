package common.block;

import common.FoodMachineryRevolution;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class FoodCraftTable extends Block{

	public int GuiId = 1;

	@SideOnly(Side.CLIENT)
	private IIcon top;

	public FoodCraftTable(){
		super(Material.iron);

		this.setBlockName("fmr.fCrafting");
		this.setHardness(10.0F);
		this.setResistance(10.0F);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return side == 1 ? this.top : this.top;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister){
		this.top = iconRegister.registerIcon("fmr:foodCraftingTableTop");
	}

	public int getFCraftTableGuiId(){
		return this.GuiId;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		player.openGui(FoodMachineryRevolution.Instance, GuiId, world, x, y, z);
		return true;
	}
}
