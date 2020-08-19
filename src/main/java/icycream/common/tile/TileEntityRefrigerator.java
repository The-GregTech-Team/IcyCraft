package icycream.common.tile;

import icycream.common.fluid.FluidInventory;
import icycream.common.gui.RefrigeratorContainer;
import icycream.common.gui.ScalableIntArray;
import icycream.common.recipes.RecipeTypes;
import icycream.common.recipes.ShapelessFluidRecipe;
import icycream.common.registry.BlockRegistryHandler;
import icycream.common.registry.ServerHandler;
import icycream.common.util.RecipeManagerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 冰箱TE
 * 每个格子的处理都是独立的
 * @author lyt
 */
public class TileEntityRefrigerator extends AbstractTileEntityMachine {

    public TileEntityRefrigerator() {
        super(BlockRegistryHandler.refrigerator);
        /**
         * 存储每个格子的处理情况
         * 每3个数字一个格子，分别是：
         * index - 物品栏中的位置
         * progress - 进度tick
         * progressMax - 最大进度tick
         */
        this.progress = new ScalableIntArray();
        this.inventoryItemInput = new Inventory(10) {
            /**
             * Removes a stack from the given slot and returns it.
             *
             * @param index
             */
            @Override
            public ItemStack removeStackFromSlot(int index) {
                ItemStack stack = super.removeStackFromSlot(index);
                if (index < 9) {
                    //移除后这个格子的加工进度重置

                }
                return stack;
            }

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
                if (index < 9 && world != null) {
                    checkInventoryForRecipe();
                }
                //markDirty();
            }
        };
        this.inventoryItemOutput = new Inventory(0);
        this.fluidInventoryInput = new FluidInventory(0, 8000);
        this.fluidInventoryOutput = new FluidInventory(0, 8000);
        this.recipeType = RecipeTypes.FREEZING;
    }

    public TileEntityRefrigerator(IBlockReader world) {
        this();
        this.world = (World) world;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Refrigerator");
    }

    protected boolean checkInventoryForRecipe(int i) {
        for (ShapelessFluidRecipe shapelessFluidRecipe : RecipeManagerHelper.getRecipes(recipeType, ServerHandler.getServerInstance().getRecipeManager()).values()) {
            if (shapelessFluidRecipe.matches(inventoryItemInput, i)) {
                currentRecipe = shapelessFluidRecipe;
                progress.set(1, currentRecipe.ticks);
                updateWorkBlockState(pos);
                return true;
            }
        }
        return false;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        int[] processingProgresses = compound.getIntArray("processing");
        for (int i = 0; i < processingProgresses.length; i++) {
            progress.set(i, processingProgresses[i]);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT write = super.write(compound);
        write.putIntArray("processing", ((ScalableIntArray) progress).toIntArray());
        return write;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new RefrigeratorContainer(id, playerInventory, pos, world, progress);
    }
}
