package icycream.common.fluid;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidInventory {

    /**
     * get size of inv
     * @return size
     */
    int getSize();

    /**
     * if slot at position is empty
     * @param index
     * @return
     */
    boolean getSlotEmptyAt(int index);

    /**
     * get fluid stack at pos
     * @param index
     * @return fluid stack at index
     */
    FluidStack getFluidStackAt(int index);

    /**
     * drain x mb fluid from idx
     * @param index
     * @return fluid stack with amount that drained from inventory
     */
    FluidStack drainFluidAt(int index, int mb);

    /**
     *
     * @param index
     * @param fluidStack
     * @return fluid stack remaining of input, if no fluid left return 0mb stack
     */
    FluidStack addFluidAt(int index, FluidStack fluidStack);

    void setFluidAt(int index, FluidStack fluidStack);

    /**
     * deserialize
     * @param nbt
     */
    void readFromNBT(CompoundNBT nbt);

    /**
     * serialize
     * @param nbt
     * @return
     */
    CompoundNBT writeToNBT(CompoundNBT nbt);

}
