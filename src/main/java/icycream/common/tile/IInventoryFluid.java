package icycream.common.tile;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IInventoryFluid extends IInventory {
    IFluidHandler getFluidHandler();
}
