package icycream.common.tile;

import icycream.common.fluid.FluidInventory;
import icycream.common.gui.ExtractorContainer;
import icycream.common.gui.ProgressIntArray;
import icycream.common.recipes.RecipeTypes;
import icycream.common.registry.BlockRegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TileEntityExtractor extends AbstractTileEntityMachine
{
    public TileEntityExtractor() {
        super(BlockRegistryHandler.extracter);
        this.inventoryItemInput = new Inventory(1) {
            @Override
            public void setInventorySlotContents(int index, ItemStack stack) {
                super.setInventorySlotContents(index, stack);
                if(currentRecipe == null && world != null) {
                    currentRecipe = checkInventoryForRecipe();
                }
            }
        };
        this.progress = new ProgressIntArray();
        this.inventoryItemOutput = new Inventory(1);
        this.fluidInventoryInput = new FluidInventory(2, 8000);
        this.fluidInventoryOutput = new FluidInventory(1, 8000);
        this.recipeType = RecipeTypes.EXTRACTING;
    }

    public TileEntityExtractor(World world) {
        this();
        this.world = world;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new ExtractorContainer(id, playerInventory, getPos(), world, progress);
    }
}
