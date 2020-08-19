package icycream.common.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 冰箱的配方，特点：仅仅处理一个格子，处理完毕前不会消耗物品，物品处理时间随着物品的数量而增加
 */
public class RefrigeratorRecipe extends ShapelessFluidRecipe {
    public RefrigeratorRecipe(ResourceLocation id, List<Ingredient> ingredients, List<FluidStack> fluidInputs, @Nonnull ItemStack output, FluidStack outputFluid, int ticks) {
        super(id, ingredients, fluidInputs, output, outputFluid, ticks);
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ShapelessFluidRecipeSerializer.FREEZING;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return RecipeTypes.FREEZING;
    }
}
