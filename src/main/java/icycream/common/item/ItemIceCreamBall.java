package icycream.common.item;

import com.google.common.collect.Lists;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemIceCreamBall extends Item {
    public ItemIceCreamBall(Properties properties) {
        super(properties);
    }


    /**
     * 根据nbt(原料配比)获取药水(buff)
     * 存储在ingredients nbt里面
     * 格式是dict
     * test command:
     * give Dev icycream:ice_cream_ball{"ingredient":"COCO"} 1
     * @param itemStack
     * @return
     */
    private List<EffectInstance> getEffectFromNBT(ItemStack itemStack) {
        String ingredient = itemStack.getTag().getString("ingredient");
        ArrayList<EffectInstance> effectInstances = Lists.newArrayList();
        Ingredient ingredientEnum = Ingredient.valueOf(ingredient);
        if(ingredient != null) {
            for (Effect effect : ingredientEnum.getPotionEffect()) {
                /**
                 * 初版，直接按照里面的数值来确定效果持续时间
                 */
                effectInstances.add(new EffectInstance(effect, 20));
            }
        }
        return effectInstances;
    }

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
