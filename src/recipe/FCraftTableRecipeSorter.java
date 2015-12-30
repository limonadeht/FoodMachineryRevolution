package recipe;

import java.util.Comparator;

import net.minecraft.item.crafting.IRecipe;

public class FCraftTableRecipeSorter implements Comparator{

	final FCraftTableCraftingManager fCraftTable;

	public FCraftTableRecipeSorter(FCraftTableCraftingManager manager){
		this.fCraftTable = manager;
	}

	public int compareRecipes(IRecipe iRecipe, IRecipe iRecipe2){
		return iRecipe instanceof FCraftTableShapelessRecipes && iRecipe2 instanceof FCraftTableShapedRecipes ? 1 : (iRecipe2 instanceof FCraftTableShapelessRecipes && iRecipe instanceof FCraftTableShapedRecipes ? -1 : (iRecipe2.getRecipeSize() < iRecipe.getRecipeSize() ? -1 : (iRecipe2.getRecipeSize() > iRecipe.getRecipeSize() ? 1 : 0)));
	}

	@Override
	public int compare(Object o1, Object o2) {
		return this.compareRecipes((IRecipe)o1, (IRecipe)o2);
	}
}
