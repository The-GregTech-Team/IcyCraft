package icycream.common.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;

/**
 * 大概抄了下原版的IRecipeHolder
 */
public interface IMachineProcessor<T extends IRecipe<IInventory>> {

    T getCurrentRecipe();
}
