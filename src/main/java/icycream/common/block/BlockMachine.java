package icycream.common.block;

import icycream.common.tile.AbstractTileEntityMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class BlockMachine extends ContainerBlock {
    public static IntegerProperty STATE_RUNNING = IntegerProperty.create("running", 0, 1);
    public BlockMachine(Properties properties) {
        super(properties);
        setDefaultState();
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     *
     * @param state
     * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
     */
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE_RUNNING);
        super.fillStateContainer(builder);
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
                    AbstractTileEntityMachine tileEntity = (AbstractTileEntityMachine) worldIn.getTileEntity(pos);
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

    protected void setDefaultState() {
        setDefaultState(stateContainer.getBaseState().with(STATE_RUNNING, 0));
    }
}
