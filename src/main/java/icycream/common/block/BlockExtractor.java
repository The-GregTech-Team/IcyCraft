package icycream.common.block;

import icycream.common.tile.TileEntityExtractor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 榨汁姬
 * 从水果中提取汁液
 */
public class BlockExtractor extends BlockMachine {
    public BlockExtractor(Properties properties) {
        super(properties);
     }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityExtractor((World) worldIn);
    }
}
