package icycream.common.block;

import icycream.common.item.Ingredient;
import icycream.common.tile.TileEntityIcecreamBucket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author lyt
 * 冰激凌桶
 * /give Dev icycream:icecream_bucket{"ingredient":"APPLE"} 1
 */
public class BlockIcecreamBucket extends Block implements ITileEntityProvider {

    public BlockIcecreamBucket(Properties properties) {
        super(properties);
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     *
     * @param worldIn
     * @param pos
     * @param state
     * @param placer
     * @param stack
     */

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if(!worldIn.isRemote) {
            TileEntityIcecreamBucket tileEntity = (TileEntityIcecreamBucket) worldIn.getTileEntity(pos);
            tileEntity.setIngredient(Ingredient.valueOf(stack.getTag() != null ? stack.getTag().getString("ingredient") : "DEFAULT"));
            tileEntity.setCount(64);
            worldIn.notifyBlockUpdate(pos, state, state, 2);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityIcecreamBucket();
    }
}
