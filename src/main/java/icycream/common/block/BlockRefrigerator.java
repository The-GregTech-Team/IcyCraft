package icycream.common.block;

import icycream.common.tile.TileEntityRefrigerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 冰箱，冻冰激凌
 * @author lyt
 */
public class BlockRefrigerator extends BlockMachine {
    public static IntegerProperty STATE_TEMP = IntegerProperty.create("templevel", 0, 3);

    public BlockRefrigerator(Properties properties) {
        super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE_TEMP);
        super.fillStateContainer(builder);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityRefrigerator(worldIn);
    }

    @Override
    protected void setDefaultState() {
        setDefaultState(stateContainer.getBaseState().with(STATE_TEMP, 3));
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     * 用于设置冰箱初始温度
     * @param worldIn
     * @param pos
     * @param state
     * @param placer
     * @param stack
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntityRefrigerator tileEntity = (TileEntityRefrigerator) worldIn.getTileEntity(pos);
        tileEntity.setInitTemperature(getTemperature(pos));

    }

    /**
     * 温度
     * 海拔64m及以下稳定30度
     * 每上升8m下降3度
     */
    protected int getTemperature(BlockPos pos) {
        return 30 - pos.getY() > 64 ? ((pos.getY() - 64) / 8) * 3 : 0;
    }
}