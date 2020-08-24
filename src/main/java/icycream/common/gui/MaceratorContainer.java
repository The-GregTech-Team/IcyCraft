package icycream.common.gui;

import icycream.common.tile.SlotInput;
import icycream.common.tile.SlotOutput;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class MaceratorContainer extends AbstractMachineContainer {
    public static final ContainerType<MaceratorContainer> TYPE = (ContainerType<MaceratorContainer>) IForgeContainerType.create(
            (int windowId, PlayerInventory inv, PacketBuffer data)
                    -> new MaceratorContainer(windowId, inv, data.readBlockPos(), Minecraft.getInstance().world.getWorld(), new ProgressIntArray(), data.readCompoundTag()))
            .setRegistryName("macerator_container");

    public MaceratorContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, IIntArray progressIntArray, CompoundNBT liquidTag) {
        super(id, playerInventory, TYPE, pos, world, progressIntArray, liquidTag);
    }

    public MaceratorContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, IIntArray progressIntArray) {
        super(id, playerInventory, TYPE, pos, world, progressIntArray);
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
        return ItemStack.EMPTY;
    }

    @Override
    protected void addCustomSlots() {
        /**
         * 给container添加slots
         */
        //左方输入物品
        addSlot(new SlotInput(itemInventoryInput, 0, 80, 19));
        //右方输出物品
        addSlot(new SlotOutput(itemInventoryOutput, 0, 80, 55));
    }
}
