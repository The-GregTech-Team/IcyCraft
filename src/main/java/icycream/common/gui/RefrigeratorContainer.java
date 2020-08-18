package icycream.common.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;

/**
 * 冰箱服务器container
 * @author lyt
 */
public class RefrigeratorContainer extends AbstractMachineContainer {

    public static ContainerType<RefrigeratorContainer> type = (ContainerType<RefrigeratorContainer>) IForgeContainerType.create(
            (int windowId, PlayerInventory inv, PacketBuffer data)
                    -> new RefrigeratorContainer(windowId, inv, data.readBlockPos(), Minecraft.getInstance().world.getWorld(), new ProgressIntArray()))
            .setRegistryName("refrigerator_container");

    public RefrigeratorContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, IIntArray progressIntArray) {
        super(id, playerInventory, type, pos, world, progressIntArray);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     *
     * @param playerIn
     * @param index
     */
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return super.transferStackInSlot(playerIn, index);
    }

    /**
     * 冰箱的slot
     * 3x3的原料
     * 1个冰块输入
     */
    @Override
    protected void addCustomSlots() {
        int x0 = 62;
        int y0 = 17;
        int L = 18;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new Slot(this.itemInventoryInput, 3 * i + j, x0 + i * L, y0 + j * L));
            }
        }
        this.addSlot(new Slot(this.itemInventoryInput, 9, 134, 35));
    }
}
