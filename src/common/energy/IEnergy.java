package common.energy;

import net.minecraftforge.common.util.ForgeDirection;

public interface IEnergy {

	public boolean canAddEnergyOnSide(ForgeDirection direction);
	public boolean canConnect(ForgeDirection direction);
	public EnergyBar getEnergyBar();
	public void SetLeastRecivedDirection(ForgeDirection direction);
	public int getEnergyTransferRate();
}
