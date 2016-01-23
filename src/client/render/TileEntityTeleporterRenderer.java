package client.render;

import org.lwjgl.opengl.GL11;

import common.model.ModelTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityTeleporterRenderer extends TileEntitySpecialRenderer{

	public static final TileEntityTeleporterRenderer instance = new TileEntityTeleporterRenderer();
	ResourceLocation TEXTURE = new ResourceLocation("fmr", "textures/models/Teleporter.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double xOffset, double yOffset, double zOffset, float partialTickTime){
		int meta = tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);

		GL11.glPushMatrix();
		GL11.glTranslatef((float) (xOffset + 0.5F), (float) (yOffset + 1.5F), (float) (zOffset + 0.5F));
		GL11.glRotatef(180, 0, 0, 1);

		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		ModelTeleporter.instance.renderModel(0.0625F);
		//ModelResourcePortal.instance.renderModel(0.0625F);

		GL11.glPopMatrix();
	}
}
