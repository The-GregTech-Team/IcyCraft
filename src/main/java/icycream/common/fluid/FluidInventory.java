package icycream.common.fluid;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

/**
 * @author lyt, Takakura-Anri
 */
public class FluidInventory implements IFluidInventory {

    private FluidTank[] tanks;

    private int capacity;

    public FluidInventory(int size, int capacity) {
        this.tanks = new FluidTank[size];
        for (int i = 0; i < size; i++) {
            final FluidTank tank = new FluidTank(capacity);
            tank.setValidator(stack -> {
                if (tank.isEmpty()) return true;
                return tank.getFluid().getRawFluid() == stack.getRawFluid();
            });
            tanks[i] = tank;
        }
        this.capacity = capacity;
    }

    /**
     * get size of inv
     *
     * @return size
     */
    @Override
    public int getTanks() {
        return tanks.length;
    }

    /**
     * if slot at position is empty
     *
     * @param index
     * @return
     */
    @Override
    public boolean getSlotEmptyAt(int index) {
        return tanks[index].isEmpty();
    }

    /**
     * get fluid stack at pos
     *
     * @param index
     * @return fluid stack at index
     */
    @Nonnull
    @Override
    public FluidStack getFluidInTank(int index) {
        return tanks[index].getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        return tanks[tank].getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return tanks[tank].isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        for (FluidTank tank : tanks) {
            if (tank.isFluidValid(resource)) return tank.fill(resource, action);
        }
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return null;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return null;
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
        return tanks[index].drain(mb, FluidAction.EXECUTE);
    }

    /**
     * @return how much fluid was filled
     */
    @Override
    public int addFluidAt(int index, FluidStack fluidStack) {
        return tanks[index].fill(fluidStack, FluidAction.EXECUTE);
    }

    @Override
    public void setFluidAt(int index, FluidStack fluidStack) {
        if(fluidStack != null) {
            tanks[index].setFluid(fluidStack);
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
                tanks[i].readFromNBT(listNBT.getCompound(i));
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
        for (FluidTank tank : tanks) {
            CompoundNBT compoundNBT = new CompoundNBT();
            if(tank != null) {
                tank.writeToNBT(compoundNBT);
            } else {
                FluidStack.EMPTY.writeToNBT(compoundNBT);
            }
        }
        nbt.put("fluids", listNBT);
        return nbt;
    }

    public int getCapacity() {
        return capacity;
    }
}
