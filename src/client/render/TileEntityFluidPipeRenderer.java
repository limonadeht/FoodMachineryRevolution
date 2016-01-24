package client.render;

import org.lwjgl.opengl.GL11;

import cofh.api.energy.IEnergyHandler;
import common.model.ModelFluidPipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluidPipeRenderer extends TileEntitySpecialRenderer{

	public static final TileEntityFluidPipeRenderer instance = new TileEntityFluidPipeRenderer();
	ResourceLocation TEXTURE = new ResourceLocation("fmr", "textures/models/FluidPipe.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double xOffset, double yOffset, double zOffset, float partialTickTime){
		int meta = tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
		boolean[][] sides = new boolean[2][6];
		boolean[] cables = new boolean[6];
		for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS){
			sides[meta][direction.ordinal()] = tileentity.getWorldObj().getTileEntity(tileentity.xCoord + direction.offsetX, tileentity.yCoord + direction.offsetY, tileentity.zCoord + direction.offsetZ)instanceof IEnergyHandler  && ((IEnergyHandler) tileentity.getWorldObj().getTileEntity(tileentity.xCoord + direction.offsetX, tileentity.yCoord + direction.offsetY, tileentity.zCoord + direction.offsetZ)).canConnectEnergy(direction);
			cables [direction.ordinal()] = tileentity.getWorldObj().getTileEntity(tileentity.xCoord + direction.offsetX, tileentity.yCoord + direction.offsetY, tileentity.zCoord + direction.offsetZ)instanceof IEnergyHandler  && ((IEnergyHandler)tileentity.getWorldObj().getTileEntity(tileentity.xCoord + direction.offsetX, tileentity.yCoord + direction.offsetY, tileentity.zCoord + direction.offsetZ))instanceof IEnergyHandler;
		}
		GL11.glPushMatrix();
		GL11.glTranslatef((float) (xOffset + 0.5F), (float) (yOffset + 1.5F), (float) (zOffset + 0.5F));
		GL11.glRotatef(180, 0, 0, 1);
		if(meta == 0){
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
			ModelFluidPipe.instance.renderMiddle();
			if(sides[0][0]) ModelFluidPipe.instance.renderDown(cables[0]);
				if(sides[0][1]) ModelFluidPipe.instance.renderUp(cables[1]);
					if(sides[0][2]) ModelFluidPipe.instance.renderNorth(cables[2]);
						if(sides[0][3]) ModelFluidPipe.instance.renderSouth(cables[3]);
							if(sides[0][4]) ModelFluidPipe.instance.renderWest(cables[4]);
								if(sides[0][5]) ModelFluidPipe.instance.renderEast(cables[5]);
		}
		GL11.glPopMatrix();
	}
}
