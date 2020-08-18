package icycream.common.tile;

import icycream.common.gui.MaceratorContainer;
import icycream.common.gui.MixerContainer;
import icycream.common.recipes.RecipeTypes;
import icycream.common.registry.BlockRegistryHandler;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author lyt
 * 磨粉机TE
 */
public class TileEntityMacerator  extends AbstractTileEntityMachine {
    public TileEntityMacerator() {
        super(BlockRegistryHandler.macerator);
        this.inventoryItemInput = this.inventoryItemInput = new Inventory(1) {
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
        this.inventoryItemOutput = new Inventory(1);
        this.recipeType = RecipeTypes.MACERATING;
    }

    public TileEntityMacerator(World world) {
        this();
        this.world = world;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Macerator");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new MaceratorContainer(id, playerInventory, pos, world, progress);
    }
}
