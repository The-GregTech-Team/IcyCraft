package icycream.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import icycream.IcyCream;
import icycream.common.gui.AbstractMachineContainer;
import icycream.common.gui.RefrigeratorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GuiRefrigeratorScreen extends AbstractGuiMachineScreen<RefrigeratorContainer> {
    public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(IcyCream.MODID, "textures/gui/refrigerator_gui.png");

    public GuiRefrigeratorScreen(RefrigeratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn, 175, 165);
    }

    @Override
    public ITextComponent getTitle() {
        return new StringTextComponent("Refrigerator");
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     *
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        int x0 = 20;
        int y0 = 30;
        int L = 18;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                float progress = this.container.getProgress(i);
                int xStart = x0 + i * L;
                int yStart = y0 + j * L;
                int xEnd = xStart + 18;
                int yEnd = yStart + 2;
                fillGradient(xStart, yStart, xEnd, yEnd, 0xFF0000, 0xFFFF00);
            }
        }
    }

    /**
     * Draws the background layer of this container (behind the items).
     *
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
    }
}
