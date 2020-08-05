package icycream.common.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;

public class ExtractorContainer extends AbstractMachineContainer {
    public ExtractorContainer(int id, PlayerInventory playerInventory, ContainerType<?> containerType) {
        super(id, playerInventory, containerType);
    }
}
