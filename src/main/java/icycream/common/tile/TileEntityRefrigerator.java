package icycream.common.tile;

import icycream.common.block.BlockRefrigerator;
import icycream.common.fluid.FluidInventory;
import icycream.common.gui.RefrigeratorContainer;
import icycream.common.gui.ScalableIntArray;
import icycream.common.recipes.RecipeTypes;
import icycream.common.recipes.ShapelessFluidRecipe;
import icycream.common.registry.BlockRegistryHandler;
import icycream.common.registry.ServerHandler;
import icycream.common.util.RecipeManagerHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * 冰箱TE
 * 每个格子的处理都是独立的
 *
 * @author lyt
 */
public class TileEntityRefrigerator extends AbstractTileEntityMachine {

    protected Map<Integer, ShapelessFluidRecipe> outputs = new HashMap<>();

    /**
     * 温度
     * 海拔64m及以下稳定30度
     * 每上升8m下降3度
     */
    private int initTemperature = 30;

    private int temperature = 30;

    public TileEntityRefrigerator() {
        super(BlockRegistryHandler.refrigerator);
        /**
         * 存储每个格子的处理情况
         * 每3个数字一个格子，分别是：
         * index - 物品栏中的位置
         * progress - 进度tick
         * progressMax - 最大进度tick
         */
        this.progress = new ScalableIntArray(18);
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
                if (index < 9 && world != null) {
                    checkInventoryForRecipe();
                }
            }
        };
        this.inventoryItemOutput = new Inventory(9) {
            /**
             * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
             * guis use Slot.isItemValid
             *
             * @param index
             * @param stack
             */
            @Override
            public boolean isItemValidForSlot(int index, ItemStack stack) {
                return false;
            }
        };
        this.fluidInventoryInput = new FluidInventory(1, 8000);
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

    /**
     * 检查物品栏, 遍及查找合成并缓存
     *
     * @return 是否有合成对应机器物品栏
     */
    @Override
    protected boolean checkInventoryForRecipe() {
        boolean hasThingsProcessing = false;
        FluidInventory nullFluid = new FluidInventory(0, 0);
        for (int i = 0; i < 9; i++) {
            int finalI = i;
            IInventory inv = new Inventory(1) {
                {
                    setInventorySlotContents(0, inventoryItemInput.getStackInSlot(finalI));
                }
            };
            if (outputs.get(i) == null) { // LG: 不判断一下的话每次放一个物品都会吞一个
                for (ShapelessFluidRecipe shapelessFluidRecipe : RecipeManagerHelper.getRecipes(recipeType, ServerHandler.getServerInstance().getRecipeManager()).values()) {
                    if (shapelessFluidRecipe.matches(inv, nullFluid)) {
                        outputs.put(i, shapelessFluidRecipe);
                        setProgress(i, 0, shapelessFluidRecipe.ticks);
                        hasThingsProcessing |= true;
                    }
                }
            } else if (getProgress(i) == 0 && !outputs.get(i).matches(inv, nullFluid)) { // LG: 只有在这里的时候才重新遍及检查合成
                outputs.put(i, null);
                progress.set(0, 0);
                hasThingsProcessing |= checkInventoryForRecipe();
            }
        }
        return hasThingsProcessing;
    }

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            updateWorkBlockState(pos);
            /**
             * 冰箱只有温度 < 0才会开始处理东西
             */
            if(temperature <= 0) {
                /**
                 * 9个格子一一对应每个格子都可以处理配方
                 */
                for (int i = 0; i < 9; i++) {
                    ShapelessFluidRecipe currentRecipe = outputs.get(i);
                    if (currentRecipe != null) {
                        if (getProgress(i) >= getProgressMax(i)) {
                            //处理完成
                            //GT式吞材料
                            ItemStack itemResult = currentRecipe.output;
                            if (itemResult != ItemStack.EMPTY) {
                                ItemStack stackInSlot = inventoryItemOutput.getStackInSlot(i);
                                if (!stackInSlot.isEmpty()) {
                                    if (stackInSlot.getItem() == itemResult.getItem()) {
                                        int count = stackInSlot.getCount();
                                        int addedCount = Math.min(count + itemResult.getCount(), stackInSlot.getMaxStackSize());
                                        stackInSlot.setCount(addedCount);
                                    }
                                } else {
                                    inventoryItemOutput.setInventorySlotContents(i, itemResult.copy());
                                }
                            }

                            setProgress(i, 0);
                        } else if (getProgress(i) == 0) {
                            // 合成开始
                            // 吞材料
                            currentRecipe.consume(inventoryItemInput, fluidInventoryInput);

                            setProgress(i, getProgress(i) + 1);
                        } else {
                            // 增加进度
                            setProgress(i, getProgress(i) + 1);
                        }
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new RefrigeratorContainer(id, playerInventory, pos, world, progress);
    }

    protected void setProgress(int pos, int progress, int max) {
        int base = pos * 2;
        this.progress.set(base, progress);
        this.progress.set(base + 1, max);

    }

    protected void setProgress(int pos, int progress) {
        int base = pos * 2;
        this.progress.set(base, progress);

    }

    protected int getProgress(int pos) {
        int base = pos * 2;
        return this.progress.get(base);
    }

    protected int getProgressMax(int pos) {
        int base = pos * 2;
        return this.progress.get(base + 1);
    }

    /**
     * 目前mod机器只使用int state
     * LG: 需要重新设置 TileEntity 不然会出现bug
     *
     * @param pos
     */
    @Override
    protected void updateWorkBlockState(BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        int stateIntByTemperature = getStateIntByTemperature();
        if(blockState.get(BlockRefrigerator.STATE_TEMP).intValue() != stateIntByTemperature) {
            world.setBlockState(pos, getNewBlockState(blockState), 3);
            validate();
            world.setTileEntity(pos, this);
        }
    }

    public void setInitTemperature(int initTemperature) {
        this.initTemperature = initTemperature;
    }

    /**
     * 根据机器当前状态决定blockstate
     *
     * @param state
     * @return
     */
    @Override
    protected BlockState getNewBlockState(BlockState state) {
        return state.with(BlockRefrigerator.STATE_TEMP, getStateIntByTemperature());
    }

    protected int getStateIntByTemperature() {
        if(temperature > 0) {
            return 3;
        } else if(temperature <= 0 && temperature > -10) {
            return 2;
        } else if(temperature <= -10 && temperature >= -15) {
            return 1;
        } else {
            return 0;
        }
    }
}
