package icycream.common.tile;

import com.mojang.blaze3d.systems.RenderSystem;
import icycream.IcyCream;
import icycream.common.fluid.FluidInventory;
import icycream.common.fluid.IFluidInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;

/**
 * 液体槽
 */
public class SlotFluid {
    
    private final int slotIndex;
    
    public final IFluidInventory inventory;
    
    public int slotNumber;
    
    public final int xPos;
    
    public final int yPos;
    
    public SlotFluid(IFluidInventory inventoryIn, int index, int xPosition, int yPosition) {
        this.inventory = inventoryIn;
        this.slotIndex = index;
        this.xPos = xPosition;
        this.yPos = yPosition;
    }

    /**
     * 添加液体
     */

    /**
     * 排出液体
     */

    /**
     * 渲染
     */
    public boolean getHasFluid() {
        return inventory.getSlotEmptyAt(slotNumber);
    }

    public FluidStack getFluid() {
        return inventory.getFluidStackAt(slotNumber);
    }

    public void drawFluid(float parentX, float parentY) {
        //FluidStack fluidStackAt = new FluidStack(Registry.FLUID.getOrDefault(new ResourceLocation(IcyCream.MODID, "fluid_egg")), 1000);
        FluidStack fluidStackAt = inventory.getFluidStackAt(slotIndex);
        if(fluidStackAt != null) {
            Fluid fluid = fluidStackAt.getFluid();
            //获取TextureAtlas
            TextureAtlasSprite fluidStillTexture = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(fluid.getAttributes().getStillTexture());
            int color = fluid.getAttributes().getColor();
            Minecraft.getInstance().getTextureManager().bindTexture(fluidStillTexture.getAtlasTexture().getTextureLocation());
            RenderSystem.enableDepthTest();
            RenderSystem.pushMatrix();
            //坐标平移到ui左上
            RenderSystem.translatef(parentX, parentY, 0);
            //获取颜色
            RenderSystem.color4f(getR(color), getG(color), getB(color), getA(color));
            //渲染
            ContainerScreen.blit(xPos, yPos, 0, 16, 16, fluidStillTexture);
            RenderSystem.popMatrix();
            RenderSystem.disableDepthTest();
        }
    }

    private float getR(int color) {
        return ((color >> 16) & 0xFF) / 255f;
    }
    private float getG(int color) {
        return ((color >> 8) & 0xFF) / 255f;
    }
    private float getB(int color) {
        return ((color >> 0) & 0xFF) / 255f;
    }
    private float getA(int color) {
        return ((color >> 24) & 0xFF) / 255f;
    }
}
