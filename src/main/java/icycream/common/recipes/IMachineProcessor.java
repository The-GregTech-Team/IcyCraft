package icycream.common.recipes;

/**
 * 大概抄了下原版的IRecipeHolder
 */
public interface IMachineProcessor {

    MachineRecipe getCurrentRecipe();

    float getProgress();
}
