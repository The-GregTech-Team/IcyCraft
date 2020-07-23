package icycream.common.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot {
    public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     * 任何物品都无法被放入输出栏中
     * @param stack
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }
}
