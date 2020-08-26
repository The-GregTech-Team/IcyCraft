package icycream.common.item;

import icycream.IcyCream;
import icycream.common.tile.TileEntityIcecreamBucket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Objects;

public class ItemSpoon extends Item {
    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     *
     * @param worldIn
     * @param playerIn
     * @param handIn
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack handle = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            RayTraceResult rayTraceResult = playerIn.pick(20.0D, 0.0F, false);
            Item icecreamBall = Registry.ITEM.getOrDefault(new ResourceLocation(IcyCream.MODID, "icecream_ball"));
            if (rayTraceResult.getType() == BlockRayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) rayTraceResult;
                TileEntity te = worldIn.getTileEntity(blockRayTraceResult.getPos());
                if (te instanceof TileEntityIcecreamBucket) {
                    TileEntityIcecreamBucket tileEntity = (TileEntityIcecreamBucket) te;
                    Ingredient ingredient = tileEntity.getIngredient();
                    if (tileEntity.getCount() > 0) {
                        for (int i = 0; i < playerIn.inventory.mainInventory.size(); i++) {
                            ItemStack current = playerIn.inventory.mainInventory.get(i);
                            if (current.isEmpty()) {
                                tileEntity.setCount(tileEntity.getCount() - 1);
                                ItemStack newItem = new ItemStack(icecreamBall, 1);
                                CompoundNBT nbt = new CompoundNBT();
                                nbt.putString("ingredient", ingredient.name());
                                newItem.setTag(nbt);
                                playerIn.inventory.mainInventory.set(i, newItem);
                                return ActionResult.resultSuccess(handle);
                            } else if (current.getItem() == icecreamBall) {
                                if (tileEntity.getIngredient().name().equals(Objects.requireNonNull(current.getTag()).getString("ingredient"))) {
                                    if (current.getCount() >= current.getMaxStackSize()) {
                                        continue;
                                    }
                                    current.setCount(current.getCount() + 1);
                                    tileEntity.setCount(tileEntity.getCount() - 1);
                                }
                                return ActionResult.resultSuccess(handle);
                            } else {
                                continue;
                            }
                        }
                        return ActionResult.resultFail(handle);
                    } else {
                        return ActionResult.resultFail(handle);
                    }
                } else {
                    return ActionResult.resultFail(handle);
                }
            } else {
                return ActionResult.resultFail(handle);
            }
        } else  {
            return ActionResult.resultPass(handle);
        }
    }

    public ItemSpoon(Properties properties) {
        super(properties);
    }
}
