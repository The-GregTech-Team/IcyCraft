package icycream.common.block.entity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;

import javax.annotation.Nullable;

/**
 * 机器状态
 * 也是老设计了
 * @param <T> 这个机器本身，用于寻找合成之类的
 */
public interface Machine<T extends IInventory> {
    /**
     * 机器运转状态
     * @return 机器是否在运转
     */
    boolean isWorking();

    /**
     * 寻找合成并判断输出未满
     * 如果你不调用 super 的话你需要在这里重新写一次 {@linkplain #findRecipe()}
     * @return 机器可以开始处理合成
     */
    boolean canWorkBegin();

    /**
     * 把关于能量等的判定放在这里面
     * 每 Tick 判断一次，true 即可调用 {@linkplain #progress()}
     * @return 机器可以处理合成
     */
    boolean canWork();

    boolean canFinish();

    /**
     * 寻找合成
     */
    @Nullable
    IRecipe<T> findRecipe();

    /**
     * 合成开始时调用
     * 重设进度
     */
    void onCraftingStart();

    /**
     * 合成结束时调用
     */
    void onCraftingFinish();

    /**
     * 每 Tick 执行一次
     * Post-progress 的简写，在父级逻辑执行完之后再执行
     * 我觉得不用写 Pre-progress 了
     *
     * 不要把耗时逻辑扔进去！
     */
    void progress();
}
