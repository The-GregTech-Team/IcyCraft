package icycream.common.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotInput extends Slot {
    @Override
    public void onSlotChange(ItemStack oldStackIn, ItemStack newStackIn) {
        super.onSlotChange(oldStackIn, newStackIn);
    }

    public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
}
