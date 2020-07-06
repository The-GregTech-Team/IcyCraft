package icycream.common;

import com.google.common.collect.Lists;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
public class ItemIceCream extends Item {
    protected Logger logger = LogManager.getLogger();
    private static List empty = Collections.unmodifiableList(Lists.newArrayList());
    public ItemIceCream(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }



    /**
     * 根据nbt(原料配比)获取药水(buff)
     * 存储在ingredients nbt里面
     * 格式是dict
     * test command:
     * give Dev icycream:ice_cream_basic{"ingredients":{"COCO":20,"MILK":40}} 1
     * @param itemStack
     * @return
     */
    private List<EffectInstance> getEffectFromNBT(ItemStack itemStack) {
        CompoundNBT ingredients = itemStack.getTag().getCompound("ingredients");
        if(ingredients == null || ingredients.keySet().isEmpty()) {
            logger.debug("eating an empty icecream");
            return empty;
        }
        else {
            List<EffectInstance> effectInstances = Lists.newArrayList();
            for (String ingredient : ingredients.keySet()) {
                for (Effect effect : Ingredient.valueOf(ingredient).getPotionEffect()) {
                    /**
                     * 初版，直接按照里面的数值来确定效果持续时间
                     */
                    logger.debug("got effect {} , duration = {}", effect.getName(), ingredients.getInt(ingredient));
                    effectInstances.add(new EffectInstance(effect, ingredients.getInt(ingredient)));
                }
            }
            return effectInstances;
        }
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     *
     * @param itemStack
     * @param world
     * @param world
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity livingEntity) {
        //复制了一段原版代码mmp
        //为了把根据nbt加药水的效果做出来
        //先做功能，目前这个地方就这样吧
        Item item = itemStack.getItem();
        if (item.isFood()) {
            world.playSound(null, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), livingEntity.getEatSound(itemStack), SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
            for (EffectInstance effectInstance : getEffectFromNBT(itemStack)) {
                livingEntity.addPotionEffect(effectInstance);
            }
            if (!(livingEntity instanceof PlayerEntity) || !((PlayerEntity)livingEntity).abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
        }
        return itemStack;
    }
}
