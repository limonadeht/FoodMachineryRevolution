package client.render;

import org.lwjgl.opengl.GL11;

import common.model.ModelFluidPipe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemFluidPipeRenderer implements IItemRenderer{

	public static final ItemFluidPipeRenderer instance = new ItemFluidPipeRenderer();
	ResourceLocation TEXTURE = new ResourceLocation("fmr", "textures/models/FluidPipe.png");

	@Override
	public boolean handleRenderType(ItemStack itemstack, ItemRenderType type){
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data){
		int meta = item.getItemDamage();
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.4F, 0.5F);
		GL11.glRotatef(180, 0, 0, 1);
		if(meta == 0){
			Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
			ModelFluidPipe.instance.renderMiddle();
			ModelFluidPipe.instance.renderDown(true);
			ModelFluidPipe.instance.renderUp(true);
		}
		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
}
