package icycream.common.tile;

import icycream.common.fluid.FluidInventory;
import icycream.common.gui.RefrigeratorContainer;
import icycream.common.registry.BlockRegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TileEntityRefrigerator  extends AbstractTileEntityMachine {

    public TileEntityRefrigerator() {
        super(BlockRegistryHandler.refrigerator);
        this.inventoryItemInput = new Inventory(10) {
            /**
             * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
             *
             * @param index
             * @param stack
             */
            @Override
            public void setInventorySlotContents(int index, ItemStack stack) {
                /**
                 * world is null on loading phase
                 */
                super.setInventorySlotContents(index, stack);
                if(currentRecipe == null && world != null) {
                    checkInventoryForRecipe();
                }
                //markDirty();
            }
        };
        this.inventoryItemOutput = new Inventory(0);
        this.fluidInventoryInput = new FluidInventory(0, 8000);
        this.fluidInventoryOutput = new FluidInventory(0, 8000);
    }

    public TileEntityRefrigerator(IBlockReader world) {
        this();
        this.world = (World) world;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Refrigerator");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new RefrigeratorContainer(id, playerInventory, pos, world, progress);
    }
}
