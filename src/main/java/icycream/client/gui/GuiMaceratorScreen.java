package icycream.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import icycream.IcyCream;
import icycream.common.gui.MaceratorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GuiMaceratorScreen extends AbstractGuiMachineScreen<MaceratorContainer> {
    public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(IcyCream.MODID, "textures/gui/macerator_gui.png");

    public GuiMaceratorScreen(MaceratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn, 175, 165);
    }

    @Override
    public ITextComponent getTitle() {
        return new StringTextComponent("Macerator");
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
        //176,0 -> 176 + 16, 27 + 16

        //begin: (80, 37)

        //加工图标左上角(176, 0)
        //宽 = 16
        float progress = this.container.getProgress();
        blit(i + 80, j + 37, 176, 0, 16,(int) (16 * progress));
    }
}
