package common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOfSteel extends Block{

	public BlockOfSteel(){
		super(Material.iron);
		this.setBlockName("fmr.blockOfSteel");
		this.setBlockTextureName("fmr:blockOfSteel");
		this.setStepSound(soundTypeMetal);
	}
}
