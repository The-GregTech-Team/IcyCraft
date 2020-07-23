package icycream.common.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;


public interface IIngredient {
    IngredientType getType();

    default FluidStack getFluidIngredient() {
        if(getType() == IngredientType.ITEM) {
            throw new UnsupportedOperationException("not supported on ITEM ingredient");
        }
        return null;
    }

    default ItemStack getItemIngredient() {
        if(getType() == IngredientType.FLUID) {
            throw new UnsupportedOperationException("not supported on FLUID ingredient");
        }
        return null;
    }

    void writeToNBT(CompoundNBT nbt);

    void readFromNBT(CompoundNBT nbt);
}
