package icycream.common.gui;

import icycream.common.tile.SlotFluid;
import icycream.common.tile.SlotInput;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ExtractorContainer extends AbstractMachineContainer {
    public static final ContainerType<ExtractorContainer> TYPE = (ContainerType<ExtractorContainer>) IForgeContainerType.create(
            (int windowId, PlayerInventory inv, PacketBuffer data)
                    -> new ExtractorContainer(windowId, inv, data.readBlockPos(), Minecraft.getInstance().world.getWorld(), new ProgressIntArray(), data.readCompoundTag()))
            .setRegistryName("extractor_container");

    public ExtractorContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, IIntArray progressIntArray, CompoundNBT liquidTag) {
        super(id, playerInventory, TYPE, pos, world, progressIntArray, liquidTag);
    }

    public ExtractorContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, IIntArray progressIntArray) {
        super(id, playerInventory, TYPE, pos, world, progressIntArray);
    }

    @Override
    protected void addCustomSlots() {
        /**
         * 给container添加slots
         */
        //左方输入物品
        addSlot(new SlotInput(itemInventoryInput, 0, 80, 19));

        //右方输出液体
        slotFluidList.add(new SlotFluid(fluidInventoryOutput, 0, 80, 55));
    }
}
