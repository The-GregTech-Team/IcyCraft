package icycream.common.tile;

import icycream.common.item.Ingredient;
import icycream.common.registry.BlockRegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TileEntityIcecreamBucket extends TileEntity {
    private int count = 0;

    private Ingredient ingredient = Ingredient.DEFAULT;

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
        updateTag.putString("ingredient", ingredient.name());
        return updateTag;
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT updateTag = super.getUpdateTag();
        updateTag.putString("ingredient", ingredient.name());
        SUpdateTileEntityPacket sUpdateTileEntityPacket = new SUpdateTileEntityPacket(getPos(), 0, updateTag);
        return sUpdateTileEntityPacket;
    }

    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT tag = pkt.getNbtCompound();
        ingredient = Ingredient.valueOf(tag.getString("ingredient"));
        BlockState blockState = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, blockState, blockState, 2);
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
