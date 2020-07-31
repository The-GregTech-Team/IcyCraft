package icycream.common.fluid;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

public class FluidInventory implements IFluidInventory {
    public static final Joiner joiner = Joiner.on(",");

    private FluidStack[] fluids;

    private int capacity;

    public FluidInventory(int size, int capacity) {
        this.fluids = new FluidStack[size];
        this.capacity = capacity;
    }

    /**
     * get size of inv
     *
     * @return size
     */
    @Override
    public int getSize() {
        return fluids.length;
    }

    /**
     * if slot at position is empty
     *
     * @param index
     * @return
     */
    @Override
    public boolean getSlotEmptyAt(int index) {
        FluidStack fluid = fluids[index];
        return fluid != null ? fluid.getAmount() <= 0 : true;
    }

    /**
     * get fluid stack at pos
     *
     * @param index
     * @return fluid stack at index
     */
    @Override
    public FluidStack getFluidStackAt(int index) {
        return fluids[index] != null ? fluids[index] : new FluidStack(Registry.FLUID.getOrDefault(new ResourceLocation("minecraft","water")), 0);
    }

    /**
     * drain x mb fluid from idx
     *
     * @param index
     * @param mb
     * @return fluid stack with amount that drained from inventory
     */
    @Override
    public FluidStack drainFluidAt(int index, int mb) {
        FluidStack fluid = fluids[index];
        Preconditions.checkNotNull(fluid != null);
        Fluid innerFluid = fluid.getFluid();
        int amount = fluid.getAmount();
        int amountLeft = amount > mb ? amount - mb : 0;
        int drained = amount > mb ? amount - mb : amount;
        fluid.setAmount(amountLeft);
        return new FluidStack(innerFluid, drained);
    }

    /**
     * @param index
     * @param fluidStack
     * @return fluid stack remaining of input, if no fluid left return 0mb stack
     */
    @Override
    public FluidStack addFluidAt(int index, FluidStack fluidStack) {
        FluidStack fluid = fluids[index];
        if(fluid == null) {
            fluids[index] = new FluidStack(fluidStack.getFluid(), fluidStack.getAmount());
            fluidStack.setAmount(0);
        } else {
            if(fluid.getFluid().getRegistryName().equals(fluidStack.getFluid().getRegistryName()))
            {
                int add = fluid.getAmount() + fluidStack.getAmount();
                int remain = add - capacity;
                fluid.setAmount(add > capacity ? capacity : add);
                fluidStack.setAmount(remain > 0 ? remain : 0);
            } else {
                if(fluid.isEmpty()) {
                    fluids[index] = new FluidStack(fluidStack.getFluid(), fluidStack.getAmount());
                    fluidStack.setAmount(0);
                }
            }
        }
        return fluidStack;
    }

    @Override
    public void setFluidAt(int index, FluidStack fluidStack) {
        if(fluidStack != null) {
            fluids[index] = fluidStack;
        }
    }

    /**
     * deserialize
     *
     * @param nbt
     */
    @Override
    public void readFromNBT(CompoundNBT nbt) {
       ListNBT listNBT = (ListNBT) nbt.get("fluids");
       if(listNBT != null) {
           for (int i = 0; i < listNBT.size(); i++) {
               fluids[i] = FluidStack.loadFluidStackFromNBT(listNBT.getCompound(i));
           }
       }
    }

    /**
     * serialize
     *
     * @param nbt
     * @return
     */
    @Override
    public CompoundNBT writeToNBT(CompoundNBT nbt) {
        ListNBT listNBT = new ListNBT();
        for (FluidStack fluid : fluids) {
            CompoundNBT compoundNBT = new CompoundNBT();
            if(fluid != null) {
                fluid.writeToNBT(compoundNBT);
            } else {
                new FluidStack(Fluids.WATER, 0).writeToNBT(compoundNBT);
            }
        }
        nbt.put("fluids", listNBT);
        return nbt;
    }

    public int getCapacity() {
        return capacity;
    }
}
