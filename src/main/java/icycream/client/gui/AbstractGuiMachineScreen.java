package icycream.client.gui;

import com.google.common.collect.Lists;
import icycream.common.gui.AbstractMachineContainer;
import icycream.common.tile.SlotFluid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 搅拌机gui
 */
public abstract class AbstractGuiMachineScreen<T extends AbstractMachineContainer> extends ContainerScreen<T> {

    protected SlotFluid hoveredSlotFluid;

    public AbstractGuiMachineScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn, int xSize, int ySize) {
        super(screenContainer, inv, titleIn);
    }


    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);
        if (hoveredSlotFluid != null && this.minecraft.player.inventory.getItemStack().isEmpty()
                && hoveredSlotFluid.inventory.getFluidInTank(hoveredSlotFluid.slotNumber).getAmount() > 0) {
            this.renderTooltip(hoveredSlotFluid.inventory.getFluidInTank(hoveredSlotFluid.slotNumber), mouseX, mouseY);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        for (SlotFluid slotFluid : container.getSlotFluidList()) {
            if (isSlotSelected(slotFluid, mouseX, mouseY)) {
                this.hoveredSlotFluid = slotFluid;
            }
            slotFluid.drawFluid(this.guiLeft, this.guiTop);
        }
        renderHoveredToolTip(mouseX, mouseY);
        this.hoveredSlotFluid = null;
    }

    protected boolean isSlotSelected(SlotFluid slotIn, double mouseX, double mouseY) {
        return isPointInRegion(slotIn.xPos, slotIn.yPos, 16, 16, mouseX, mouseY);
    }

    protected void renderTooltip(FluidStack fluidStack, int mouseX, int mouseY) {
        FontRenderer font = Minecraft.getInstance().getRenderManager().getFontRenderer();
        this.renderTooltip(this.getTooltipFromItem(fluidStack), mouseX, mouseY, (font == null ? this.font : font));
    }

    protected List<ITextComponent> getTooltipFromFluidStack(FluidStack fluidStack) {
        List<ITextComponent> list = Lists.newArrayList();
        list.add(fluidStack.getDisplayName());
        list.add(new StringTextComponent("amount: " + fluidStack.getAmount()));
        return list;
    }

    public List<String> getTooltipFromItem(FluidStack fluidStack) {
        List<ITextComponent> list = getTooltipFromFluidStack(fluidStack);
        return list.stream().map(e -> e.getFormattedText()).collect(Collectors.toList());
    }

}
