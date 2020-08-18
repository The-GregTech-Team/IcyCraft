package icycream.common.gui;

import icycream.common.tile.SlotFluid;
import icycream.common.tile.SlotInput;
import icycream.common.tile.SlotOutput;
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
 * @author lyt
 * 搅拌机服务器端container
 */
public class MixerContainer extends AbstractMachineContainer {

    public static ContainerType<MixerContainer> type = (ContainerType<MixerContainer>) IForgeContainerType.create(
            (int windowId, PlayerInventory inv, PacketBuffer data)
                    -> new MixerContainer(windowId, inv, data.readBlockPos(), Minecraft.getInstance().world.getWorld(), new ProgressIntArray(), data.readCompoundTag()))
            .setRegistryName("mixer_container");


    public MixerContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, IIntArray progressIntArray, CompoundNBT liquidTag) {
        super(id, playerInventory, type, pos, world, progressIntArray, liquidTag);
    }

    public MixerContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, IIntArray progressIntArray) {
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
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack().copy();
            if(index < 36) {
                mergeItemStack(stack, 36, 37, true);
            } else {
                mergeItemStack(stack, 0, 35, true);
            }
            slot.putStack(stack);
            if(!stack.isEmpty()) {
                slot.onSlotChanged();
            }
            return stack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    protected void addCustomSlots() {
        /**
         * 给container添加slots
         */
        //左方输入物品
        addSlot(new SlotInput(itemInventoryInput, 0, 19, 17));
        addSlot(new SlotInput(itemInventoryInput, 1, 56, 17));
        //右方输出物品
        addSlot(new SlotOutput(itemInventoryOutput, 0, 116, 35));

        //左方输入液体
        slotFluidList.add(new SlotFluid(fluidInventoryInput, 0, 19, 53));
        slotFluidList.add(new SlotFluid(fluidInventoryInput, 1, 56, 53));

        //右方输出液体
        slotFluidList.add(new SlotFluid(fluidInventoryOutput, 0, 145, 35));
    }
}
