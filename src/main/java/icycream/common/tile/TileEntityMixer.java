package icycream.common.tile;

import icycream.common.fluid.FluidInventory;
import icycream.common.gui.MixerContainer;
import icycream.common.recipes.IMachineProcessor;
import icycream.common.recipes.MachineRecipe;
import icycream.common.registry.BlockRegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import javax.crypto.Mac;

/**
 * 搅拌机TE
 */
public class TileEntityMixer extends TileEntity implements ITickableTileEntity,INamedContainerProvider,IMachineProcessor {

    public static final int TYPE_ITEMSTACK = 0;

    public static final int TYPE_FLUIDSTACK = 1;

    private Logger logger = LogManager.getLogger(getClass());

    private MachineRecipe currentRecipe;

    private float progress;

    private Inventory inventoryItem = new Inventory(4);

    private FluidInventory fluidInventory = new FluidInventory(4, 8000);

    public TileEntityMixer() {
        super(BlockRegistryHandler.mixer);
    }

    public TileEntityMixer(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TileEntityMixer(World world) {
        super(BlockRegistryHandler.mixer);
        this.world = world;
    }

    @Override
    public void read(CompoundNBT compound) {
        ListNBT items = compound.getList("items", TYPE_ITEMSTACK);
        for (int i = 0; i < items.size(); i++) {
            if(i >= inventoryItem.getSizeInventory()) {
                break;
            }
            inventoryItem.setInventorySlotContents(i, ItemStack.read(items.getCompound(i)));
        }
        fluidInventory.readFromNBT(compound);
        currentRecipe = MachineRecipe.readFromNBT(compound);
        progress = compound.getFloat("progress");
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT items = new ListNBT();
        for (int i = 0; i < inventoryItem.getSizeInventory(); i++) {
            ItemStack stackInSlot = inventoryItem.getStackInSlot(i);
            CompoundNBT compoundNBT = new CompoundNBT();
            if(stackInSlot != null) {
                stackInSlot.write(compoundNBT);
            }
            items.add(compoundNBT);
        }
        compound.put("items",items);
        fluidInventory.writeToNBT(compound);
        if(currentRecipe != null) {
            currentRecipe.writeToNBT(compound);
            compound.putFloat("progress", progress);
        }
        return super.write(compound);
    }

    @Override
    public void tick() {
        if(!this.world.isRemote) {
           //server logic
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Mixer");
    }

    /**
     * 实现这个方法来创建后端container，这是服务端实际存放物品和液体的地方
     * @param id
     * @param playerInventory
     * @param playerEntity
     * @return
     */
    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return MixerContainer.createMixerContainer(id, playerInventory, inventoryItem, fluidInventory);
    }

    @Override
    public MachineRecipe getCurrentRecipe() {
        return null;
    }

    @Override
    public float getProgress() {
        return 0;
    }
}
