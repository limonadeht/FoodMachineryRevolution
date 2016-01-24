package client.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import common.block.FoodCraftTable;
import common.block.container.ContainerFCraftingTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiFCraftTable extends GuiContainer{

	private ResourceLocation texture = new ResourceLocation("fmr", "textures/gui/fCraftTable.png");

	public FoodCraftTable ftable;

	public GuiFCraftTable(InventoryPlayer invPlayer, World world, int x, int y, int z){
		super(new ContainerFCraftingTable(invPlayer, world, x, y, z));

		this.xSize = 176;
		this.ySize = 188;
	}

	public void onGuiClosed(){
		super.onGuiClosed();
	}


	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		this.fontRendererObj.drawString(StatCollector.translateToLocal("Food Crafting"), 100, 5, 0xFFFFFF);
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
}
