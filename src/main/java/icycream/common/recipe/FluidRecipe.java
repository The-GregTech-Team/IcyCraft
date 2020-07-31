package icycream.common.recipe;

import icycream.common.block.entity.TileEntityRecipeMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FluidRecipe implements IRecipe<TileEntityRecipeMachine> {
    @Override
    public boolean matches(TileEntityRecipeMachine inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(TileEntityRecipeMachine inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }
}
