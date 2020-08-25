package icycream.common.recipes.special;

import icycream.IcyCream;
import icycream.common.recipes.IcyCreamRecipeUid;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;

public class SpecialRecipes {
    public static final SpecialRecipeSerializer<IcecreamFreezingRecipe> CRAFTING_SPECIAL_ICECREAM_FREEZING = register("freezing", new SpecialRecipeSerializer<>(IcecreamFreezingRecipe::new));

    public static <T extends IRecipe<?>> SpecialRecipeSerializer<T> register(String name, SpecialRecipeSerializer<T> serializer) {
        IRecipeSerializer.register(IcyCream.MODID + ":" + name, serializer.setRegistryName(IcyCream.MODID, name));
        return serializer;
    }

    public static void registerAll() {
        // Does nothing but trigger class loading
    }
}
