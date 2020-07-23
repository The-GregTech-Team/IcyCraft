package icycream.common.block;

import icycream.common.tile.TileEntityMixer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.OptionalInt;

/**
 * 搅拌鸡蛋，面粉等做蛋筒用
 */
public class BlockMixer extends ContainerBlock {
    public BlockMixer(Properties properties) {
        super(properties);
    }

    /**
     * 虽说是deprecated，但是依然需要覆盖该方法来打开gui
     * 基本逻辑: 根据raytraceResult和距离来决定是否打开gui
     * tile需要实现INamedContainerProvider
     * 客户端的话直接返回pass
     * @param state
     * @param worldIn
     * @param pos
     * @param player
     * @param handIn
     * @param hit
     * @return
     */
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(worldIn.isRemote) {
            //client always pass
            return ActionResultType.SUCCESS;
        }
        else {
            if (hit.getType() == RayTraceResult.Type.BLOCK) {
                if (player.getPosition().withinDistance(pos, 3)) {
                    //server only
                    TileEntityMixer tileEntity = (TileEntityMixer) worldIn.getTileEntity(pos);
                    if (tileEntity != null) {
                        player.openContainer(tileEntity);
                        return ActionResultType.SUCCESS;
                    } else {
                        return ActionResultType.FAIL;
                    }
                } else {
                    return ActionResultType.PASS;
                }
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityMixer((World) worldIn);
    }
}
