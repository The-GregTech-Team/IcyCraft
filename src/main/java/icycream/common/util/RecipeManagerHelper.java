package icycream.common.util;

import icycream.common.recipes.ShapelessFluidRecipeSerializer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

/**
 * @author lyt
 */
public class RecipeManagerHelper {
    public static MethodHandle getRecipesMethodHandle = null;

    public static void loadRecipes() {
        Method method = ObfuscationReflectionHelper.findMethod(RecipeManager.class, "func_215366_a", IRecipeType.class);
        try {
            getRecipesMethodHandle = MethodHandles.lookup().unreflect(method);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ShapelessFluidRecipeSerializer.register();
    }

    public static <C extends IInventory, T extends IRecipe<C>> Map<ResourceLocation, T> getRecipes(IRecipeType<T> recipeTypeIn, RecipeManager recipeManager) {
        if (getRecipesMethodHandle != null) {
            try {
                return (Map<ResourceLocation, T>) getRecipesMethodHandle.bindTo(recipeManager).invoke(recipeTypeIn);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            throw new RuntimeException("PRIVATE GO TO HELL");
        }
        return Collections.emptyMap();
    }
}
