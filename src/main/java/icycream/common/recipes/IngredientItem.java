package icycream.common.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class IngredientItem implements IIngredient {
    ItemStack ingredient;
    public IngredientItem(String namespace, String name, int count) {
        ingredient = new ItemStack(Registry.ITEM.getOrDefault(new ResourceLocation(namespace, name)), count);
    }

    public IngredientItem() {

    }

    @Override
    public IngredientType getType() {
        return IngredientType.ITEM;
    }

    @Override
    public ItemStack getItemIngredient() {
        return ingredient;
    }

    @Override
    public void writeToNBT(CompoundNBT nbt) {
        nbt.putString("type", "ITEM");
        CompoundNBT body = new CompoundNBT();
        ingredient.write(body);
        nbt.put("item", body);
    }

    @Override
    public void readFromNBT(CompoundNBT nbt) {
        CompoundNBT item = nbt.getCompound("item");
        ingredient = ItemStack.read(item);
    }
}
