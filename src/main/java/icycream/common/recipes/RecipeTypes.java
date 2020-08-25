package icycream.common.recipes;

import icycream.IcyCream;
import icycream.common.recipes.special.IcecreamFreezingRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface RecipeTypes {
    IRecipeType<ShapelessFluidRecipe> SHAPELESS_FLUID_RECIPE_TYPE = IRecipeType.register(IcyCream.MODID + ":shapeless_fluid_recipe");
    IRecipeType<MixerRecipe> MIXING = IRecipeType.register(IcyCreamRecipeUid.MIXING.toString());
    IRecipeType<MaceratorRecipe> MACERATING = IRecipeType.register(IcyCreamRecipeUid.MACERATING.toString());
    IRecipeType<ExtractorRecipe> EXTRACTING = IRecipeType.register(IcyCreamRecipeUid.EXTRACTING.toString());
    IRecipeType<IcecreamFreezingRecipe> FREEZING = IRecipeType.register(IcyCreamRecipeUid.FREEZING.toString());

}
