package icycream.common.recipes.special;

import icycream.IcyCream;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;

public class SpecialRecipes {
    public static final SpecialRecipeSerializer<IcecreamMixingRecipe> CRAFTING_SPECIAL_ICECREAM_MIXING = register("crafting_special_icecream_mixing", new SpecialRecipeSerializer<>(IcecreamMixingRecipe::new));

    public static <T extends IRecipe<?>> SpecialRecipeSerializer<T> register(String name, SpecialRecipeSerializer<T> serializer) {
        IRecipeSerializer.register(IcyCream.MODID + ":" + name, serializer.setRegistryName(IcyCream.MODID, name));
        return serializer;
    }

    public static void registerAll() {
        // Does nothing but trigger class loading
    }
}
