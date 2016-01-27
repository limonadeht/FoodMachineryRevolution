package client.render;

import org.lwjgl.opengl.GL11;

import common.model.ModelDieselGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemDieselGeneratorRenderer implements IItemRenderer{

	public static final ItemDieselGeneratorRenderer instance = new ItemDieselGeneratorRenderer();
	ResourceLocation TEXTURE = new ResourceLocation("fmr", "textures/models/DieselGenerator.png");

	@Override
	public boolean handleRenderType(ItemStack itemstack, ItemRenderType type){
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data){
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.4F, 0.5F);
		GL11.glRotatef(180, 0, 0, 1);

		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		ModelDieselGenerator.instance.renderModel(0.0625F);

		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
}
