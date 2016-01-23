package common.energy;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyNetwork {

	public static void disttributeEnergyToSurrounding(World world, int x, int y, int z, ForgeDirection lastDerection, EnergyBar energyBar)
	{
		distributeEnergyToSurroundingWithLoss(world, x, y, z, lastDerection, energyBar, 0);
	}

	public static void disttributeEnergyToSurrounding(World world, int x, int y, int z, EnergyBar energyBar)
	{
		disttributeEnergyToSurrounding(world, x, y, z, ForgeDirection.UNKNOWN, energyBar);
	}

	public static void disttributeEnergyToSide(World world, int x, int y, int z, ForgeDirection direction, EnergyBar energyBar)
	{
		if(world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ) instanceof IEnergyHandler)
		{
			IEnergy energyTileOnSide = (IEnergy) world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
			IEnergy thisEnergyTile = (IEnergy) world.getTileEntity(x, y, z);
			ForgeDirection invertedSide = ForgeDirection.VALID_DIRECTIONS[ForgeDirection.OPPOSITES[direction.ordinal()]];
			if (energyTileOnSide.canConnect(invertedSide) && energyTileOnSide.canAddEnergyOnSide(invertedSide))
			{
				if(energyBar.getEnergyLevel() - thisEnergyTile.getEnergyTransferRate() >= 0)
				{
					if(energyTileOnSide.getEnergyBar().canAddEnergy(thisEnergyTile.getEnergyTransferRate()))
					{
						energyTileOnSide.getEnergyBar().addEnergy(thisEnergyTile.getEnergyTransferRate());
						energyBar.removeEnergy(thisEnergyTile.getEnergyTransferRate());
					}
					else
					{
						int remaining = energyTileOnSide.getEnergyBar().addEnergyWithRemaining(thisEnergyTile.getEnergyTransferRate());
						energyBar.removeEnergy(thisEnergyTile.getEnergyTransferRate() - remaining);
					}
					energyTileOnSide.SetLeastRecivedDirection(ForgeDirection.VALID_DIRECTIONS[ForgeDirection.OPPOSITES[direction.ordinal()]]);
				}
			}
		}
	}

	public static void distributeEnergyToSurroundingWithLoss(World world, int x, int y, int z, ForgeDirection lastDirection, EnergyBar energyBar, int loss)
	{
	int sides = 0;
		boolean sidesCanOutput[] = new boolean[6];
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
		{
			if (world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ) instanceof IEnergy)
			{
				IEnergy energyTileNextTolt = (IEnergy) world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
				IEnergy thisEnergyTile = (IEnergy) world.getTileEntity(x, y, z);
				ForgeDirection invertedSide = ForgeDirection.VALID_DIRECTIONS[ForgeDirection.OPPOSITES[direction.ordinal()]];
				if(thisEnergyTile.canConnect(invertedSide) && energyTileNextTolt.canAddEnergyOnSide(invertedSide) && direction != lastDirection)
				{
					sidesCanOutput[direction.ordinal()] = true;
					sides++;
				}
			}
		}

		for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
		{
			if (sidesCanOutput[direction.ordinal()] && direction != lastDirection)
			{
				IEnergy energytile = (IEnergy) world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
				if(energyBar.getEnergyLevel() - energytile.getEnergyTransferRate() / sides >= 0)
				{
					if(energytile.getEnergyBar().canAddEnergy(energytile.getEnergyTransferRate() / sides - loss))
					{
						energytile.getEnergyBar().addEnergy(energytile.getEnergyTransferRate() / sides - loss);
						energyBar.removeEnergy(energytile.getEnergyTransferRate() / sides);
					}
					else
					{
						int remaining = energytile.getEnergyBar().addEnergyWithRemaining(energytile.getEnergyTransferRate() / sides - loss);
						energyBar.removeEnergy(energytile.getEnergyTransferRate() / sides - remaining);
					}
					energytile.SetLeastRecivedDirection(ForgeDirection.VALID_DIRECTIONS[ForgeDirection.OPPOSITES[direction.ordinal()]]);
				}
			}
		}
	}
}
