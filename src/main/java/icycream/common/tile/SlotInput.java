package icycream.common.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public class SlotInput extends Slot {
    public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
}
