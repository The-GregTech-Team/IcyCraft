package icycream.common.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;

public class RefrigeratorContainer extends AbstractMachineContainer {
    public RefrigeratorContainer(int id, PlayerInventory playerInventory, ContainerType<?> containerType) {
        super(id, playerInventory, containerType);
    }
}
