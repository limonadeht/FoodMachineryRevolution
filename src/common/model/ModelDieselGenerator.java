package common.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelDieselGenerator extends ModelBase{

	public static ModelDieselGenerator instance = new ModelDieselGenerator();

  //fields
    ModelRenderer DieselBase;
    ModelRenderer Pool1;
    ModelRenderer Pool2;
    ModelRenderer Pool3;
    ModelRenderer Pool4;

  public ModelDieselGenerator()
  {
    textureWidth = 256;
    textureHeight = 256;

      DieselBase = new ModelRenderer(this, 0, 0);
      DieselBase.addBox(0F, 0F, 0F, 16, 15, 16);
      DieselBase.setRotationPoint(-8F, 9F, -8F);
      DieselBase.setTextureSize(256, 256);
      DieselBase.mirror = true;
      setRotation(DieselBase, 0F, 0F, 0F);
      Pool1 = new ModelRenderer(this, 0, 35);
      Pool1.addBox(0F, 0F, 0F, 16, 1, 1);
      Pool1.setRotationPoint(-8F, 8F, 7F);
      Pool1.setTextureSize(256, 256);
      Pool1.mirror = true;
      setRotation(Pool1, 0F, 0F, 0F);
      Pool2 = new ModelRenderer(this, 0, 35);
      Pool2.addBox(0F, 0F, 0F, 16, 1, 1);
      Pool2.setRotationPoint(-8F, 8F, -8F);
      Pool2.setTextureSize(256, 256);
      Pool2.mirror = true;
      setRotation(Pool2, 0F, 0F, 0F);
      Pool3 = new ModelRenderer(this, 0, 40);
      Pool3.addBox(0F, 0F, 0F, 1, 1, 14);
      Pool3.setRotationPoint(-8F, 8F, -7F);
      Pool3.setTextureSize(256, 256);
      Pool3.mirror = true;
      setRotation(Pool3, 0F, 0F, 0F);
      Pool4 = new ModelRenderer(this, 0, 40);
      Pool4.addBox(0F, 0F, 0F, 1, 1, 14);
      Pool4.setRotationPoint(7F, 8F, -7F);
      Pool4.setTextureSize(256, 256);
      Pool4.mirror = true;
      setRotation(Pool4, 0F, 0F, 0F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    DieselBase.render(f5);
    Pool1.render(f5);
    Pool2.render(f5);
    Pool3.render(f5);
    Pool4.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void renderModel(float f5){
	  DieselBase.render(f5);
	  Pool1.render(f5);
	  Pool2.render(f5);
	  Pool3.render(f5);
	  Pool4.render(f5);
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityPlayer player)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, player);
  }

}
