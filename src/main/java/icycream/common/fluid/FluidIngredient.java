package icycream.common.fluid;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fluids.FluidAttributes;

import java.awt.Color;
import java.util.Map;

/**
 * 学着原版写
 */
public abstract class FluidIngredient extends WaterFluid {
    /**
     * Creates the fluid attributes object, which will contain all the extended values for the fluid that aren't part of the vanilla system.
     * Do not call this from outside. To retrieve the values use {@link Fluid#getAttributes()}
     */
    protected Color color;// = new Color(0xffb800);

    protected Tag<Fluid> tag; // = new FluidTags.Wrapper(new ResourceLocation("icycream:fluid_ingredient"));

    protected String name;

    protected static Map<String, Source> srcs = Maps.newHashMap();

    protected static Map<String, Flowing> flowings = Maps.newHashMap();



    /**
     * mod液体需要在这里实现方法
     * 指定液体的纹理，颜色，等。
     * 甚至还有密度嗷
     * @return
     */
    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(new ResourceLocation("block/water_still"),
                new ResourceLocation("block/water_flow")).
                overlay(new ResourceLocation("block/water_overlay")).
                color(color.getRGB()).build(this);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public Fluid getFlowingFluid() {
        return srcs.get(name);
    }

    @Override
    public Fluid getStillFluid() {
        return flowings.get(name);
    }

    /**
     * 蛋液桶
     * @return
     */
    @Override
    public Item getFilledBucket() {
        return super.getFilledBucket();
    }

    @Override
    public boolean canDisplace(IFluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return p_215665_5_ == Direction.DOWN && !p_215665_4_.isIn(tag);
    }

    @Override
    public BlockState getBlockState(IFluidState state) {
        return null;
    }

    public static class Source extends FluidIngredient {

        public Source(Color color, String name) {
            this.color = color;
            this.tag = new FluidTags.Wrapper(new ResourceLocation("icycream", name));
            this.name = name;
            srcs.put(name, this);
        }

        @Override
        public boolean isSource(IFluidState state) {
            return true;
        }

        @Override
        public int getLevel(IFluidState p_207192_1_) {
            return p_207192_1_.get(LEVEL_1_8);
        }
    }

    public static class Flowing extends FluidIngredient {
        public Flowing(Color color, String name) {
            this.color = color;
            this.tag = new FluidTags.Wrapper(new ResourceLocation("icycream", name + "_flowing"));
            this.name = name;
            flowings.put(name, this);
        }

        @Override
        public boolean isSource(IFluidState state) {
            return false;
        }

        @Override
        public int getLevel(IFluidState p_207192_1_) {
            return 8;
        }
    }
}