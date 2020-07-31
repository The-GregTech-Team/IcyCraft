package icycream.common.tile;

import com.google.common.collect.Lists;
import icycream.common.fluid.FluidInventory;
import icycream.common.gui.MixerContainer;
import icycream.common.gui.ProgressIntArray;
import icycream.common.recipes.IMachineProcessor;
import icycream.common.recipes.MachineRecipe;
import icycream.common.registry.BlockRegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 搅拌机TE
 */
public class TileEntityMixer extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IMachineProcessor {

    public static List<MachineRecipe> MIXER_RECIPES = Lists.newArrayList();

    public static final int TYPE_ITEMSTACK = 0;

    public static final int TYPE_FLUIDSTACK = 1;

    private Logger logger = LogManager.getLogger(getClass());

    private MachineRecipe currentRecipe;

    private ProgressIntArray progress = new ProgressIntArray();

    private Inventory inventoryItemInput = new Inventory(2) {

        /**
         * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
         *
         * @param index
         * @param stack
         */
        @Override
        public void setInventorySlotContents(int index, ItemStack stack) {
            super.setInventorySlotContents(index, stack);
            checkInventoryForRecipe();
        }
    };

    private Inventory inventoryItemOutput = new Inventory(1);

    private FluidInventory fluidInventoryInput = new FluidInventory(2, 8000);

    private FluidInventory fluidInventoryOutput = new FluidInventory(1, 8000);

    public TileEntityMixer() {
        super(BlockRegistryHandler.mixer);
    }

    public TileEntityMixer(World world) {
        super(BlockRegistryHandler.mixer);
        this.world = world;
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT compoundNBT = new CompoundNBT();
        writeLiquidInfoToCompoundNBT(compoundNBT);
        return new SUpdateTileEntityPacket(getPos(), 1, compoundNBT);
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
        if (world.isRemote && Minecraft.getInstance().player.openContainer instanceof MixerContainer) {
            CompoundNBT nbtCompound = pkt.getNbtCompound();
            readLiquidFromCompoundNBT(nbtCompound);
        }
    }

    public void writeLiquidInfoToCompoundNBT(CompoundNBT compoundNBT) {
        ListNBT inputLiquids = new ListNBT();
        for (int i = 0; i < fluidInventoryInput.getSize(); i++) {
            FluidStack fluidStackAt = fluidInventoryInput.getFluidStackAt(i);
            if(!fluidStackAt.isEmpty()) {
                CompoundNBT liquid = new CompoundNBT();
                liquid.putInt("pos",1);
                CompoundNBT n = new CompoundNBT();
                fluidStackAt.writeToNBT(n);
                liquid.put("liquid", n);
                inputLiquids.add(liquid);
            }
        }
        compoundNBT.put("inputLiquids", inputLiquids);
        if (!fluidInventoryOutput.getFluidStackAt(0).isEmpty()) {
            CompoundNBT outputFluidNBT = new CompoundNBT();
            fluidInventoryOutput.getFluidStackAt(0).writeToNBT(outputFluidNBT);
            compoundNBT.put("outputLiquid", outputFluidNBT);
        }
    }

    public void readLiquidFromCompoundNBT(CompoundNBT nbtCompound) {
        CompoundNBT outputLiquid = nbtCompound.getCompound("outputLiquid");
        if (outputLiquid != null) {
            fluidInventoryOutput.setFluidAt(0, FluidStack.loadFluidStackFromNBT(outputLiquid));
        }
        ListNBT inputLiquids = (ListNBT) nbtCompound.get("inputLiquids");
        inputLiquids.forEach(e -> {
            CompoundNBT nbt = (CompoundNBT) e;
            int pos = nbt.getInt("pos");
            FluidStack stack = FluidStack.loadFluidStackFromNBT(nbt.getCompound("liquid"));
            fluidInventoryInput.setFluidAt(pos, stack);
        });
    }

    protected boolean checkInventoryForRecipe() {
        for (MachineRecipe mixerRecipe : MIXER_RECIPES) {
            if(mixerRecipe.canAccept(inventoryItemInput, fluidInventoryInput)) {
                mixerRecipe.consume(inventoryItemInput, fluidInventoryInput);
                currentRecipe = mixerRecipe;
                return true;
            }
        }
        return false;
    }

    @Override
    public void read(CompoundNBT compound) {
        ListNBT items = (ListNBT) compound.get("items");
        if(items != null) {
            for (int i = 0; i < items.size(); i++) {
                if (i >= inventoryItemInput.getSizeInventory()) {
                    break;
                }
                inventoryItemInput.setInventorySlotContents(i, ItemStack.read(items.getCompound(i)));
            }
        }
        fluidInventoryInput.readFromNBT(compound);
        currentRecipe = MachineRecipe.readFromNBT(compound);
        progress.set(0, compound.getInt("progress"));
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT items = new ListNBT();
        for (int i = 0; i < inventoryItemInput.getSizeInventory(); i++) {
            ItemStack stackInSlot = inventoryItemInput.getStackInSlot(i);
            CompoundNBT compoundNBT = new CompoundNBT();
            if (stackInSlot != null) {
                stackInSlot.write(compoundNBT);
            }
            items.add(compoundNBT);
        }
        compound.put("items", items);
        fluidInventoryInput.writeToNBT(compound);
        if (currentRecipe != null) {
            currentRecipe.writeToNBT(compound);
            compound.putInt("progress", progress.get(0));
        }
        return super.write(compound);
    }

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            if (currentRecipe != null) {
                if (progress.get(0) >= progress.get(1)) {
                    //GT式吞材料
                    FluidStack fluidResult = currentRecipe.getFluidResult();
                    ItemStack itemResult = currentRecipe.getItemResult();
                    if (itemResult != null) {
                        ItemStack stackInSlot = inventoryItemOutput.getStackInSlot(0);
                        if (!stackInSlot.isEmpty()) {
                            if (stackInSlot.getItem() == itemResult.getItem()) {
                                int count = stackInSlot.getCount();
                                int addedCount = count + itemResult.getCount() > stackInSlot.getMaxStackSize() ? stackInSlot.getMaxStackSize() : count + itemResult.getCount();
                                stackInSlot.setCount(addedCount);
                            }
                        } else {
                            inventoryItemOutput.setInventorySlotContents(0, itemResult);
                        }
                    }
                    if (fluidResult != null) {
                        //GT式吞材料
                        fluidInventoryOutput.addFluidAt(0, fluidResult.copy());
                    }
                    if(!checkInventoryForRecipe()) {
                        currentRecipe = null;
                    }
                    progress.set(0, 0);
                    sync();
                } else {
                    progress.set(0, progress.get(0) + 1);
                    sync();
                    logger.info("progress = {}%", progress.get(0) / (progress.get(1) * 1.0) * 100);
                }
            }
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Mixer");
    }

    /**
     * 实现这个方法来创建后端container，这是服务端实际存放物品和液体的地方, server calls this
     *
     * @param id
     * @param playerInventory
     * @param playerEntity
     * @return
     */
    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new MixerContainer(id, playerInventory, getPos(), world, progress);
    }

    @Override
    public MachineRecipe getCurrentRecipe() {
        return currentRecipe;
    }


    protected void sync() {
        if(!world.isRemote) {
            SUpdateTileEntityPacket packet = this.getUpdatePacket();
            // 获取当前正在“追踪”目标 TileEntity 所在区块的玩家。
            // 之所以这么做，是因为在逻辑服务器上，不是所有的玩家都需要获得某个 TileEntity 更新的信息。
            // 比方说，有一个玩家和需要同步的 TileEntity 之间差了八千方块，或者压根儿就不在同一个维度里。
            // 这个时候就没有必要同步数据——强行同步数据实际上也没有什么用，因为大多数时候这样的操作都应会被
            // World.isBlockLoaded（func_175667_e）的检查拦截下来，避免意外在逻辑客户端上加载多余的区块。
            BlockPos pos = getPos();
            List<ServerPlayerEntity> entitiesWithinAABB = ((ServerWorld) this.world).getEntitiesWithinAABB(ServerPlayerEntity.class, new AxisAlignedBB(pos.getX() - 5, pos.getY() - 5, pos.getZ() - 5, pos.getX() + 5, pos.getY() + 5, pos.getZ() + 5));
            entitiesWithinAABB.forEach(e -> {
                e.connection.sendPacket(packet);
            });
        }
    }

    public Inventory getInventoryItemInput() {
        return inventoryItemInput;
    }

    public Inventory getInventoryItemOutput() {
        return inventoryItemOutput;
    }

    public FluidInventory getFluidInventoryInput() {
        return fluidInventoryInput;
    }

    public FluidInventory getFluidInventoryOutput() {
        return fluidInventoryOutput;
    }
}
