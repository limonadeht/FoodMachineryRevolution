package client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import client.tileentity.TileEntityDiselGenerator;
import common.model.ModelDieselGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TileEntityDieselGeneratorRenderer extends TileEntitySpecialRenderer{

	public static TileEntityDieselGeneratorRenderer renderer;

	public static final TileEntityDieselGeneratorRenderer instance = new TileEntityDieselGeneratorRenderer();
	ResourceLocation TEXTURE = new ResourceLocation("fmr", "textures/models/DieselGenerator.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double xOffset, double yOffset, double zOffset, float partialTickTime){
		this.renderTileEntityCupAt((TileEntityDiselGenerator)tileentity, xOffset, yOffset, zOffset, partialTickTime);

		GL11.glPushMatrix();
		GL11.glTranslatef((float) (xOffset + 0.5F), (float) (yOffset + 1.5F), (float) (zOffset + 0.5F));
		GL11.glRotatef(180, 0, 0, 1);

		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		ModelDieselGenerator.instance.renderModel(0.0625F);

		GL11.glPopMatrix();
	}

	public void renderTileEntityCupAt(TileEntityDiselGenerator par1Tile, double par2, double par4, double par6, float par8)
    {
        this.setRotation(par1Tile, (float)par2, (float)par4, (float)par6);
    }

	 public void setTileEntityRenderer(TileEntityRendererDispatcher par1TileEntityRenderer)
	    {
	        super.func_147497_a(par1TileEntityRenderer);
	        renderer = this;
	    }


	 public void setRotation(TileEntityDiselGenerator par0Tile, float par1, float par2, float par3){
	    	Tessellator tessellator = Tessellator.instance;
	    	if (par0Tile.getFluidIcon() != null){
	            //コメントアウト部分を復帰させると、水面の描写が半透明になる。
	    	    GL11.glPushMatrix();
	            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	            //GL11.glEnable(GL11.GL_BLEND);//1
	            //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);//2
	            GL11.glColor4f(2.0F, 2.0F, 2.0F, 0.75F);
	            GL11.glTranslatef((float)par1, (float)par2 + 0.5F, (float)par3);
	            GL11.glScalef(1.0F, -1.2F, -1.0F);/*GL11.glScalef(1.0F, -1.0F, -1.0F);*/
	            GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);

	            IIcon iicon = par0Tile.getFluidIcon();
	            float f14 = iicon.getMinU();
	            float f15 = iicon.getMaxU();
	            float f4 = iicon.getMinV();
	            float f5 = iicon.getMaxV();

	            this.bindTexture(TextureMap.locationBlocksTexture);

	            float f = 0.0625F;
	            tessellator.startDrawingQuads();
	            tessellator.setNormal(1.0F, 0.0F, 0.0F);
	            tessellator.addVertexWithUV(0.0D + f, -0.4D, -1.0D + f, (double)f15, (double)f4);
	            tessellator.addVertexWithUV(1.0D - f, -0.4D, -1.0D + f, (double)f14, (double)f4);
	            tessellator.addVertexWithUV(1.0D - f, -0.4D, 0.0D - f, (double)f14, (double)f5);
	            tessellator.addVertexWithUV(0.0D + f, -0.4D, 0.0D - f, (double)f15, (double)f5);
	            tessellator.draw();

	            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	            //GL11.glDisable(GL11.GL_BLEND);//3
	            GL11.glPopMatrix();
	    	}


	    }
}
