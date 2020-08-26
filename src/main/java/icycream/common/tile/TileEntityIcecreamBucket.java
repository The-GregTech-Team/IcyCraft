package icycream.common.tile;

import icycream.common.item.Ingredient;
import icycream.common.registry.BlockRegistryHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityIcecreamBucket extends TileEntity {
    private int count = 0;

    private Ingredient ingredient;

    public TileEntityIcecreamBucket() {
        super(BlockRegistryHandler.bucket);
    }
    public TileEntityIcecreamBucket(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.ingredient = Ingredient.valueOf(compound.getString("ingredient"));
        this.count = compound.getInt("count");
    }

    /**
     * Called when the chunk's TE update tag, gotten from {@link #getUpdateTag()}, is received on the client.
     * <p>
     * Used to handle this tag in a special way. By default this simply calls {@link #readFromNBT(NBTTagCompound)}.
     *
     * @param tag The {@link NBTTagCompound} sent from {@link #getUpdateTag()}
     */
    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        ingredient = Ingredient.valueOf(tag.getString("ingredient"));
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
     */
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT updateTag = super.getUpdateTag();
        updateTag.putString("ingredient",ingredient.name());
        return updateTag;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putString("ingredient", ingredient.name());
        compound.putInt("count", count);
        return super.write(compound);

    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
