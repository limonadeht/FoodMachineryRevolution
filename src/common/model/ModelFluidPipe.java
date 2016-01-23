package common.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelFluidPipe extends ModelBase{

	public static ModelFluidPipe instance = new ModelFluidPipe();

  //fields
    ModelRenderer Base;
    ModelRenderer Pipe1;
    ModelRenderer Pipe1Closed;
    ModelRenderer Pipe2;
    ModelRenderer Pipe2Closed;
    ModelRenderer Pipe3;
    ModelRenderer Pipe3Closed;
    ModelRenderer Pipe4;
    ModelRenderer Pipe4Closed;
    ModelRenderer Pipe5;
    ModelRenderer Pipe5Closed;
    ModelRenderer Pipe6;
    ModelRenderer Pipe6Closed;

  public ModelFluidPipe()
  {
    textureWidth = 256;
    textureHeight = 256;

      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(0F, 0F, 0F, 8, 8, 8);
      Base.setRotationPoint(-4F, 12F, -4F);
      Base.setTextureSize(256, 256);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);

      //East
      Pipe1 = new ModelRenderer(this, 37, 0);
      Pipe1.addBox(0F, 0F, 0F, 4, 6, 6);
      Pipe1.setRotationPoint(-8F, 13F, -3F);
      Pipe1.setTextureSize(256, 256);
      Pipe1.mirror = true;
      setRotation(Pipe1, 0F, 0F, 0F);

      //EastClosed
      Pipe1Closed = new ModelRenderer(this, 43, 23);
      Pipe1Closed.addBox(0F, 0F, 0F, 1, 8, 8);
      Pipe1Closed.setRotationPoint(-9F, 12F, -4F);
      Pipe1Closed.setTextureSize(256, 256);
      Pipe1Closed.mirror = true;
      setRotation(Pipe1Closed, 0F, 0F, 0F);

      //West
      Pipe2 = new ModelRenderer(this, 60, 0);
      Pipe2.addBox(0F, 0F, 0F, 4, 6, 6);
      Pipe2.setRotationPoint(4F, 13F, -3F);
      Pipe2.setTextureSize(256, 256);
      Pipe2.mirror = true;
      setRotation(Pipe2, 0F, 0F, 0F);

      //WestClosed
      Pipe2Closed = new ModelRenderer(this, 83, 0);
      Pipe2Closed.addBox(0F, 0F, 0F, 1, 8, 8);
      Pipe2Closed.setRotationPoint(8F, 12F, -4F);
      Pipe2Closed.setTextureSize(256, 256);
      Pipe2Closed.mirror = true;
      setRotation(Pipe2Closed, 0F, 0F, 0F);

      Pipe3 = new ModelRenderer(this, 104, 0);
      Pipe3.addBox(0F, 0F, 0F, 6, 6, 4);
      Pipe3.setRotationPoint(-3F, 13F, 4F);
      Pipe3.setTextureSize(256, 256);
      Pipe3.mirror = true;
      setRotation(Pipe3, 0F, 0F, 0F);

      Pipe3Closed = new ModelRenderer(this, 124, 21);
      Pipe3Closed.addBox(0F, 0F, 0F, 8, 8, 1);
      Pipe3Closed.setRotationPoint(-4F, 12F, 8F);
      Pipe3Closed.setTextureSize(256, 256);
      Pipe3Closed.mirror = true;
      setRotation(Pipe3Closed, 0F, 0F, 0F);

      //North
      Pipe4 = new ModelRenderer(this, 104, 0);
      Pipe4.addBox(0F, 0F, 0F, 6, 6, 4);
      Pipe4.setRotationPoint(-3F, 13F, -8F);
      Pipe4.setTextureSize(256, 256);
      Pipe4.mirror = true;
      setRotation(Pipe4, 0F, 0F, 0F);

      //NorthClosed
      Pipe4Closed = new ModelRenderer(this, 124, 21);
      Pipe4Closed.addBox(0F, 0F, 0F, 8, 8, 1);
      Pipe4Closed.setRotationPoint(-4F, 12F, -9F);
      Pipe4Closed.setTextureSize(256, 256);
      Pipe4Closed.mirror = true;
      setRotation(Pipe4Closed, 0F, 0F, 0F);

      Pipe5 = new ModelRenderer(this, 2, 21);
      Pipe5.addBox(0F, 0F, 0F, 6, 4, 6);
      Pipe5.setRotationPoint(-3F, 20F, -3F);
      Pipe5.setTextureSize(256, 256);
      Pipe5.mirror = true;
      setRotation(Pipe5, 0F, 0F, 0F);

      Pipe5Closed = new ModelRenderer(this, 36, 55);
      Pipe5Closed.addBox(0F, 0F, 0F, 8, 1, 8);
      Pipe5Closed.setRotationPoint(-4F, 24F, -4F);
      Pipe5Closed.setTextureSize(256, 256);
      Pipe5Closed.mirror = true;
      setRotation(Pipe5Closed, 0F, 0F, 0F);

      //South
      Pipe6 = new ModelRenderer(this, 2, 42);
      Pipe6.addBox(0F, 0F, 0F, 6, 4, 6);
      Pipe6.setRotationPoint(-3F, 8F, -3F);
      Pipe6.setTextureSize(256, 256);
      Pipe6.mirror = true;
      setRotation(Pipe6, 0F, 0F, 0F);

      //SouthClosed
      Pipe6Closed = new ModelRenderer(this, 0, 56);
      Pipe6Closed.addBox(0F, 0F, 0F, 8, 1, 8);
      Pipe6Closed.setRotationPoint(-4F, 7F, -4F);
      Pipe6Closed.setTextureSize(256, 256);
      Pipe6Closed.mirror = true;
      setRotation(Pipe6Closed, 0F, 0F, 0F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Base.render(f5);
    Pipe1.render(f5);
    Pipe1Closed.render(f5);
    Pipe2.render(f5);
    Pipe2Closed.render(f5);
    Pipe3.render(f5);
    Pipe3Closed.render(f5);
    Pipe4.render(f5);
    Pipe4Closed.render(f5);
    Pipe5.render(f5);
    Pipe5Closed.render(f5);
    Pipe6.render(f5);
    Pipe6Closed.render(f5);
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

  public void renderMiddle()
	{
		Base.render(0.0625F);
	}

  public void renderEast(boolean closing)
	{
		Pipe1.render(0.0625F);
		if(closing) Pipe1Closed.render(0.0625F);
	}

	public void renderSouth(boolean closing)
	{
		Pipe3.render(0.0625F);
		if(closing) Pipe3Closed.render(0.0625F);
	}

	public void renderNorth(boolean closing)
	{
		Pipe4.render(0.0625F);
		if(closing) Pipe4Closed.render(0.0625F);
	}

	public void renderWest(boolean closing)
	{
		Pipe2.render(0.0625F);
		if(closing) Pipe2Closed.render(0.0625F);
	}

	public void renderDown(boolean closing)
	{
		Pipe5.render(0.0625F);
		if(closing) Pipe5Closed.render(0.0625F);
	}

	public void renderUp(boolean closing)
	{
		Pipe6.render(0.0625F);
		if(closing) Pipe6Closed.render(0.0625F);
	}

}
