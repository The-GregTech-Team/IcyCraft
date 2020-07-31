package icycream.client.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import icycream.IcyCream;
import icycream.common.gui.MixerContainer;
import icycream.common.tile.SlotFluid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 搅拌机gui
 */
public class GuiMixerScreen extends ContainerScreen<MixerContainer> {

    private SlotFluid hoveredSlotFluid;

    public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(IcyCream.MODID, "textures/gui/mixer_gui.png");

    public GuiMixerScreen(MixerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = 175;
        this.ySize = 165;
    }

    /**
     * Draws the background layer of this container (behind the items).
     *ø
     * @param partialTicks
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_BACKGROUND);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        /**
         以 x y 为起点绘制纹理中以 u v 为起点，长w宽h的矩形
         */
        blit(i, j, 0, 0, this.xSize, this.ySize);
        //176,0 -> 202, 27

        //begin: (77, 28)

        //加工图标左上角(176, 0)
        //宽 = 202 - 176 = 26
        float progress = this.container.getProgress();
        blit(i + 77, j + 28, 176, 0, (int) (26 * progress),27);
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);
        if ( hoveredSlotFluid != null && this.minecraft.player.inventory.getItemStack().isEmpty()
                && hoveredSlotFluid.inventory.getFluidInTank(hoveredSlotFluid.slotNumber).getAmount() > 0) {
            this.renderTooltip(hoveredSlotFluid.inventory.getFluidInTank(hoveredSlotFluid.slotNumber), mouseX, mouseY);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        for (SlotFluid slotFluid : container.getSlotFluidList()) {
            if(isSlotSelected(slotFluid, mouseX, mouseY)) {
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
