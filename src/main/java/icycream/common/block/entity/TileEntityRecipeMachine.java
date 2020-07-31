package icycream.common.block.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Plain old design
 * 虽然我很不乐意再写第N遍，但是*我没得选*
 */
public abstract class TileEntityRecipeMachine<T extends TileEntityRecipeMachine<T>> extends TileEntity implements ISidedInventory, ITickableTileEntity, Machine<T> {
    protected Inventory inventory;
    protected IItemHandler invWrapper;
    protected int[] inputSlots, outputSlots;

    @Nullable
    protected IRecipe<T> recipe;
    protected int progress;

    public TileEntityRecipeMachine(TileEntityType<T> tileEntityTypeIn, int slots) {
        super(tileEntityTypeIn);
        inventory = new Inventory(slots);
        invWrapper = new InvWrapper(inventory);
//        this.inputSlots = inputSlots;
//        this.outputSlots = outputSlots;

        addListener(inv -> {
            recipe = findRecipe();
        });
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return LazyOptional.of(() -> (T) invWrapper);
        else return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public int[] getSlotsForFace(@Nonnull Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
        return invWrapper.insertItem(index, stack, true) != stack;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return !invWrapper.extractItem(index, stack.getCount(), true).isEmpty();
    }

    @Override
    public void tick() {
        if (canWork()) {
            progress();
        } else if (canFinish()) { // 结束合成
            onCraftingFinish();
            if (canWorkBegin()) onCraftingStart();
        } else if (canWorkBegin()) {
            onCraftingStart();
        }
    }

    @Override
    public boolean isWorking() {
        return recipe != null;
    }

    @Override
    public boolean canWorkBegin() {
        return recipe != null;
    }

    @Override
    public boolean canWork() {
        return progress > 0 && progress < 200;
    }

    @Override
    public boolean canFinish() {
        return progress == 200;
    }

    @Override
    public void onCraftingStart() {
        progress = 1;
    }

    @Override
    public void onCraftingFinish() {
        progress = 0;


        recipe = findRecipe();
    }

    @Override
    public void progress() {
        progress++;
    }

///////////////// DELEGATION METHODS //////////////
    //<editor-fold> Delegations
    public void addListener(IInventoryChangedListener listener) {
        inventory.addListener(listener);
    }

    public void removeListener(IInventoryChangedListener listener) {
        inventory.removeListener(listener);
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return inventory.decrStackSize(index, count);
    }

    public ItemStack func_223374_a(Item p_223374_1_, int p_223374_2_) {
        return inventory.func_223374_a(p_223374_1_, p_223374_2_);
    }

    public ItemStack addItem(ItemStack stack) {
        return inventory.addItem(stack);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return inventory.removeStackFromSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.setInventorySlotContents(index, stack);
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public void markDirty() {
        inventory.markDirty();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return inventory.isUsableByPlayer(player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    public void fillStackedContents(RecipeItemHelper helper) {
        inventory.fillStackedContents(helper);
    }
    //</editor-fold>
}
