package icycream.common.recipes.special;

import icycream.common.recipes.RecipeTypes;
import icycream.common.util.RecipeManagerHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Map;

public class IcecreamFreezingRecipe extends SpecialRecipe {

    private ItemStack ingredient;

    private ItemStack output;

    private int ticks;

    private int tempLevel;

    public IcecreamFreezingRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return true;
    }

    public boolean matches(CraftingInventory inv, World worldIn, int tempLevel) {
        return inv.getStackInSlot(0).getItem() == ingredient.getItem() && tempLevel < this.tempLevel;
    }

    public int getTicks(int tempLevel) {
        return (int) ((1 - 0.12 * (tempLevel - this.tempLevel)) * ticks);
    }

    /**
     * 产生一个冰激凌桶，里面装上对应颜色的东西
     * @param inv
     * @return
     */
    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width == 1 && height == 1;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SpecialRecipes.CRAFTING_SPECIAL_ICECREAM_FREEZING;
    }
}
