package client.gui;

import org.lwjgl.opengl.GL11;

import common.block.container.ContainerFCraftingTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiFCraftTable extends GuiContainer{

	private ResourceLocation texture = new ResourceLocation("fmr", "textures/gui/fCraftTable.png");

	private float mouseX;
	private float mouseY;

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

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
