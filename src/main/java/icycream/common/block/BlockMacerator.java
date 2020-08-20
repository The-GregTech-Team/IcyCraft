package icycream.common.block;

import icycream.common.tile.TileEntityMacerator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 磨粉机
 */
public class BlockMacerator extends BlockMachine {
    public BlockMacerator(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityMacerator((World) worldIn);
    }
}
