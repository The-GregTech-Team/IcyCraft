package icycream.client.gui;

import icycream.common.gui.MaceratorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiMaceratorScreen extends AbstractGuiMachineScreen<MaceratorContainer> {
    public GuiMaceratorScreen(MaceratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn, int xSize, int ySize) {
        super(screenContainer, inv, titleIn, xSize, ySize);
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

    }
}