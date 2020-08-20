package icycream.common.recipes;

import com.google.common.collect.Lists;
import icycream.common.util.InventoryUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class ShapelessFluidRecipe implements IRecipe<IInventory> {
    public final ResourceLocation id;
    public final List<Ingredient> ingredients;
    public final List<FluidStack> fluidInputs;
    public final ItemStack output;
    public final FluidStack outputFluid;
    public final int ticks;

    public ShapelessFluidRecipe(ResourceLocation id, List<Ingredient> ingredients, List<FluidStack> fluidInputs, @Nonnull ItemStack output, FluidStack outputFluid, int ticks) {
        this.id = id;
        this.ingredients = ingredients;
        this.fluidInputs = fluidInputs;
        this.output = output;
        this.outputFluid = outputFluid;
        this.ticks = ticks;
    }

    /**
     * 就一个默认实现而已
     */
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    public boolean matches(IInventory inv, IFluidHandler fluidHandler) {
        if (RecipeMatcher.findMatches(InventoryUtils.toList(inv), ingredients) == null) return false;
        for (FluidStack fluidInput : fluidInputs) {
            FluidStack drained = fluidHandler.drain(fluidInput, IFluidHandler.FluidAction.SIMULATE);
            if (!FluidStack.areFluidStackTagsEqual(drained, fluidInput) || drained.getAmount() != fluidInput.getAmount()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给冰箱用的，只判断单个格子
     * @return
     */
    public boolean matches(IInventory inv, int index) {
        return RecipeMatcher.findMatches(Lists.newArrayList(inv.getStackInSlot(index)), ingredients) != null;
    }

    public void consume(IInventory inv, IFluidHandler fluidHandler) {
        InventoryUtils.take(inv, ingredients);
        for (FluidStack fluidInput : fluidInputs) {
            fluidHandler.drain(fluidInput, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull IInventory inv) {
        return output;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ShapelessFluidRecipeSerializer.INSTANCE;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return RecipeTypes.SHAPELESS_FLUID_RECIPE_TYPE;
    }
}
