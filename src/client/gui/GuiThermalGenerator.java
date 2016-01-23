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
import net.minecraftforge.common.util.ForgeDirection;

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

	public void drawGuiContainerForegroundLayer(int mouseX,int mouseY){

		String s = this.tileentity.hasCustomInventoryName() ? this.tileentity.getInventoryName() : I18n.format(this.tileentity.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
	    this.fontRendererObj.drawString(I18n.format("inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);

		this.drawInfomation(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int X, int Y) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bground);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        //if(tileentity.getStackInSlot(0) == null){

        	//float power = (float)tileentity.getEnergyStored(ForgeDirection.DOWN) / (float)tileentity.getMaxEnergyStored(ForgeDirection.DOWN) * -1 + 1;
    		//float fuel = tileentity.burnTimeRemaining / ((float)tileentity.burnTime) * -1 + 1;

    		if(this.tileentity.isBurning){
    			int i1 = this.tileentity.getBurnTimeRemainingScaled(13);
                this.drawTexturedModalRect(k + 81, l + 30 + 12 - i1, 176, 44 - i1, 14, i1 + 1);
    			/*int k = this.tileentity.getBurnTimeRemainingScaled(13);
    			int j = 13 - k;
    			this.drawTexturedModalRect(guiLeft + 81, guiTop + 30, 176, 44, 14 - j, 14);*/
    		}
    		//this.drawTexturedModalRect(guiLeft + 150, guiTop + 20 + (int)(power*40), xSize, 0 + (int)(power*40), 16, 50 - (int)(power*40));//エネルギーバー
    		//this.drawTexturedModalRect(guiLeft + 81, guiTop + 30 + (int)(fuel*13), xSize, 40 + (int)(fuel*13), 18, 18 - (int)(fuel*13));//エネルギーバー

    		this.fontRendererObj.drawStringWithShadow("Thermal Generator", guiLeft + 64, guiTop + 0, 0x00FFFF);
        //}
	}

	private void drawInfomation(int x, int y)
	{
		int minX = guiLeft + 147; int minX2 = guiLeft + 81;
		int maxX = guiLeft + 162; int maxX2 = guiLeft + 94;
		int minY = guiTop + 17;   int minY2 = guiTop + 30;
		int maxY = guiTop + 66;   int maxY2 = guiTop + 43;
		if(x >= minX && x <= maxX && y >= minY && y <= maxY){
			this.drawHoveringText(Arrays.asList("Energy: " + this.tileentity.getEnergyStored(ForgeDirection.DOWN) + " / " + this.tileentity.getMaxEnergyStored(ForgeDirection.DOWN)), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}
		if(x >= minX2 && x <= maxX2 && y >= minY2 && y <= maxY2){
			this.drawHoveringText(Arrays.asList("BurnTime: " + this.tileentity.burnTimeRemaining), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}
	}

	@Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
