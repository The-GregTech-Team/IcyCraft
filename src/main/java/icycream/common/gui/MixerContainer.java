package icycream.common.gui;

import com.google.common.collect.Lists;
import icycream.client.gui.GuiMixerScreen;
import icycream.common.fluid.FluidInventory;
import icycream.common.fluid.IFluidInventory;
import icycream.common.registry.Util;
import icycream.common.tile.SlotFluid;
import icycream.common.tile.SlotInput;
import icycream.common.tile.SlotOutput;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 搅拌机服务器端container
 */
public class MixerContainer extends Container {

    private static ContainerType<MixerContainer> type = new ContainerType<>(MixerContainer::createMixerContainer);

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
    private IInventory itemInventory;

    private IFluidInventory fluidInventory;

    private PlayerInventory playerInventory;

    private List<SlotFluid> slotFluidList = Lists.newArrayList();

    protected MixerContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }


    public static MixerContainer createMixerContainer(int id, PlayerInventory playerInventory) {

        return createMixerContainer(id, playerInventory, new Inventory(3), new FluidInventory(3, 8000));

    }


    public static MixerContainer createMixerContainer(int id, PlayerInventory playerInventory, IInventory itemInventory, IFluidInventory fluidInventory) {
        MixerContainer container = new MixerContainer(type, id);
        container.itemInventory = itemInventory;
        container.fluidInventory = fluidInventory;
        container.playerInventory = playerInventory;
        /**
         * 给container添加slots
         */
        //左方输入物品
        container.addSlot(new SlotInput(itemInventory, 0, 11,8));
        container.addSlot(new SlotInput(itemInventory, 1, 33, 8));
        //右方输出物品
        container.addSlot(new SlotOutput(itemInventory, 2, 87, 24));

        //左方输入液体
        container.slotFluidList.add(new SlotFluid(fluidInventory, 0, 11, 40));
        container.slotFluidList.add(new SlotFluid(fluidInventory, 1, 33, 40));

        //右方输出液体
        container.slotFluidList.add(new SlotFluid(fluidInventory, 2, 111,24));
        return container;
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


    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public IFluidInventory getFluidInventory() {
        return fluidInventory;
    }

    public IInventory getItemInventory() {
        return itemInventory;
    }

    public List<SlotFluid> getSlotFluidList() {
        return slotFluidList;
    }
}
