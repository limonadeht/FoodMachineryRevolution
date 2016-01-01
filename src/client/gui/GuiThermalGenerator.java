package client.gui;

import org.lwjgl.opengl.GL11;

import client.tileentity.TileEntitytGenerator;
import common.block.container.ContainerTGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiThermalGenerator extends GuiContainer{

	public static final ResourceLocation bground = new ResourceLocation("fmr", "textures/gui/tGenerator.png");

	public TileEntitytGenerator tileentity;

	public EntityPlayer player;
	private int guiUpdateTick;

	public GuiThermalGenerator(InventoryPlayer invPlayer, TileEntitytGenerator entity){
		super(new ContainerTGenerator(invPlayer, entity));

		this.tileentity = entity;
		this.player = invPlayer.player;

		this.xSize = 176;
		this.ySize = 166;
	}

	public void drawGuiContainerBackgroundLayer(int per1,int per2){

		String s = this.tileentity.hasCustomInventoryName() ? this.tileentity.getInventoryName() : I18n.format(this.tileentity.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
	    this.fontRendererObj.drawString(I18n.format("inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float per1, int per2, int per3) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bground);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        /*if (this.tileentity.isBurning())
        {
            int i1 = this.tileentity.getBurnTimeRemainingScaled(69);
            this.drawTexturedModalRect(x + 6, y + 62 + 12 - i1, 178, 69 - i1, 17, i1 + 1);

            int i2 = this.tileentity.getCookProgressScaled(82);
            this.drawTexturedModalRect(x + 53, y + 35, 0, 167, i2 + 1, 16);
        }*/

        //int i3 = 69 - ((this.tileentity.waterTank.getCapacity() - this.tileentity.waterTank.getFluidAmount()) / 57);
        //this.drawTexturedModalRect(x + 34, y + 62 + 12 - i3, 178, 141 - i3, 17, i3 + 1);
	}

	@Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
