package icycream.common.gui;

import com.google.common.collect.Lists;
import icycream.client.gui.GuiMixerScreen;
import icycream.common.fluid.IFluidInventory;
import icycream.common.registry.Util;
import icycream.common.tile.SlotFluid;
import icycream.common.tile.SlotInput;
import icycream.common.tile.SlotOutput;
import icycream.common.tile.TileEntityMixer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;

import java.util.List;

/**
 * 搅拌机服务器端container
 */
public class MixerContainer extends Container {

    private static ContainerType<MixerContainer> type = IForgeContainerType.create((int windowId, PlayerInventory inv, PacketBuffer data) -> new MixerContainer(windowId, inv, data.readBlockPos(), Minecraft.getInstance().world.getWorld(), new ProgressIntArray(), data.readCompoundTag()));

    static {
        try {
            Util.unfreezeRegistry((SimpleRegistry) Registry.MENU);
            Registry.register(Registry.MENU, "mixer_container", type);
            Util.freezeRegistry((SimpleRegistry) Registry.MENU);
            ScreenManager.registerFactory(type, GuiMixerScreen::new);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private IIntArray progressIntArray;

    private IInventory itemInventoryInput;

    private IInventory itemInventoryOutput;

    private IFluidInventory fluidInventoryInput;

    private IFluidInventory fluidInventoryOutput;

    private List<SlotFluid> slotFluidList = Lists.newArrayList();

    protected MixerContainer(int id, PlayerInventory playerInventory) {
        super(type, id);
        //玩家物品栏

        //偷懒copy原版了
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        //快捷栏

        for(int k = 0; k < 9; ++k) {
            addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public MixerContainer(int id, PlayerInventory playerInventory, BlockPos pos, World world, IIntArray progressIntArray, CompoundNBT liquidTag) {
        this(id, playerInventory, pos, world, progressIntArray);
        TileEntityMixer tileEntity = (TileEntityMixer) world.getTileEntity(pos);
        tileEntity.writeLiquidInfoToCompoundNBT(liquidTag);
    }


    public MixerContainer(int id,PlayerInventory playerInventory,  BlockPos pos, World world, IIntArray progressIntArray) {
        this(id, playerInventory);
        TileEntityMixer tileEntity = (TileEntityMixer) world.getTileEntity(pos);
        itemInventoryInput = tileEntity.getInventoryItemInput();
        itemInventoryOutput = tileEntity.getInventoryItemOutput();
        fluidInventoryInput = tileEntity.getFluidInventoryInput();
        fluidInventoryOutput = tileEntity.getFluidInventoryOutput();
        /**
         * 给container添加slots
         */
        //左方输入物品
        addSlot(new SlotInput(itemInventoryInput, 0, 19,17));
        addSlot(new SlotInput(itemInventoryInput, 1, 56, 17));
        //右方输出物品
        addSlot(new SlotOutput(itemInventoryOutput, 0, 116, 35));

        //左方输入液体
        slotFluidList.add(new SlotFluid(fluidInventoryInput, 0, 19, 53));
        slotFluidList.add(new SlotFluid(fluidInventoryInput, 1, 56, 53));

        //右方输出液体
        slotFluidList.add(new SlotFluid(fluidInventoryOutput, 0, 145,35));
        this.progressIntArray = progressIntArray;
        trackIntArray(progressIntArray);

    }

    /**
     * Determines whether supplied player can use this container
     * 按照距离
     * 如果超出去自动关闭gui
     * @param playerIn
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public List<SlotFluid> getSlotFluidList() {
        return slotFluidList;
    }

    public float getProgress() {
        float progress = (float) (((1.0) * progressIntArray.get(0)) / progressIntArray.get(1));
        return progress > 1 ? 1 : progress;
    }
}
