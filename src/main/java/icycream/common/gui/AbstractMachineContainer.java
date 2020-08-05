package icycream.common.gui;

import com.google.common.collect.Lists;
import icycream.common.fluid.IFluidInventory;
import icycream.common.tile.AbstractTileEntityMachine;
import icycream.common.tile.SlotFluid;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class AbstractMachineContainer extends Container {
    protected IIntArray progressIntArray;

    protected IInventory itemInventoryInput;

    protected IInventory itemInventoryOutput;

    protected IFluidInventory fluidInventoryInput;

    protected IFluidInventory fluidInventoryOutput;

    protected List<SlotFluid> slotFluidList = Lists.newArrayList();

    public AbstractMachineContainer(int id, PlayerInventory playerInventory, ContainerType<?> containerType) {
        super(containerType, id);
        //偷懒copy原版了
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        //快捷栏

        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public AbstractMachineContainer(int id, PlayerInventory playerInventory, ContainerType<?> containerType, BlockPos pos, World world, IIntArray progressIntArray, CompoundNBT liquidTag) {
        this(id, playerInventory, containerType, pos, world, progressIntArray);
        AbstractTileEntityMachine tileEntity = (AbstractTileEntityMachine) world.getTileEntity(pos);
        tileEntity.readLiquidFromCompoundNBT(liquidTag);
    }


    public AbstractMachineContainer(int id, PlayerInventory playerInventory, ContainerType<?> containerType, BlockPos pos, World world, IIntArray progressIntArray) {
        this(id, playerInventory, containerType);
        AbstractTileEntityMachine tileEntity = (AbstractTileEntityMachine) world.getTileEntity(pos);
        itemInventoryInput = tileEntity.getInventoryItemInput();
        itemInventoryOutput = tileEntity.getInventoryItemOutput();
        fluidInventoryInput = tileEntity.getFluidInventoryInput();
        fluidInventoryOutput = tileEntity.getFluidInventoryOutput();
        addCustomSlots();
        this.progressIntArray = progressIntArray;
        trackIntArray(progressIntArray);

    }


    /**
     * Determines whether supplied player can use this container
     * 按照距离
     * 如果超出去自动关闭gui
     *
     * @param playerIn
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    protected void addCustomSlots() {

    }

    public List<SlotFluid> getSlotFluidList() {
        return slotFluidList;
    }

    public float getProgress() {
        float progress = (float) (((1.0) * progressIntArray.get(0)) / progressIntArray.get(1));
        return progress > 1 ? 1 : progress;
    }
}
