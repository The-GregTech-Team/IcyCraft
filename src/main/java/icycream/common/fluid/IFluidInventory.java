package icycream.common.fluid;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public interface IFluidInventory extends IFluidHandler {

    /**
     * get size of inv
     * @return size
     */
    int getTanks();

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
    @Nonnull
    FluidStack getFluidInTank(int index);

    /**
     * drain x mb fluid from idx
     * @param index
     * @return fluid stack with amount that drained from inventory
     */
    FluidStack drainFluidAt(int index, int mb);

    /**
     * @return how much fluid was filled
     */
    int addFluidAt(int index, FluidStack fluidStack);

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
