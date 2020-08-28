package icycream.common.recipes;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import icycream.IcyCream;
import icycream.common.util.FluidStackSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.VanillaIngredientSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Takakura-Anri
 */
public class ShapelessFluidRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapelessFluidRecipe> {
    public static ShapelessFluidRecipeSerializer INSTANCE;
    public static ShapelessFluidRecipeSerializer MIXING;
    public static ShapelessFluidRecipeSerializer EXTRACTING;
    public static ShapelessFluidRecipeSerializer MACERATING;

    public static void register() {
        INSTANCE = new ShapelessFluidRecipeSerializer("shapeless_fluid_recipe");
        MIXING = new ShapelessFluidRecipeSerializer("mixing");
        EXTRACTING = new ShapelessFluidRecipeSerializer("extracting");
        MACERATING = new ShapelessFluidRecipeSerializer("macerating");
    }

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

        FluidStack outputFluid = FluidStack.EMPTY;
        if (json.has("outputFluid"))
            outputFluid = FluidStackSerializer.getFluidStack(json.getAsJsonObject("outputFluid"));
        int ticks = json.get("ticks").getAsInt();

        switch (json.get("type").getAsString()) {
            case "icycream:mixing":
                return new MixerRecipe(recipeId, ingredients, fluidStacks, output, outputFluid, ticks);
            case "icycream:extracting":
                return new ExtractorRecipe(recipeId, ingredients, fluidStacks, output, outputFluid, ticks);
            case "icycream:macerating":
                return new MaceratorRecipe(recipeId, ingredients, fluidStacks, output, outputFluid, ticks);
            default:
                return new ShapelessFluidRecipe(recipeId, ingredients, fluidStacks, output, outputFluid, ticks);
        }
    }

    @Nullable
    @Override
    public ShapelessFluidRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        List<Ingredient> ingredients = Lists.newArrayList();
        List<FluidStack> inputFluids = Lists.newArrayList();
        ShapelessFluidRecipe shapelessFluidRecipe;
        ItemStack output = ItemStack.EMPTY;
        FluidStack outputFluid = FluidStack.EMPTY;
        String type = buffer.readString();
        ResourceLocation resourceLocation = buffer.readResourceLocation();
        int itemsSize = buffer.readVarInt();
        for (int i = 0; i < itemsSize; i++) {
            ingredients.add(VanillaIngredientSerializer.INSTANCE.parse(buffer));
        }
        int fluidsSize = buffer.readVarInt();
        for (int i = 0; i < fluidsSize; i++) {
            inputFluids.add(new FluidStack(Registry.FLUID.getOrDefault(buffer.readResourceLocation()), buffer.readVarInt()));
        }
        boolean hasItemOutput = buffer.readBoolean();
        if(hasItemOutput) {
            output = buffer.readItemStack();
        }
        boolean hasFluidOutput = buffer.readBoolean();
        if(hasFluidOutput) {
            outputFluid = buffer.readFluidStack();
        }
        int ticks = buffer.readVarInt();
        switch (type) {
            case "mixing":
                shapelessFluidRecipe = new MixerRecipe(resourceLocation, ingredients, inputFluids, output, outputFluid, ticks);break;
            case "macerating":
                shapelessFluidRecipe = new MaceratorRecipe(resourceLocation, ingredients, inputFluids, output, outputFluid, ticks);break;
            case "extracting":
                shapelessFluidRecipe = new ExtractorRecipe(resourceLocation, ingredients, inputFluids, output, outputFluid, ticks);break;
            default:
                shapelessFluidRecipe = new ShapelessFluidRecipe(resourceLocation, ingredients, inputFluids, output, outputFluid, ticks);break;
        }
        return shapelessFluidRecipe;
    }

    @Override
    public void write(PacketBuffer buffer, ShapelessFluidRecipe recipe) {
        String type;
        if(recipe instanceof MixerRecipe) {
            type = "mixing";
        } else if (recipe instanceof MaceratorRecipe) {
            type = "macerating";
        } else if (recipe instanceof ExtractorRecipe) {
            type = "extracting";
        } else {
            type = "shapeless_fluid_recipe";
        }
        buffer.writeString(type);
        buffer.writeResourceLocation(recipe.getId());
        buffer.writeVarInt(recipe.ingredients.size());
        for (Ingredient ingredient : recipe.ingredients) {
            VanillaIngredientSerializer.INSTANCE.write(buffer, ingredient);
        }
        buffer.writeVarInt(recipe.fluidInputs.size());
        for (FluidStack fluidStack : recipe.fluidInputs) {
            buffer.writeFluidStack(fluidStack);
        }
        boolean hasOutputItem = recipe.output != null && !recipe.output.isEmpty();
        buffer.writeBoolean(hasOutputItem);
        if(hasOutputItem) {
            buffer.writeItemStack(recipe.output);
        }
        boolean hasOutputFluid = recipe.outputFluid != null && !recipe.outputFluid.isEmpty();
        buffer.writeBoolean(hasOutputFluid);
        if(hasOutputFluid) {
            buffer.writeFluidStack(recipe.outputFluid);
        }
        buffer.writeVarInt(recipe.ticks);
    }
}
