package icycream.common.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import icycream.IcyCream;
import icycream.common.util.FluidStackSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.VanillaIngredientSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ShapelessFluidRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapelessFluidRecipe> {
    public static final ShapelessFluidRecipeSerializer INSTANCE = new ShapelessFluidRecipeSerializer("shapeless_fluid_recipe");
    public static final ShapelessFluidRecipeSerializer MIXING = new ShapelessFluidRecipeSerializer("mixing");
    public static final ShapelessFluidRecipeSerializer EXTRACTING = new ShapelessFluidRecipeSerializer("extracting");
    public static final ShapelessFluidRecipeSerializer MACERATING = new ShapelessFluidRecipeSerializer("macerating");
    public static final ShapelessFluidRecipeSerializer FREEZING = new ShapelessFluidRecipeSerializer("freezing");

    public ShapelessFluidRecipeSerializer(String name) {
        setRegistryName(IcyCream.MODID, name);
        IRecipeSerializer.register(getRegistryName().toString(), this);
    }

    @Override
    public ShapelessFluidRecipe read(ResourceLocation recipeId, JsonObject json) {
        List<Ingredient> ingredients = new ArrayList<>();
        if (json.has("ingredients"))
            for (JsonElement jsonElement : json.getAsJsonArray("ingredients")) {
                ingredients.add(CraftingHelper.getIngredient(jsonElement));
            }

        List<FluidStack> fluidStacks = new ArrayList<>();
        if (json.has("inputFluids"))
            for (JsonElement jsonElement : json.getAsJsonArray("inputFluids")) {
                fluidStacks.add(FluidStackSerializer.getFluidStack(jsonElement.getAsJsonObject()));
            }
        ItemStack output = ItemStack.EMPTY;
        if (json.has("result"))
            output = CraftingHelper.getItemStack(json.getAsJsonObject("result"), false);

        FluidStack outputFluid = null;
        if (json.has("outputFluid"))
            outputFluid = FluidStackSerializer.getFluidStack(json.getAsJsonObject("outputFluid"));
        int ticks = json.get("ticks").getAsInt();
        return new ShapelessFluidRecipe(recipeId, ingredients, fluidStacks, output, outputFluid, ticks);
    }

    @Nullable
    @Override
    public ShapelessFluidRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        return null;
    }

    @Override
    public void write(PacketBuffer buffer, ShapelessFluidRecipe recipe) {
        buffer.writeInt(recipe.ingredients.size());
        for (Ingredient ingredient : recipe.ingredients) {
            VanillaIngredientSerializer.INSTANCE.write(buffer, ingredient);
        }
        buffer.writeInt(recipe.fluidInputs.size());
        for (FluidStack fluidStack : recipe.fluidInputs) {

        }
        buffer.writeItemStack(recipe.output);
    }
}
