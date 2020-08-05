package icycream.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import icycream.IcyCream;
import icycream.common.gui.MixerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * @author lyt
 */
public class GuiMixerScreen extends AbstractGuiMachineScreen<MixerContainer> {
    public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(IcyCream.MODID, "textures/gui/mixer_gui.png");

    public GuiMixerScreen(MixerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn, 175, 165);
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

}
