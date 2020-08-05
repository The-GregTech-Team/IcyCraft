package icycream.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;

public abstract class BlockMachine extends ContainerBlock {
    public static IntegerProperty STATE = IntegerProperty.create("running", 0, 1);
    public BlockMachine(Properties properties) {
        super(properties);
        setDefaultState(stateContainer.getBaseState().with(STATE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        super.fillStateContainer(builder);
    }
}
