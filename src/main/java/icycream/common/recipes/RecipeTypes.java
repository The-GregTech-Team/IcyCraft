package icycream.common.recipes;

import icycream.IcyCream;
import net.minecraft.item.crafting.IRecipeType;

public interface RecipeTypes {
    IRecipeType<ShapelessFluidRecipe> SHAPELESS_FLUID_RECIPE_TYPE = IRecipeType.register(IcyCream.MODID + ":shapeless_fluid_recipe");
    IRecipeType<MixerRecipe> MIXING = IRecipeType.register(IcyCream.MODID + ":mixing");
    IRecipeType<MaceratorRecipe> MACERATING = IRecipeType.register(IcyCream.MODID + ":macerating");
    IRecipeType<ExtractorRecipe> EXTRACTING = IRecipeType.register(IcyCream.MODID + ":extracting");
    IRecipeType<RefrigeratorRecipe> FREEZING = IRecipeType.register(IcyCream.MODID + ":freezing");

}
