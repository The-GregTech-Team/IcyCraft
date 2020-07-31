package icycream.common.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

public class MixerRecipe extends ShapelessFluidRecipe {
    public MixerRecipe(ResourceLocation id, List<Ingredient> ingredients, List<FluidStack> fluidInputs, @Nonnull ItemStack output, FluidStack outputFluid, int ticks) {
        super(id, ingredients, fluidInputs, output, outputFluid, ticks);
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ShapelessFluidRecipeSerializer.MIXING;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return RecipeTypes.MIXING;
    }
}
