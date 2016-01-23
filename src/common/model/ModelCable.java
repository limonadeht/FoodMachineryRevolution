package common.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelCable extends ModelBase{
	public static ModelCable instance = new ModelCable();

  //fields
    ModelRenderer Middle;
    ModelRenderer WestClosing;
    ModelRenderer EastClosing;
    ModelRenderer East;
    ModelRenderer West;
    ModelRenderer North;
    ModelRenderer NorthClosed;
    ModelRenderer South;
    ModelRenderer SouthClosed;
    ModelRenderer Up;
    ModelRenderer UpClosed;
    ModelRenderer Down;
    ModelRenderer DownClosed;

  public ModelCable(){
    textureWidth = 32;
    textureHeight = 32;

      Middle = new ModelRenderer(this, 0, 7);
      Middle.addBox(0F, 0F, 0F, 4, 4, 4);
      Middle.setRotationPoint(-2F, 14F, -2F);
      Middle.setTextureSize(32, 32);
      Middle.mirror = true;
      setRotation(Middle, 0F, 0F, 0F);
      WestClosing = new ModelRenderer(this, 0, 15);
      WestClosing.addBox(0F, 0F, 0F, 1, 6, 6);
      WestClosing.setRotationPoint(7F, 13F, -3F);
      WestClosing.setTextureSize(32, 32);
      WestClosing.mirror = true;
      setRotation(WestClosing, 0F, 0F, 0F);
      EastClosing = new ModelRenderer(this, 0, 20);
      EastClosing.addBox(0F, 0F, 0F, 1, 6, 6);
      EastClosing.setRotationPoint(-8F, 13F, -3F);
      EastClosing.setTextureSize(32, 32);
      EastClosing.mirror = true;
      setRotation(EastClosing, 0F, 0F, 0F);
      East = new ModelRenderer(this, 0, 24);
      East.addBox(0F, 0F, 0F, 6, 4, 4);
      East.setRotationPoint(-8F, 14F, -2F);
      East.setTextureSize(32, 32);
      East.mirror = true;
      setRotation(East, 0F, 0F, 0F);
      West = new ModelRenderer(this, 0, 24);
      West.addBox(0F, 0F, 0F, 6, 4, 4);
      West.setRotationPoint(2F, 14F, -2F);
      West.setTextureSize(32, 32);
      West.mirror = true;
      setRotation(West, 0F, 0F, 0F);
      North = new ModelRenderer(this, 0, 15);
      North.addBox(0F, 0F, 0F, 4, 4, 6);
      North.setRotationPoint(-2F, 14F, -8F);
      North.setTextureSize(32, 32);
      North.mirror = true;
      setRotation(North, 0F, 0F, 0F);
      NorthClosed = new ModelRenderer(this, 0, 18);
      NorthClosed.addBox(0F, 0F, 0F, 6, 6, 1);
      NorthClosed.setRotationPoint(-3F, 13F, -8F);
      NorthClosed.setTextureSize(32, 32);
      NorthClosed.mirror = true;
      setRotation(NorthClosed, 0F, 0F, 0F);
      South = new ModelRenderer(this, 0, 22);
      South.addBox(0F, 0F, 0F, 4, 4, 6);
      South.setRotationPoint(-2F, 14F, 2F);
      South.setTextureSize(32, 32);
      South.mirror = true;
      setRotation(South, 0F, 0F, 0F);
      SouthClosed = new ModelRenderer(this, 0, 18);
      SouthClosed.addBox(0F, 0F, 0F, 6, 6, 1);
      SouthClosed.setRotationPoint(-3F, 13F, 7F);
      SouthClosed.setTextureSize(32, 32);
      SouthClosed.mirror = true;
      setRotation(SouthClosed, 0F, 0F, 0F);
      Up = new ModelRenderer(this, 0, 15);
      Up.addBox(0F, 0F, 0F, 4, 6, 4);
      Up.setRotationPoint(-2F, 8F, -2F);
      Up.setTextureSize(32, 32);
      Up.mirror = true;
      setRotation(Up, 0F, 0F, 0F);
      UpClosed = new ModelRenderer(this, 0, 25);
      UpClosed.addBox(0F, 0F, 0F, 6, 1, 6);
      UpClosed.setRotationPoint(-3F, 8F, -3F);
      UpClosed.setTextureSize(32, 32);
      UpClosed.mirror = true;
      setRotation(UpClosed, 0F, 0F, 0F);
      Down = new ModelRenderer(this, 0, 15);
      Down.addBox(0F, 0F, 0F, 4, 6, 4);
      Down.setRotationPoint(-2F, 18F, -2F);
      Down.setTextureSize(32, 32);
      Down.mirror = true;
      setRotation(Down, 0F, 0F, 0F);
      DownClosed = new ModelRenderer(this, 0, 25);
      DownClosed.addBox(0F, 0F, 0F, 6, 1, 6);
      DownClosed.setRotationPoint(-3F, 23F, -3F);
      DownClosed.setTextureSize(32, 32);
      DownClosed.mirror = true;
      setRotation(DownClosed, 0F, 0F, 0F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Middle.render(f5);
    WestClosing.render(f5);
    EastClosing.render(f5);
    East.render(f5);
    West.render(f5);
    North.render(f5);
    NorthClosed.render(f5);
    South.render(f5);
    SouthClosed.render(f5);
    Up.render(f5);
    UpClosed.render(f5);
    Down.render(f5);
    DownClosed.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z){
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityPlayer player){
    super.setRotationAngles(f, f1, f2, f3, f4, f5, player);
    //super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }

  public void renderMiddle()
	{
		Middle.render(0.0625F);
	}

	public void renderNorth(boolean closing)
	{
		North.render(0.0625F);
		if(closing) NorthClosed.render(0.0625F);
	}

	public void renderSouth(boolean closing)
	{
		South.render(0.0625F);
		if(closing) SouthClosed.render(0.0625F);
	}

	public void renderEast(boolean closing)
	{
		East.render(0.0625F);
		if(closing) EastClosing.render(0.0625F);
	}

	public void renderWest(boolean closing)
	{
		West.render(0.0625F);
		if(closing) WestClosing.render(0.0625F);
	}

	public void renderUp(boolean closing)
	{
		Up.render(0.0625F);
		if(closing) UpClosed.render(0.0625F);
	}

	public void renderDown(boolean closing)
	{
		Down.render(0.0625F);
		if(closing) DownClosed.render(0.0625F);
	}
}