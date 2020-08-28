package icycream.client.color;

import icycream.common.item.Ingredient;
import icycream.common.tile.TileEntityIcecreamBucket;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

import javax.annotation.Nullable;

public class IcecreamBucketTinter implements IBlockColor {
    @Override
    public int getColor(BlockState state, @Nullable ILightReader lightReader, @Nullable BlockPos blockPos, int tintIndex) {
         if(tintIndex == 0) {
            return Ingredient.DEFAULT.getColor().getRGB();
        } else {
            if(blockPos != null) {
                TileEntityIcecreamBucket tileEntity = (TileEntityIcecreamBucket) lightReader.getTileEntity(blockPos);
                return tileEntity.getIngredient() != null ? tileEntity.getIngredient().getColor().getRGB() : Ingredient.DEFAULT.getColor().getRGB();
            } else {
                return Ingredient.DEFAULT.getColor().getRGB();
            }
        }
    }
}
