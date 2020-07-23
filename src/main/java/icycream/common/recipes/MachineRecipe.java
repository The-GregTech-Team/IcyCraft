package icycream.common.recipes;

import com.google.common.collect.Lists;
import icycream.common.fluid.FluidInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.fluids.FluidStack;

import java.util.Iterator;
import java.util.List;

/**
 * 搅拌机合成
 */
public class MachineRecipe {
    /**
     * 原料 可为物品或者液体
     */
    private List<IIngredient> ingredients;

    /**
     * 物品合成结果
     */
    private ItemStack itemResult;

    /**
     * 液体合成结果
     */
    private FluidStack fluidResult;

    /**
     * 加工时间
     */
    private int processingTicks;

    /**
     * 反序列化专用
     */
    public MachineRecipe() {

    }

    public MachineRecipe(List<IIngredient> ingredients, ItemStack itemResult, FluidStack fluidResult, int processingTicks) {
        this.ingredients = ingredients;
        this.itemResult = itemResult;
        this.fluidResult = fluidResult;
        this.processingTicks = processingTicks;
    }
    /**
     * 每命中一个材料就从中移除一个
     *
     * @param inputItems
     * @param inputFluids
     * @return
     */
    public boolean canAccept(Inventory inputItems, FluidInventory inputFluids) {
        List<IIngredient> toMatch = Lists.newArrayList(ingredients);
        Iterator<IIngredient> iterator = toMatch.iterator();
        while (iterator.hasNext()) {
            IIngredient ingredient = iterator.next();
            if (ingredient.getType() == IngredientType.ITEM) {
                ItemStack itemIngredient = ingredient.getItemIngredient();
                for (int i = 0; i < inputItems.getSizeInventory(); i++) {
                    ItemStack input = inputItems.getStackInSlot(i);
                    if (input != null && input.getItem() == itemIngredient.getItem() && input.getCount() >= itemIngredient.getCount()) {
                        iterator.remove();
                    }
                }
            } else {
                FluidStack fluidIngredient = ingredient.getFluidIngredient();
                for (int i = 0; i < inputFluids.getSize(); i++) {
                    FluidStack input = inputFluids.getFluidStackAt(i);
                    if (input != null && input.getFluid() == fluidIngredient.getFluid() && input.getAmount() > fluidIngredient.getAmount()) {
                        iterator.remove();
                    }
                }

            }
        }
        return toMatch.isEmpty();
    }

    /**
     * 合成时从输入仓扣除对应的物品
     * @param inputItems
     * @param inputFluids
     */
    public void consume(Inventory inputItems, FluidInventory inputFluids) {
        for (IIngredient ingredient : ingredients) {
            if (ingredient.getType() == IngredientType.ITEM) {
                ItemStack itemIngredient = ingredient.getItemIngredient();
                for (int i = 0; i < inputItems.getSizeInventory(); i++) {
                    ItemStack input = inputItems.getStackInSlot(i);
                    if (input != null && input.getItem() == itemIngredient.getItem() && input.getCount() >= itemIngredient.getCount()) {
                        input.shrink(itemIngredient.getCount());
                    }
                }
            } else {
                FluidStack fluidIngredient = ingredient.getFluidIngredient();
                for (int i = 0; i < inputFluids.getSize(); i++) {
                    FluidStack input = inputFluids.getFluidStackAt(i);
                    if (input != null && input.getFluid() == fluidIngredient.getFluid() && input.getAmount() > fluidIngredient.getAmount()) {
                        input.shrink(fluidIngredient.getAmount());
                    }
                }
            }
        }
    }


    public ItemStack getItemResult() {
        return itemResult;
    }

    public FluidStack getFluidResult() {
        return fluidResult;
    }

    public int getProcessingTicks() {
        return processingTicks;
    }

    public void writeToNBT(CompoundNBT nbt) {
        ListNBT listNBT = new ListNBT();
        ingredients.forEach(e -> {
            CompoundNBT compoundNBT = new CompoundNBT();
            e.writeToNBT(compoundNBT);
            listNBT.add(compoundNBT);
        });
        nbt.put("ingredients", listNBT);
        CompoundNBT resultitem = new CompoundNBT();
        itemResult.write(resultitem);
        nbt.put("itemResult", resultitem);
        CompoundNBT resultfluid = new CompoundNBT();
        fluidResult.writeToNBT(resultfluid);
        nbt.put("fluidResult", resultfluid);
        nbt.putInt("processingTicks", processingTicks);
    }


    public static MachineRecipe readFromNBT(CompoundNBT nbt) {
        MachineRecipe machineRecipe = new MachineRecipe();
        ListNBT listNBT = (ListNBT) nbt.get("ingredients");
        if(listNBT == null) {
            return null;
        }
        machineRecipe.ingredients = Lists.newArrayList();
        listNBT.forEach(e -> {
            CompoundNBT compoundNBT = (CompoundNBT) e;
            IngredientType type = IngredientType.valueOf(compoundNBT.getString("type"));
            IIngredient ingredient;
            if(type == IngredientType.ITEM) {
                ingredient = new IngredientItem();

            } else {
                ingredient = new IngredientFluid();
            }
            ingredient.readFromNBT(compoundNBT);
            machineRecipe.ingredients.add(ingredient);
        });
        machineRecipe.itemResult = ItemStack.read(nbt.getCompound("itemResult"));
        machineRecipe.fluidResult = FluidStack.loadFluidStackFromNBT(nbt.getCompound("fluidResult"));
        machineRecipe.processingTicks = nbt.getInt("processingTicks");
        return machineRecipe;
    }
}
