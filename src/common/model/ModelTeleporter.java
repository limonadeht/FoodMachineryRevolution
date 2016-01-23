package common.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelTeleporter extends ModelBase{

	public static ModelTeleporter instance = new ModelTeleporter();

	//fields
    ModelRenderer Bottom;
    ModelRenderer CenterCube;
    ModelRenderer Up;
    ModelRenderer Connector1;
    ModelRenderer Connector2;
    ModelRenderer Connector3;
    ModelRenderer Connector4;

  public ModelTeleporter()
  {
    textureWidth = 256;
    textureHeight = 256;

      Bottom = new ModelRenderer(this, 0, 0);
      Bottom.addBox(0F, 0F, 0F, 16, 1, 16);
      Bottom.setRotationPoint(-8F, 23F, -8F);
      Bottom.setTextureSize(256, 256);
      Bottom.mirror = true;
      setRotation(Bottom, 0F, 0F, 0F);
      CenterCube = new ModelRenderer(this, 72, 0);
      CenterCube.addBox(0F, 0F, 0F, 8, 10, 8);
      CenterCube.setRotationPoint(-4F, 13F, -4F);
      CenterCube.setTextureSize(256, 256);
      CenterCube.mirror = true;
      setRotation(CenterCube, 0F, 0F, 0F);
      Up = new ModelRenderer(this, 0, 0);
      Up.addBox(0F, 0F, 0F, 16, 1, 16);
      Up.setRotationPoint(-8F, 12F, -8F);
      Up.setTextureSize(256, 256);
      Up.mirror = true;
      setRotation(Up, 0F, 0F, 0F);
      Connector1 = new ModelRenderer(this, 0, 21);
      Connector1.addBox(0F, 0F, 0F, 8, 8, 1);
      Connector1.setRotationPoint(-4F, 14F, -8F);
      Connector1.setTextureSize(256, 256);
      Connector1.mirror = true;
      setRotation(Connector1, 0F, 0F, 0F);
      Connector2 = new ModelRenderer(this, 21, 20);
      Connector2.addBox(0F, 0F, 0F, 8, 8, 1);
      Connector2.setRotationPoint(-4F, 14F, 7F);
      Connector2.setTextureSize(256, 256);
      Connector2.mirror = true;
      setRotation(Connector2, 0F, 0F, 0F);
      Connector3 = new ModelRenderer(this, 21, 32);
      Connector3.addBox(0F, 0F, 0F, 1, 8, 8);
      Connector3.setRotationPoint(7F, 14F, -4F);
      Connector3.setTextureSize(256, 256);
      Connector3.mirror = true;
      setRotation(Connector3, 0F, 0F, 0F);
      Connector4 = new ModelRenderer(this, 0, 32);
      Connector4.addBox(0F, 0F, 0F, 1, 8, 8);
      Connector4.setRotationPoint(-8F, 14F, -4F);
      Connector4.setTextureSize(256, 256);
      Connector4.mirror = true;
      setRotation(Connector4, 0F, 0F, 0F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Bottom.render(f5);
    CenterCube.render(f5);
    Up.render(f5);
    Connector1.render(f5);
    Connector2.render(f5);
    Connector3.render(f5);
    Connector4.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityPlayer player)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, player);
  }

  public void renderModel(float f5){
	  Bottom.render(f5);
	    CenterCube.render(f5);
	    Up.render(f5);
	    Connector1.render(f5);
	    Connector2.render(f5);
	    Connector3.render(f5);
	    Connector4.render(f5);
  }
}