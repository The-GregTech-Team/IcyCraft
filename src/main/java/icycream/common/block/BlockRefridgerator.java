package icycream.common.block;

import icycream.common.tile.TileEntityRefrigerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

/**
 * 冰箱，冻冰激凌
 */
public class BlockRefridgerator extends BlockMachine {
    private static IntegerProperty STATE_TEMP = IntegerProperty.create("templevel", 0, 3);

    public BlockRefridgerator(Properties properties) {
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
}
