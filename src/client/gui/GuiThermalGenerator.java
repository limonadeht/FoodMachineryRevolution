package client.gui;

import java.util.Arrays;

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

	public void drawGuiContainerBackgroundLayer(int mouseX,int mouseY){

		String s = this.tileentity.hasCustomInventoryName() ? this.tileentity.getInventoryName() : I18n.format(this.tileentity.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
	    this.fontRendererObj.drawString(I18n.format("inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);

		this.drawInfomation(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float per1, int per2, int per3) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bground);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
        int i1;

        if(this.tileentity.burnTimeRemaining > 0){
        	i1 = this.tileentity.getBurnTimeRemainingScaled(12);
        	this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
	}

	private void drawInfomation(int x, int y)
	{
		int minX = guiLeft + 147;
		int maxX = guiLeft + 162;
		int minY = guiTop + 17;
		int maxY = guiTop + 66;
		if(x >= minX && x <= maxX && y >= minY && y <= maxX)
		{
			this.drawHoveringText(Arrays.asList("This is Crafting Tables!"), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}
	}

	@Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
