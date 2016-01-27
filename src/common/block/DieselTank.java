package common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class DieselTank extends FluidTank{

	public DieselTank(int capacity) {
		super(capacity);
	}

	public DieselTank(FluidStack stack, int capacity) {
		super(stack, capacity);
	}

	public DieselTank(Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
	}

	public boolean isEmpty() {
	    return (getFluid() == null) || getFluid().getFluid() == null || (getFluid().amount <= 0);
	}

	public boolean isFull() {
	    return (getFluid() != null) && (getFluid().amount == getCapacity());
	}

	public Fluid getFluidType() {
	    return getFluid() != null ? getFluid().getFluid() : null;
	}

	public String getFluidName()
	{
	    return (this.fluid != null) && (this.fluid.getFluid() != null) ? this.fluid.getFluid().getLocalizedName(this.fluid): "Empty";
	}

	@SideOnly(Side.CLIENT)
	public void setAmount(int par1)
	{
		if (this.fluid != null && this.fluid.getFluid() != null)
		{
			this.fluid.amount = par1;
		}
	}
}
