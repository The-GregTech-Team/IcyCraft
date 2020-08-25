package icycream.common.fluid;

import com.google.common.collect.Maps;
import icycream.IcyCream;
import icycream.common.item.Ingredient;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
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

    protected Ingredient ingredient;

    protected static Map<String, Source> SRCS = Maps.newHashMap();

    protected static Map<String, Flowing> FLOWINGS = Maps.newHashMap();

    protected static Item FILLED_BUCKET = Registry.ITEM.getOrDefault(new ResourceLocation(IcyCream.MODID, "bucket"));



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
        return FLOWINGS.get(name);
    }

    @Override
    public Fluid getStillFluid() {
        return SRCS.get(name);
    }

    /**
     * 蛋液桶
     * @return
     */
    @Override
    public Item getFilledBucket() {
        return FILLED_BUCKET;
    }

    @Override
    public boolean canDisplace(IFluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return p_215665_5_ == Direction.DOWN && !p_215665_4_.isIn(tag);
    }

    @Override
    public BlockState getBlockState(IFluidState state) {
        return Registry.BLOCK.getOrDefault(new ResourceLocation(IcyCream.MODID, "block_liquid_" + name)).getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public static class Source extends FluidIngredient {

        public Source(Color color, String name) {
            this.color = color;
            this.tag = new FluidTags.Wrapper(new ResourceLocation(IcyCream.MODID, name));
            this.name = name;
            SRCS.put(name, this);
        }

        public Source(Ingredient ingredient, String name) {
            this.color = ingredient.getColor();
            this.ingredient = ingredient;
            this.tag = new FluidTags.Wrapper(new ResourceLocation(IcyCream.MODID, name));
            this.name = name;
            SRCS.put(name, this);
        }

        @Override
        public boolean isSource(IFluidState state) {
            return true;
        }

        @Override
        public int getLevel(IFluidState p_207192_1_) {
            return 8;
        }
    }

    public static class Flowing extends FluidIngredient {
        public Flowing(Color color, String name) {
            this.color = color;
            this.tag = new FluidTags.Wrapper(new ResourceLocation(IcyCream.MODID, name + "_flowing"));
            this.name = name;
            FLOWINGS.put(name, this);
        }

        public Flowing(Ingredient ingredient, String name) {
            this.color = ingredient.getColor();
            this.ingredient = ingredient;
            this.tag = new FluidTags.Wrapper(new ResourceLocation(IcyCream.MODID, name + "_flowing"));
            this.name = name;
            FLOWINGS.put(name, this);
        }


        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        @Override
        public boolean isSource(IFluidState state) {
            return false;
        }

        @Override
        public int getLevel(IFluidState p_207192_1_) {
            return p_207192_1_.get(LEVEL_1_8);
        }
    }
}