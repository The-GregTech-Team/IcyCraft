package icycream.common.block;

import icycream.IcyCream;
import icycream.common.tile.TileEntityMixer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

/**
 * @author lyt
 * 搅拌鸡蛋，面粉等做蛋筒用
 */
public class BlockMixer extends BlockMachine {
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
                        NetworkHooks.openGui((ServerPlayerEntity) player, tileEntity, packetBuffer -> {
                            packetBuffer.writeBlockPos(tileEntity.getPos());
                            CompoundNBT compoundNBT = new CompoundNBT();
                            tileEntity.writeLiquidInfoToCompoundNBT(compoundNBT);
                            packetBuffer.writeCompoundTag(compoundNBT);
                        });
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
