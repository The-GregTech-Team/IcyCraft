package icycream.common.recipes;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;

public class IngredientFluid implements IIngredient{

    /**
     * use readFromNBT
     */
    public IngredientFluid() {

    }

    public IngredientFluid(String namespace, String name, int amount) {
        this.ingredient = new FluidStack(Registry.FLUID.getOrDefault(new ResourceLocation(namespace, name)), amount);
    }
    private FluidStack ingredient;
    @Override
    public FluidStack getFluidIngredient() {
        return ingredient;
    }

    @Override
    public void writeToNBT(CompoundNBT nbt) {
        nbt.putString("type", "FLUID");
        CompoundNBT body = new CompoundNBT();
        body.putString("namespace", ingredient.getFluid().getRegistryName().getNamespace());
        body.putString("name", ingredient.getFluid().getRegistryName().getPath());
        body.putInt("amount", ingredient.getAmount());
        nbt.put("fluid", body);
    }

    @Override
    public void readFromNBT(CompoundNBT nbt) {
        CompoundNBT fluid = nbt.getCompound("fluid");
        ingredient = new FluidStack(Registry.FLUID.getOrDefault(new ResourceLocation(fluid.getString("namespace"), fluid.getString("name"))), fluid.getInt("amount"));
    }

    @Override
    public IngredientType getType() {
        return IngredientType.FLUID;
    }
}
