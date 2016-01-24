package client.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import client.tileentity.TileEntityHydroelectricGenerator;
import common.block.HydroelectricGenerator;
import common.block.container.ContainerHydroGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiHydroGenerator extends GuiContainer{

	private ResourceLocation texture = new ResourceLocation("fmr", "textures/gui/HydroGenerator.png");

	public HydroelectricGenerator generator;

	public GuiHydroGenerator(InventoryPlayer invPlayer, TileEntityHydroelectricGenerator entity){
		super(new ContainerHydroGenerator(invPlayer, entity));

		this.xSize = 176;
		this.ySize = 188;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){

		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Hydrolectric Generator"), 7, 6, 0x00FFFF);
		/*if(generator.getEnergyGeneration() > 0){
			this.fontRendererObj.drawString(
					StatCollector.translateToLocal("Generate Energy: 50 RF/t"), 7, 12, 0x000000);
		}
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Energy Capacity: " + generator.getEnergyStored(null) + " / " + generator.energyCapacity), 7, 18, 0x000000);*/

		this.drawInfomation(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	private void drawInfomation(int x, int y)
	{
		int minX = guiLeft + 0;
		int maxX = guiLeft + 173;
		int minY = guiTop + 0;
		int maxY = guiTop + 0;
		if(x >= minX && x <= maxX && y >= minY && y <= maxY)
		{
			this.drawHoveringText(Arrays.asList("This is Crafting Tables!"), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}
	}

	@Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
