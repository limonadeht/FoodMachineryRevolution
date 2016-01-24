package client.render;

import org.lwjgl.opengl.GL11;

import common.model.ModelHydroGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityHydroGeneratorRenderer extends TileEntitySpecialRenderer{

	public static final TileEntityHydroGeneratorRenderer instance = new TileEntityHydroGeneratorRenderer();
	ResourceLocation TEXTURE = new ResourceLocation("fmr", "textures/models/HydroGenerator.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double xOffset, double yOffset, double zOffset, float partialTickTime){
		GL11.glPushMatrix();
		GL11.glTranslatef((float) (xOffset + 0.5F), (float) (yOffset + 1.5F), (float) (zOffset + 0.5F));
		GL11.glRotatef(180, 0, 0, 1);

		int meta = tileentity.getBlockMetadata();

		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		ModelHydroGenerator.instance.renderModel(0.0625F);

		GL11.glPopMatrix();
	}
}
