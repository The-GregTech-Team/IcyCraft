package icycream.common.tile;

import icycream.common.fluid.FluidInventory;
import icycream.common.gui.MixerContainer;
import icycream.common.recipes.RecipeTypes;
import icycream.common.registry.BlockRegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author lyt
 * 搅拌机TE
 */
public class TileEntityMixer extends AbstractTileEntityMachine {

    public TileEntityMixer() {
        super(BlockRegistryHandler.mixer);
        this.inventoryItemInput = new Inventory(2) {
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
        this.inventoryItemOutput = new Inventory(1);
        this.fluidInventoryInput = new FluidInventory(2, 8000);
        this.fluidInventoryOutput = new FluidInventory(1, 8000);
        this.recipeType = RecipeTypes.MIXING;
    }

    public TileEntityMixer(World world) {
        super(BlockRegistryHandler.mixer);
        this.world = world;
    }


    public TileEntityMixer(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new MixerContainer(id, playerInventory, getPos(), world, progress);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Mixer");
    }
}
